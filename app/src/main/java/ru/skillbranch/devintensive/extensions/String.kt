package ru.skillbranch.devintensive.extensions

fun String.truncate(newsize:Int=16):String {
    var result:String = this.trim()
    return if (result.length>newsize) result.substring(0, newsize).trim()+"..." else result
}