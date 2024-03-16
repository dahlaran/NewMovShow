package com.dahlaran.newmovshow.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.danlew.android.joda.BuildConfig
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltAndroidApp
class NewMovShowApplication : Application() {
    companion object {
        var instance: WeakReference<NewMovShowApplication> = WeakReference(null)
    }

    override fun onCreate() {
        super.onCreate()
        instance = WeakReference(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}