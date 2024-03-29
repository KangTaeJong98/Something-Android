package com.taetae98.something.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.taetae98.something.dto.Date
import com.taetae98.something.dto.ToDo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ToDoEditViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
): ViewModel() {
    val id = stateHandle.getLiveData("ID", 0L)
    val title = stateHandle.getLiveData("TITLE", "")
    val description = stateHandle.getLiveData("DESCRIPTION", "")
    val drawerId = stateHandle.getLiveData<Long>("DRAWER_ID", null)
    val isOnTop = stateHandle.getLiveData("IS_ON_TOP", false)
    val isNotification = stateHandle.getLiveData("IS_NOTIFICATION", false)
    val isFinished = stateHandle.getLiveData("IS_FINISHED", false)
    val password = stateHandle.getLiveData<String>("PASSWORD", null)
    val hideDescription = stateHandle.getLiveData("HIDE_DESCRIPTION", false)
    val beginDate = stateHandle.getLiveData<Date>("BEGIN_DATE", null)
    val endDate = stateHandle.getLiveData<Date>("END_DATE", null)

    val hasTerm = stateHandle.getLiveData("HAS_TERM", (beginDate.value != null && endDate.value != null))
    val hasDrawer = stateHandle.getLiveData("HAS_DRAWER", (drawerId.value != null))

    init {
        beginDate.observeForever {
            hasTerm.value = (it != null && endDate.value != null)
        }
        endDate.observeForever {
            hasTerm.value = (beginDate.value != null && it != null)
        }
        drawerId.observeForever {
            hasDrawer.value = it != null
        }
        hasTerm.observeForever {
            if (it) {
                if (beginDate.value == null) {
                    beginDate.value = Date()
                }
                if (endDate.value == null) {
                    endDate.value = Date()
                }
            }
        }
    }

    fun update(todo: ToDo) {
        id.value = todo.id
        title.value = todo.title
        description.value = todo.description
        drawerId.value = todo.drawerId
        isOnTop.value = todo.isOnTop
        isNotification.value = todo.isNotification
        isFinished.value = todo.isFinished
        beginDate.value = todo.term?.beginDate
        endDate.value = todo.term?.endDate
    }
}