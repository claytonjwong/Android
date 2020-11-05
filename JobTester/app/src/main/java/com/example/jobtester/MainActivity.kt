package com.example.jobtester

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    companion object {
        private const val JOB_ID = 13
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())
        Timber.d("onCreate")

        scheduleJobButton.setOnClickListener {
            scheduleJob()
        }

        cancelJobButton.setOnClickListener {
            cancelJob()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun scheduleJob() {
        Timber.d("scheduleJob: begin")
        val componentName = ComponentName(this, BackgroundJobService::class.java)
        val jobInfo = JobInfo.Builder(JOB_ID, componentName).build()
        val jobScheduler: JobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = jobScheduler.schedule(jobInfo)
        Timber.d("scheduleJob: $resultCode")
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun cancelJob() {
        Timber.d("cancelJob: begin")
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(JOB_ID)
        Timber.d("cancelJob: end")
    }
}