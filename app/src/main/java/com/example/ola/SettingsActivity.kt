package com.example.ola

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.ola.databinding.ActivityLoginBinding
import com.example.ola.databinding.ActivitySettingsBinding

// Get Memory Info
lateinit var activityManager: ActivityManager


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setMemoryInformation()
        setStorageInformation()
        setBatteryInformation()
        setNetworkInformation()

        var sharedPreferences: SharedPreferences = getSharedPreferences(
            "extraHelp", Context.MODE_PRIVATE
        )

        if (sharedPreferences.getBoolean("darkMode", false)) {
            binding.settingsSwitch.isChecked = true
        }
        binding.settingsSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                val editor = sharedPreferences?.edit()
                editor?.putBoolean("darkMode", true)
                editor?.commit()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                val editor = sharedPreferences?.edit()
                editor?.putBoolean("darkMode", false)
                editor?.commit()
            }
        }
    }


    private fun setMemoryInformation() {

        activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        val totalMemory = memoryInfo.totalMem / (1024 * 1024)  // Convert to MB
        val availableMemory = memoryInfo.availMem / (1024 * 1024)  // Convert to MB

        binding.memoryInformation.text = "Total: ${totalMemory}MB, Available: ${availableMemory}MB"
    }

    fun setStorageInformation() {

        val stat = StatFs(Environment.getExternalStorageDirectory().path)
        val bytesAvailable: Long = stat.blockSizeLong * stat.availableBlocksLong
        val megAvailable = bytesAvailable / (1024 * 1024)  // Convert to MB

        binding.storageInformation.text = "Avialable: ${megAvailable}MB"

    }

    fun setNetworkInformation() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            val type = when (activeNetworkInfo.type) {
                ConnectivityManager.TYPE_WIFI -> "WiFi"
                ConnectivityManager.TYPE_MOBILE -> "Mobile"
                else -> "Other"
            }

            binding.networkInformtion.text = "Connected to $type"
//            Toast.makeText(this, "Connected to $type network", Toast.LENGTH_SHORT).show()
        } else {
            binding.networkInformtion.text = "Not connected to any network"
//            Toast.makeText(this, "Not connected to any network", Toast.LENGTH_SHORT).show()
        }
    }


    fun setBatteryInformation() {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            applicationContext.registerReceiver(null, ifilter)
        }

        batteryStatus?.let { battery ->
            val level: Int = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = battery.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPct: Float = level * 100 / scale.toFloat()

            binding.batteryInformation.text = "Battery is $batteryPct%"

        }
    }
}