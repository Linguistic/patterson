package com.linguistic.patterson.util

import java.util.regex.Pattern

import com.linguistic.patterson.models.corenlp.Token

import scala.collection.mutable

import com.linguistic.patterson.util.StringUtils._

object RegexUtils {
    case class Location(var start: Int, var end: Int)

    def combineAdjacentLocations(locations: List[Location]): List[Location] = {
        if (locations.size <= 1)
            return locations

        val sortedLocations = List(locations.sortWith((t1, t2) ⇒ (t1.start - t2.start) < 0):_*)

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
        return false
    }

    def matchesOverlap(match1: List[Location], match2: List[Location]): Boolean = {
        // TODO: improve speed complexity in following block
        for (location1 ← match1)
            for (location2 ← match2)
                if (locationsOverlap(location1, location2))
                    return true
        return false
    }

    def intersectMatches(match1: List[Location], match2: List[Location]): List[Location] = {
        val allLocations = (match1 ::: match2).sortWith((location1, location2) ⇒ (location1.start - location2.start) < 0)
        val locationQueue = mutable.Queue(allLocations:_*)

        var combinedMatch = List[Location]()

        if (locationQueue.size == 0) return List[Location]()

        while (locationQueue.length > 1) {
            val currentLocation = locationQueue.dequeue()
            val head = locationQueue.head

            if (locationsOverlap(currentLocation, head)) {
                combinedMatch :+= new Location(
                    Math.max(currentLocation.start, head.start),
                    Math.min(currentLocation.end, head.end)
                )
            }
        }

        return combinedMatch
    }

    def unionMatches(match1: List[Location], match2: List[Location]): List[Location] = {
        val allLocations = (match1 ::: match2).sortWith((location1, location2) ⇒ (location1.start - location2.start) < 0)

        var locationQueue = mutable.Queue(allLocations:_*)
        var combinedMatch = List[Location]()

        if (locationQueue.size == 0) return List[Location]()

        while (locationQueue.length > 1) {
            val currentLocation = locationQueue.dequeue()
            val head = locationQueue.head

            if (locationsOverlap(currentLocation, head)) {
                locationQueue(0) = new Location(
                    Math.min(currentLocation.start, head.start),
                    Math.max(currentLocation.end, head.end)
                )
            } else {
                combinedMatch :+= currentLocation
            }
        }

        combinedMatch :+= locationQueue.last
        locationQueue = locationQueue.dropRight(1)

        return combinedMatch
    }

    def matchesEqual(match1: List[Location], match2: List[Location]): Boolean = {
        if (match1.size != match2.size)
            return false

        for (i ← 0 to match1.size - 1) {
            val location1 = match1(i)
            val location2 = match2(i)

            if (location1.start != location2.start || location1.end != location2.end)
                return false

            return false
        }

        return true
    }

    def matchAContainsMatchB(matchA: List[Location], matchB: List[Location]) : Boolean = {
        val intersection = intersectMatches(matchA, matchB)
        return matchesEqual(matchA, matchB) && !matchesEqual(intersection, matchA)
    }

    def appendOrMergeMatch(matchesList: List[List[Location]], m: List[Location], conservative: Boolean) : List[List[Location]] = {
        var updatedMatchesList = List[List[Location]](matchesList:_*)

        for ((existingMatch, index) ← updatedMatchesList.view.zipWithIndex) {
            if (this.matchesOverlap(existingMatch, m)) {
                var combinedMatch : List[Location] = null

                if (conservative) combinedMatch = intersectMatches(existingMatch, m)
                else combinedMatch = unionMatches(existingMatch, m)

                updatedMatchesList = updatedMatchesList.updated(index, combinedMatch)

                return updatedMatchesList
            }
        }

        updatedMatchesList :+= m

        return updatedMatchesList
    }

    /**
      * Merge matched regex groups
      *
      * @param locationMatchGroups  A list of matched group locations (start/end points)
      * @param conservative         Denotes whether in the event of overlapping matched to use the intersection (true) or union (false)
      * @return The merge list of matches
      */
    def mergeMatchGroups(locationMatchGroups: List[List[List[Location]]], conservative: Boolean = true): List[List[Location]] = {
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

//    def locFromToken(token: Token, matchCharsStr: String = null) : Location = {
//        val baseLocation = Location(token.beginPosition, token.endPosition)
//
//        if (matchCharsStr.isEmpty)
//            return baseLocation
//
//        val matchData = token.word.`match`(Pattern.compile(s"[${matchCharsStr}]+", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE))
//
//        if (matchData == null)
//            return null
//
//        baseLocation.start += matchData.index
//        baseLocation.end = baseLocation.start + matchData[0].length;
//
//        return baseLocation;
//    };
}
