package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?,String?>{
        val parts = fullName?.split(" ")
        val firstName = if (parts?.getOrNull(0)?.length?:0 == 0) null else parts?.getOrNull(0)
        val lastName = if (parts?.getOrNull(1)?.length?:0 == 0) null else parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider:String = " "): String {
        //TODO()
        return "not implemented"
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val first = if (firstName?.trim()?.length?:0==0) null else firstName?.trim()?.substring(0,1)
        val last = if (lastName?.trim()?.length?:0==0) null else lastName?.trim()?.substring(0,1)
        val result = ((first?:"")+(last?:"")).trim().toUpperCase()
        return if (result.length==0) null else result
    }


}