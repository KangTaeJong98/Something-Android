package com.taetae98.something.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.MutableLiveData
import com.taetae98.something.di.SettingDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository @Inject constructor(
    @SettingDataStore
    private val settingDataStore: DataStore<Preferences>
) {
    companion object {
        private val isStickyKey by lazy { booleanPreferencesKey("isSticky") }
        private val showFinishedToDoInCalendarFragmentKey by lazy { booleanPreferencesKey("showFinishedToDoInCalendarFragment") }
        private val showFinishedToDoInDrawerFragmentKey by lazy { booleanPreferencesKey("showFinishedToDoInDrawerFragment") }
    }

    val isSticky = MutableLiveData(
        runBlocking(Dispatchers.IO) {
            settingDataStore.data.map {
                it[isStickyKey] ?: false
            }.first()
        }
    ).apply {
        observeForever { boolean ->
            CoroutineScope(Dispatchers.IO).launch {
                settingDataStore.edit {
                    it[isStickyKey] = boolean
                }
            }
        }
    }

    val showFinishedToDoInCalendarFragment = MutableLiveData(
        runBlocking(Dispatchers.IO) {
            settingDataStore.data.map {
                it[showFinishedToDoInCalendarFragmentKey] ?: true
            }.first()
        }
    ).apply {
        observeForever { boolean ->
            CoroutineScope(Dispatchers.IO).launch {
                settingDataStore.edit {
                    it[showFinishedToDoInCalendarFragmentKey] = boolean
                }
            }
        }
    }

    val showFinishedToDoInDrawerFragment = MutableLiveData(
        runBlocking(Dispatchers.IO) {
            settingDataStore.data.map {
                it[showFinishedToDoInDrawerFragmentKey] ?: true
            }.first()
        }
    ).apply {
        observeForever { boolean ->
            CoroutineScope(Dispatchers.IO).launch {
                settingDataStore.edit {
                    it[showFinishedToDoInDrawerFragmentKey] = boolean
                }
            }
        }
    }
}