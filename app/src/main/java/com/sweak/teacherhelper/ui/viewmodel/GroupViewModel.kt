package com.sweak.teacherhelper.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sweak.teacherhelper.database.entity.Group
import com.sweak.teacherhelper.repository.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GroupRepository = GroupRepository(application)
    val allGroups: LiveData<List<Group>> = repository.allGroups

    fun insert(group: Group) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(group)
        }
    }

    fun update(group: Group) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(group)
        }
    }

    fun delete(group: Group) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(group)
        }
    }
}

class GroupViewModelFactory(private val application: Application) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GroupViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}