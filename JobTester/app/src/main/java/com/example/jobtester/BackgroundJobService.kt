package com.example.jobtester

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class BackgroundJobService: JobService() {

    companion object {
        private const val TIME_SLEEP_MILLIS: Long = 1000
    }

    private var jobCanceled: Boolean = false

    private fun doBackgroundWork(params: JobParameters?) {
        Thread {
            kotlin.run {
                for (i: Int in 0 until 10) {
                    Timber.d("doBackgroundWork: $i")
                    if (jobCanceled)
                        return@run
                    Thread.sleep(TIME_SLEEP_MILLIS)
                }
            }
            Timber.d("doBackgroundWork: finished")
            jobFinished(params, false)
        }.start()
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Timber.d("onStartJob")
        doBackgroundWork(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Timber.d("onStopJob")
        jobCanceled = true
        return true
    }
}