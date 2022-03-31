package com.prabhatkushwaha.launcher.service

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.prabhatkushwaha.launcher.receiver.AppBroadcastReceiver


class AppStatusWorker(private val appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addDataScheme("package")
        val mPackageChangedReceiver = AppBroadcastReceiver()
        appContext.registerReceiver(mPackageChangedReceiver, intentFilter)
        return Result.success()
    }
}