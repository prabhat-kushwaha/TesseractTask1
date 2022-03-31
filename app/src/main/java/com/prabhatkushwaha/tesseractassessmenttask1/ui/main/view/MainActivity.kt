package com.prabhatkushwaha.tesseractassessmenttask1.ui.main.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.prabhatkushwaha.launcher.LauncherProvider.getAllInstalledApp
import com.prabhatkushwaha.launcher.LauncherProvider.startAppService
import com.prabhatkushwaha.tesseractassessmenttask1.R
import com.prabhatkushwaha.tesseractassessmenttask1.data.model.AppDataModel
import com.prabhatkushwaha.tesseractassessmenttask1.databinding.ActivityMainBinding
import com.prabhatkushwaha.tesseractassessmenttask1.ui.main.adapter.AppListAdapter
import com.prabhatkushwaha.tesseractassessmenttask1.ui.utils.Extension.showToast


class MainActivity : AppCompatActivity() {

    private lateinit var appAdapter: AppListAdapter
    private lateinit var appList: List<AppDataModel>
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        fetchAppData()
        setAppAdapter()
        startAppStatusService()
    }

    private fun startAppStatusService() {
        startAppService(this)
    }

    private fun setAppAdapter() {
        appAdapter = AppListAdapter(appList) {
            navigateToApp(it)
        }
        if (::binding.isInitialized)
            binding.rvAppData.adapter = appAdapter
    }

    private fun fetchAppData() {

        appList = getAllInstalledApp(packageManager, packageName).map {
            AppDataModel(
                appIcon = it.appIcon,
                appName = it.appName,
                packageName = "Package: " + it.packageName,
                versionName = "Version Name: " + it.versionName,
                versionCode = "Version Code: " + it.versionCode
            )
        }
    }

    private fun navigateToApp(appModel: AppDataModel) {
        try {
            val intent: Intent? =
                packageManager.getLaunchIntentForPackage(appModel.packageName.replace("Package: ",""))?.apply {
                    addCategory(Intent.CATEGORY_LAUNCHER)
                }

            if (intent != null)
                startActivity(intent)
        } catch (e: PackageManager.NameNotFoundException) {
            showToast("application not found")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val searchItem: MenuItem = menu!!.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return false
            }
        })
        return true
    }

    fun filter(filterText: String?) {
        val filterList = arrayListOf<AppDataModel>()
        if (::appList.isInitialized) {
            appList.forEach {
                if (it.appName.replace(" ", "").lowercase().contains(filterText?.lowercase() ?: ""))
                    filterList.add(it)
            }
        }
        if (filterList.isEmpty()) {
            showToast(getString(R.string.data_not_found))
        } else if (::appAdapter.isInitialized)
            appAdapter.setFilterData(filterList)
    }
}