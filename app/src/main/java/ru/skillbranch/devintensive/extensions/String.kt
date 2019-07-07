package ru.skillbranch.devintensive.extensions

fun String.truncate(newsize:Int=16):String {
    var result:String = this.trim()
    return if (result.length>newsize) result.substring(0, newsize).trim()+"..." else result
}

fun String.trimDubleWhitespace():String {
    var result:String = this
    while (result.indexOf("  ")>-1) {
        result = result.replace("  "," ")
    }
    return result
}

fun String.stripHtml(): String {
    var task = this
    var result = ""
    while ((task.indexOf("<")>-1) && (task.indexOf(">")>-1) && task.indexOf("<")<task.indexOf(">")){
        result = result + task.substringBefore("<")
        task = task.substringAfter(">")
    }
    result = result + task

    return result.trim().trimDubleWhitespace()
}

