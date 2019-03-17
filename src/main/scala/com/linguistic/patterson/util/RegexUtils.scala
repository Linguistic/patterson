package com.linguistic.patterson.util

import java.util.regex.Pattern

import com.linguistic.patterson.models.corenlp.Token

import scala.collection.mutable

import com.linguistic.patterson.util.StringUtils._

object RegexUtils {
  case class Location(var start: Int, var end: Int)

  /**
    * Return the locations (start/end points) of each group in a substring given a regex pattern
    *
    * @param text         Text to match
    * @param regex        Regex pattern
    * @param startIndex   Start index of `text` in the parent string
    * @return
    */
  private def getCapturedLocationsFromMatch(text: String, regex: Pattern, startIndex: Int): List[Location] = {
    val matcher = regex.matcher(text)
    val m = matcher.find()

    var locations = List[Location]()

    if (m) {
      for (i ← 1 to matcher.groupCount()) {
        val group = matcher.group(i)

        if (group != null) {
          val start = matcher.start(i) + startIndex
          val end = start + group.length

          locations :+= Location(start, end)
        }
      }
    }

    locations
  }

  /**
    * Returns a list of the locations of each matched regex group, taking into account overlap
    *
    * @param text     Text to match
    * @param regex    Regex pattern
    * @return
    */
  def getMatchLocations(text: String, regex: String): List[List[Location]] = {
    // Compile the base regex
    val p = Pattern.compile(regex, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE)

    // Get a list of all matches
    val mainMatches = text.matchAll(p)

    // Return no locations if we can't even match the string
    if (mainMatches.isEmpty) return null

    var matchResults = List[List[Location]]()
    var lastMatchEndIndex = 0

    // Loop through each match string
    for (innerMatchStr ← mainMatches) {
      // Find the start of the matched string in the text, leaving off at the end of the last match
      val innerMatchStartPos = text.indexOf(innerMatchStr, lastMatchEndIndex)
      // Match all groups in this match and return their locations in the parent string
      var locations = this.getCapturedLocationsFromMatch(innerMatchStr, p, innerMatchStartPos)
      // Combine any of the same groups that happen to appear in two separate matches and overlap
      locations = combineAdjacentLocations(locations)
      // Add the locations to the parent results
      matchResults :+= locations
      // Move the index pointer to the end of the most recent match
      lastMatchEndIndex = innerMatchStartPos + innerMatchStr.length
    }

    matchResults
  }

  def combineAdjacentLocations(locations: List[Location]): List[Location] = {
    if (locations.size <= 1)
      return locations

    val sortedLocations = List(locations.sortWith((t1, t2) ⇒ (t1.start - t2.start) < 0): _*)

    var combinedIndices = List[Location]()
    var currentLocation = sortedLocations.head

    for (location ← sortedLocations) {
      if (location.start <= currentLocation.end && location.end > currentLocation.end)
        currentLocation.end = location.end
      else if (location.start < currentLocation.end) {
        combinedIndices :+= currentLocation
        currentLocation = location.copy()
      }
    }

    combinedIndices :+= currentLocation
    combinedIndices
  }

  def locationsOverlap(location1: Location, location2: Location): Boolean = {
    // location1.start is inside of location1
    if (location1.start <= location2.start && location1.end > location2.start) return true
    // location2.end is inside of location1
    if (location1.start < location2.end && location1.end >= location2.end) return true
    false
  }

  private def matchesOverlap(match1: List[Location], match2: List[Location]): Boolean = {
    // TODO: improve speed complexity in following block
    for (location1 ← match1)
      for (location2 ← match2)
        if (locationsOverlap(location1, location2))
          return true
    false
  }

  private def intersectMatches(match1: List[Location], match2: List[Location]): List[Location] = {
    val allLocations = (match1 ::: match2).sortWith((location1, location2) ⇒ (location1.start - location2.start) < 0)
    val locationQueue = mutable.Queue(allLocations: _*)

    var combinedMatch = List[Location]()

    if (locationQueue.isEmpty) return List[Location]()

    while (locationQueue.length > 1) {
      val currentLocation = locationQueue.dequeue()
      val head = locationQueue.head

      if (locationsOverlap(currentLocation, head)) {
        combinedMatch :+= Location(
          Math.max(currentLocation.start, head.start),
          Math.min(currentLocation.end, head.end)
        )
      }
    }

    combinedMatch
  }

  private def unionMatches(match1: List[Location], match2: List[Location]): List[Location] = {
    val allLocations = (match1 ::: match2).sortWith((location1, location2) ⇒ (location1.start - location2.start) < 0)

    var locationQueue = mutable.Queue(allLocations: _*)
    var combinedMatch = List[Location]()

    if (locationQueue.isEmpty) return List[Location]()

    while (locationQueue.length > 1) {
      val currentLocation = locationQueue.dequeue()
      val head = locationQueue.head

      if (locationsOverlap(currentLocation, head)) {
        locationQueue(0) = Location(
          Math.min(currentLocation.start, head.start),
          Math.max(currentLocation.end, head.end)
        )
      } else {
        combinedMatch :+= currentLocation
      }
    }

    combinedMatch :+= locationQueue.last
    locationQueue = locationQueue.dropRight(1)
    combinedMatch
  }

  private def matchesEqual(match1: List[Location], match2: List[Location]): Boolean = {
    if (match1.size != match2.size)
      return false

    for (i ← match1.indices) {
      val location1 = match1(i)
      val location2 = match2(i)

      if (location1.start != location2.start || location1.end != location2.end)
        return false

      return false
    }

    true
  }

  def matchAContainsMatchB(matchA: List[Location], matchB: List[Location]): Boolean = {
    matchesEqual(matchA, matchB) && !matchesEqual(intersectMatches(matchA, matchB), matchA)
  }

  private def appendOrMergeMatch(matchesList: List[List[Location]],
                                 m: List[Location],
                                 conservative: Boolean): List[List[Location]] = {

    var updatedMatchesList = List[List[Location]](matchesList: _*)

    for ((existingMatch, index) ← updatedMatchesList.view.zipWithIndex) {
      if (this.matchesOverlap(existingMatch, m)) {
        var combinedMatch: List[Location] = null

        if (conservative) combinedMatch = intersectMatches(existingMatch, m)
        else combinedMatch = unionMatches(existingMatch, m)

        updatedMatchesList = updatedMatchesList.updated(index, combinedMatch)

        return updatedMatchesList
      }
    }

    updatedMatchesList :+= m
    updatedMatchesList
  }

  /**
    * Merge matched regex groups
    *
    * @param locationMatchGroups  A list of matched group locations (start/end points)
    * @param conservative         Denotes whether in the event of overlapping matched to use the intersection (true) or union (false)
    * @return The merge list of matches
    */
  def mergeMatchGroups(locationMatchGroups: List[List[List[Location]]],
                       conservative: Boolean = true): List[List[Location]] = {
    var mergedMatches = List[List[Location]]()

    // Return null if we receive null
    if (locationMatchGroups == null) return null

    // Get all matches that aren't null and loop through them
    // TODO: improve speed complexity in following block
    for (locationMatchGroup ← locationMatchGroups.filter(x ⇒ x != null)) {
      for (locationMatch ← locationMatchGroup) {
        mergedMatches = this.appendOrMergeMatch(mergedMatches, locationMatch, conservative)
      }
    }

    if (mergedMatches.nonEmpty) mergedMatches else null
  }

  def locationFromToken(token: Token, matchCharsStr: String = null): Location = {
    val baseLocation = Location(token.beginPosition, token.endPosition)

    if (matchCharsStr == null)
      return baseLocation

    val matchData =
      token.word.matchOnce(Pattern.compile(s"[$matchCharsStr]+", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE))

    if (matchData == null)
      return null

    baseLocation.start += matchData.index
    baseLocation.end = baseLocation.start + matchData.text.length
    baseLocation
  };
}
