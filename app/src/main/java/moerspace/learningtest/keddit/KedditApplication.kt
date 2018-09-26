package moerspace.learningtest.keddit

import android.app.Application
import moerspace.learningtest.keddit.commons.log.NotLoggingTree
import timber.log.Timber

class KedditApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(NotLoggingTree())
        }
    }
}