package com.prabhatkushwaha.launcher.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AppBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (intent.action.equals(Intent.ACTION_PACKAGE_ADDED)) {
                val packageName = intent.dataString
                Toast.makeText(context, packageName + "is install !", Toast.LENGTH_SHORT).show()
            } else if (intent.action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
                val packageName = intent.dataString
                Toast.makeText(context, packageName + "is uninstall !", Toast.LENGTH_SHORT).show()
            }
        }

    }
}