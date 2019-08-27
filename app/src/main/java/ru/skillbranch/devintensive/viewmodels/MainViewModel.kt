package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel : ViewModel() {
    private val chatRepository = ChatRepository
}