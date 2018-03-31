package com.linguistic.patterson.graph

import java.util.regex.Pattern

import com.linguistic.patterson.models.Token

object Edge {
    case class EdgeOptions(`type`: String)

    class Edge(opts: EdgeOptions, val childNode: Node) {
        val `type` = Pattern.compile(s"^(${opts.`type`})$$")

        private def matchesDependent(token: Token, dependent: Token): Boolean = {
            if (dependent.dependencyType == "ROOT") return false
            return false
        }
    }
}