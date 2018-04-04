package com.linguistic.patterson.clients

import com.linguistic.patterson.models.corenlp.Document

trait TClient {
    def parse(text: String): Document
}