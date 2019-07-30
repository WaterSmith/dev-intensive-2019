package ru.skillbranch.devintensive.utils

object Utils {
    val charMap = mapOf(
        'а' to "a", 'б' to "b", 'в' to "v", 'г' to "g", 'д' to "d", 'е' to "e", 'ё' to "e",
        'ж' to "zh", 'з' to "z", 'и' to "i", 'й' to "i", 'к' to "k", 'л' to "l", 'м' to "m",
        'н' to "n", 'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t", 'у' to "u",
        'ф' to "f", 'х' to "h", 'ц' to "c", 'ч' to "ch", 'ш' to "sh", 'щ' to "sh'", 'ъ' to "",
        'ы' to "i", 'ь' to "", 'э' to "e", 'ю' to "yu", 'я' to "ya"
    )

    fun parseFullName(fullName: String?): Pair<String?,String?>{
        val parts = fullName?.trim()?.split(" ")
        val firstName = if (parts?.getOrNull(0)?.length?:0 == 0) null else parts?.getOrNull(0)
        val lastName = if (parts?.getOrNull(1)?.length?:0 == 0) null else parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider:String = " "): String {
        var result:String = ""

        payload.trim().forEach { char ->
            var isUppercase = char.isUpperCase()
            var key = char.toLowerCase()
            var simbol = if (char.isWhitespace()) divider else charMap.getOrDefault(key,char.toString())
            if (isUppercase) {
                result += if (simbol.length in (0..1)) (simbol.toUpperCase())
                            else (simbol.get(0).toUpperCase().toString() + simbol.drop(1).toString())
            } else {
                result += simbol
            }
        }

        return result
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val first = if (firstName?.trim()?.length?:0==0) null else firstName?.trim()?.substring(0,1)
        val last = if (lastName?.trim()?.length?:0==0) null else lastName?.trim()?.substring(0,1)
        val result = ((first?:"")+(last?:"")).trim().toUpperCase()
        return if (result.length==0) null else result
    }

    fun mathGitHubAccount(adress:String):Boolean = adress.matches(
        Regex("^(http(s){0,1}:\\/\\/){0,1}(www.){0,1}github.com\\/[A-z\\d](?:[A-z\\d]|-(?=[A-z\\d])){0,38}\$",RegexOption.IGNORE_CASE)) &&
            !adress.matches(Regex("^.*(" +
                        "\\/enterprise|" +
                        "\\/features|" +
                        "\\/topics|" +
                        "\\/collections|" +
                        "\\/trending|" +
                        "\\/events|" +
                        "\\/marketplace" +
                        "|\\/pricing|" +
                        "\\/nonprofit|" +
                        "\\/customer-stories|" +
                        "\\/security|" +
                        "\\/login|" +
                        "\\/join)\$",RegexOption.IGNORE_CASE))

}