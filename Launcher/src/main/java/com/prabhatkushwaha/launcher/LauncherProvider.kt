package com.prabhatkushwaha.launcher

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.prabhatkushwaha.launcher.model.AppModel
import com.prabhatkushwaha.launcher.service.AppStatusWorker
import java.util.concurrent.TimeUnit

object LauncherProvider {
    @SuppressLint("QueryPermissionsNeeded")
    fun getAllInstalledApp(packageManager: PackageManager, packageName: String): List<AppModel> {
        val packages = packageManager.getInstalledPackages(0)
        val appList = mutableListOf<AppModel>()
        for (i in packages.indices) {
            val packageData = packages[i]
            if (packageManager.getLaunchIntentForPackage(packageData.packageName) != null
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    if (packageData.applicationInfo.loadLabel(packageManager).toString()
                            .isNotEmpty() && packageData.packageName.isNotEmpty() && packageData.longVersionCode.toString()
                            .isNotEmpty() && packageData.versionName.isNotEmpty() && packageData.applicationInfo.loadIcon(
                            packageManager
                        ) != null && packageName != packageData.packageName
                    ) {
                        appList.add(
                            AppModel(
                                appName = packageData.applicationInfo.loadLabel(
                                    packageManager
                                ).toString(),
                                packageName = packageData.packageName,
                                versionCode = packageData.longVersionCode.toString(),
                                versionName = packageData.versionName,
                                appIcon = packageData.applicationInfo.loadIcon(
                                    packageManager
                                )
                            )
                        )
                    }
                } else {
                    if (packageData.applicationInfo.loadLabel(packageManager).toString()
                            .isNotEmpty() && packageData.packageName.isNotEmpty() && packageData.versionCode.toString()
                            .isNotEmpty() && packageData.versionName.isNotEmpty() && packageData.applicationInfo.loadIcon(
                            packageManager
                        ) != null && packageName != packageData.packageName
                    ) {
                        appList.add(
                            AppModel(
                                appName = packageData.applicationInfo.loadLabel(
                                    packageManager
                                ).toString(),
                                packageName = packageData.packageName,
                                versionCode = packageData.versionCode.toString(),
                                versionName = packageData.versionName,
                                appIcon = packageData.applicationInfo.loadIcon(
                                    packageManager
                                )
                            )
                        )
                    }
                }
            }

        }
        return appList.sortedBy {
            it.appName
        }
    }

    fun startAppService(context: Context){
        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(
            PeriodicWorkRequest.Builder(
                AppStatusWorker::class.java,
                1,
                TimeUnit.SECONDS
            ).build()
        )
    }
}