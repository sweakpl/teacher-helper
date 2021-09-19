package com.sweak.teacherhelper.features.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.teacherhelper.data.database.entity.Group
import com.sweak.teacherhelper.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    val repository: GroupRepository
) : ViewModel() {

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