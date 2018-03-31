package com.linguistic.patterson.util

import com.linguistic.patterson.models.Token

object TokenUtils {
    def isRoot = (token: Token) ⇒ token.index == 0
}
