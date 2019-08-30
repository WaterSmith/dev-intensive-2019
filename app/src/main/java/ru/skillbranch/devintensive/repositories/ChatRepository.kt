package ru.skillbranch.devintensive.repositories

import ru.skillbranch.devintensive.utils.DataGenerator

object ChatRepository {
    fun loadChats() = DataGenerator.generateChats(10, false)
}