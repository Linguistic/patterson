package com.linguistic.patterson.agents

import java.util.regex.{Matcher, Pattern}

import com.linguistic.patterson.util.StringUtils._
import com.linguistic.patterson.util.RegexUtils._

class RegexMatchingAgent {
    private def combineAdjacentLocations(locations: List[Location]): List[Location] = {
        if (locations.size <= 1)
            return locations

        val sortedLocations = List(locations.sortWith((t1, t2) ⇒ (t1.start - t2.start) < 0):_*)

        var combinedIndices = List[Location]()
        var currentLocation = sortedLocations(0)

        for (location ← sortedLocations) {
            if (location.start <= currentLocation.end && location.end > currentLocation.end)
                currentLocation.end = location.end
            else if (location.start < currentLocation.end) {
                combinedIndices :+= currentLocation
                currentLocation = location.copy()
            }
        }

        combinedIndices :+= currentLocation

        return combinedIndices
    }

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

        if (m != null) {
            for (i ← 1 to matcher.groupCount()) {
                val group = matcher.group(i)

                if (group != null) {
                    val start = matcher.start(i) + startIndex
                    val end = start + group.length

                    locations :+= new Location(start, end)
                }
            }
        }

        return locations
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
        val mainMatches = text.`match`(p)

        // Return no locations if we can't even match the string
        if (mainMatches.size == 0) return null

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

        return matchResults
    }
}
