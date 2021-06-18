package com.taetae98.something

import android.app.Application
import android.content.Intent
import android.os.Build
import com.taetae98.something.repository.SettingRepository
import com.taetae98.something.service.StickyService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var settingRepository: SettingRepository

    override fun onCreate() {
        super.onCreate()
        onCreateStickyService()
    }

    private fun onCreateStickyService() {
        settingRepository.isSticky.observeForever {
            if (it) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(Intent(this, StickyService::class.java))
                } else {
                    startService(Intent(this, StickyService::class.java))
                }
            } else {
                stopService(Intent(this, StickyService::class.java))
            }
        }
    }
}