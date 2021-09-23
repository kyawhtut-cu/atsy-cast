package com.kyawhut.atsycast.ui.setting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.leanback.preference.LeanbackPreferenceFragmentCompat
import androidx.leanback.preference.LeanbackSettingsFragmentCompat
import androidx.preference.*
import com.kyawhut.atsycast.BuildConfig
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.share.utils.ShareUtils.deviceID
import com.kyawhut.atsycast.share.utils.extension.get
import com.kyawhut.atsycast.share.utils.extension.put
import com.kyawhut.atsycast.ui.password.DialogPassword
import com.kyawhut.atsycast.ui.password.DialogPassword.Companion.showPasswordDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 9/16/21
 */
@AndroidEntryPoint
class SettingFragment : LeanbackSettingsFragmentCompat() {

    override fun onPreferenceStartInitialScreen() {
        startPreferenceFragment(ATSYSettingFragment())
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

    @AndroidEntryPoint
    class ATSYSettingFragment : LeanbackPreferenceFragmentCompat(),
        Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

        private var prefAdult: CheckBoxPreference? = null
        private var prefDeviceID: Preference? = null
        private var prefAppVersion: Preference? = null
        private var prefAppPackage: Preference? = null
        private var prefLanguage: ListPreference? = null
        private var prefDisplayName: EditTextPreference? = null

        private val sh: SharedPreferences by lazy {
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        }
        private val vm: SettingViewModel by viewModels()

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.atsy_setting, rootKey)

            prefLanguage = findPreference(getString(R.string.pref_language))
            prefDeviceID = findPreference(getString(R.string.pref_device_id))
            prefAppVersion = findPreference(getString(R.string.pref_app_version))
            prefAppPackage = findPreference(getString(R.string.pref_app_package))
            prefAdult = findPreference(getString(R.string.pref_adult))
            prefDisplayName = findPreference(getString(R.string.pref_display_name))

            prefAppVersion?.summary = BuildConfig.VERSION_NAME
            prefAppPackage?.summary = BuildConfig.APPLICATION_ID
            prefDeviceID?.summary = requireContext().deviceID

            prefAdult?.onPreferenceChangeListener = this
            prefLanguage?.onPreferenceChangeListener = this
        }

        override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
            when (preference) {
                prefAdult -> {
                    if (prefAdult?.isChecked == false) {
                        showPasswordDialog(DialogPassword.Companion.PasswordType.ADULT) {
                            prefAdult?.isChecked = it
                        }
                    }
                }
                prefLanguage -> {
                    Timber.d("Choose language => %s", newValue)
                }
            }
            return true
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences,
            key: String
        ) {
            when (key) {
                getString(R.string.pref_display_name) -> {
                    if (sharedPreferences.get(key, "").isEmpty()) return
                    (requireActivity() as SettingActivity).toggleLoading(true)
                    vm.changeDisplayName(
                        requireContext(),
                        sharedPreferences.get(key, "")
                    ) { status, message ->
                        (requireActivity() as SettingActivity).toggleLoading(
                            false,
                            if (!status) getString(R.string.lbl_error_change_name) else ""
                        )
                        if (status) {
                            sh.put(
                                getString(R.string.old_pref_display_name),
                                sharedPreferences.get(key, "")
                            )
                        } else prefDisplayName?.text = sh.get(
                            getString(R.string.old_pref_display_name),
                            ""
                        )
                    }
                }
            }
        }

        override fun onPreferenceClick(preference: Preference): Boolean {
            return true
        }

        override fun onResume() {
            super.onResume()
            sh.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onDestroy() {
            sh.unregisterOnSharedPreferenceChangeListener(this)
            super.onDestroy()
        }

    }
}
