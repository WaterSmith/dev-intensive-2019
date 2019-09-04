package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.repositories.GroupRepository

class GroupViewModel: ViewModel() {
    private val groupRepository = GroupRepository
    private val query = mutableLiveData("")
    private val userItems = mutableLiveData(loadUsers())
    private val selectedItems = Transformations.map(userItems){users -> users.filter{it.isSelected}}

    fun getUsersData():LiveData<List<UserItem>>{
        val result = MediatorLiveData<List<UserItem>>()
        val filterF = {
            val queryStr = query.value!!
            val users = userItems.value!!

            result.value =  if (queryStr.isEmpty()) users
                            else users.filter { it.fullName.contains(queryStr, true) }
        }
        result.addSource(userItems){filterF.invoke()}
        result.addSource(query){filterF.invoke()}

        return result
    }

    fun getSelectedData():LiveData<List<UserItem>>{
        return selectedItems
    }

    private fun offOrInverseSelection(userId: String, inverse:Boolean = true){
        userItems.value = userItems.value!!.map{
            if (it.id == userId) it.copy(isSelected = (!it.isSelected && inverse))
            else it
        }
    }

    fun handleSelectedItem(userId:String) {
        offOrInverseSelection(userId, true)
        //userItems.value = userItems.value!!.map{
        //    if (it.id == userId) it.copy(isSelected = !it.isSelected)
        //    else it
        //}
    }

    private fun loadUsers():List<UserItem> = groupRepository.loadUsers().map { it.toUserItem() }

    fun handleRemoveChip(userId: String) {
        offOrInverseSelection(userId, false)
        //userItems.value = userItems.value!!.map{
        //    if (it.id == userId) it.copy(isSelected = false)
        //    else it
        //}
    }

    fun handleSearchQuery(queryStr: String?) {
        query.value = queryStr.orEmpty()
    }

    fun handleCreateGroup() {
        groupRepository.createChat(selectedItems!!.value)
    }
}