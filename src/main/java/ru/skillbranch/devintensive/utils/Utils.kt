package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?,String?>{
        val parts = fullName?.split(" ")
        val firstName = if (parts?.getOrNull(0)?.length?:0 == 0) null else parts?.getOrNull(0)
        val lastName = if (parts?.getOrNull(1)?.length?:0 == 0) null else parts?.getOrNull(1)
        return firstName to lastName
    }
}