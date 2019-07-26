package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils

data class Profile(
    var firstName :String = "",
    var lastName :String = "",
    var about :String = "",
    var repository :String = "",
    var rating :Int = 0,
    var respect :Int = 0
) {
    var rank :String = "Junior Android Developer"
    val nickname :String = Utils.transliteration("$firstName $lastName","_")
    fun toMap():Map<String, Any> = mapOf(
        "nickname" to nickname,
        "rank" to rank,
        "firstName" to firstName,
        "lastName" to lastName,
        "about" to about,
        "repository" to repository,
        "rating" to rating,
        "respect" to respect
    )
}