package com.linguistic.patterson.agents

import java.util.regex.{Matcher, Pattern}

import com.linguistic.patterson.util.StringUtils._
import com.linguistic.patterson.util.RegexUtils._

class RegexMatchingAgent {
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
        val mainMatches = text.`match`(p)

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
}
