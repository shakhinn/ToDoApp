package com.example.todoapp.backend

class Note (var uid: Int, var datestamp: String, var text: String, var status: Boolean) {

    override fun toString(): String {
        return "{\"uid\": $uid, \"datestamp\": \"$datestamp\", \"text\": \"$text\", \"status\": $status}"
    }
}