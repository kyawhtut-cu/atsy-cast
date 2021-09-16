package com.kyawhut.atsycast.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.leanback.preference.LeanbackPreferenceFragmentCompat
import androidx.leanback.preference.LeanbackSettingsFragmentCompat
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.kyawhut.atsycast.BuildConfig
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.ui.password.DialogPassword.Companion.showPasswordDialog
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 9/16/21
 */
class SettingFragment : LeanbackSettingsFragmentCompat() {

    override fun onPreferenceStartInitialScreen() {
        startPreferenceFragment(DemoFragment())
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        return true
    }

    override fun onPreferenceStartScreen(
        caller: PreferenceFragmentCompat,
        pref: PreferenceScreen
    ): Boolean {
        return true
    }

    class DemoFragment : LeanbackPreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

        private var prefAdult: CheckBoxPreference? = null
        private var prefDeviceID: Preference? = null
        private var prefAppVersion: Preference? = null
        private var prefAppPackage: Preference? = null
        private var prefLottery: CheckBoxPreference? = null

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.atsy_setting, rootKey)

            prefLottery = findPreference(getString(R.string.pref_lottery))
            prefDeviceID = findPreference(getString(R.string.pref_device_id))
            prefAppVersion = findPreference(getString(R.string.pref_app_version))
            prefAppPackage = findPreference(getString(R.string.pref_app_package))
            prefAdult = findPreference(getString(R.string.pref_adult))

            prefAppVersion?.summary = BuildConfig.VERSION_NAME
            prefAppPackage?.summary = BuildConfig.APPLICATION_ID
            prefDeviceID?.summary = "Device ID 123456"

            prefDeviceID?.onPreferenceClickListener = this

            prefAdult?.onPreferenceChangeListener = this
            prefLottery?.onPreferenceChangeListener = this
        }

        override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
            Timber.d("onPreferenceChange => %s", preference)
            when (preference) {
                prefAdult -> {
                    prefAdult?.isChecked = false
                    showPasswordDialog(getString(R.string.lbl_dark_side_dialog_password_title)) {
                        prefAdult?.isChecked = it
                    }
                }
                prefLottery -> {
                }
            }
            return true
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences,
            key: String?
        ) {
            Timber.d("onSharedPreferenceChanged => %s", key)
        }

        override fun onPreferenceClick(preference: Preference): Boolean {
            Timber.d("onPreferenceClick => %s", preference)
            when (preference) {
                prefDeviceID -> {
                }
            }
            return true
        }
    }
}
