package mpei.vkr.ui.settings

import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import mpei.vkr.ui.settings.items.cipher.SettingsCipherFragment
import mpei.vkr.ui.settings.items.lockdown.LockdownFragment
import mpei.vkr.ui.settings.items.masterkey.SettingsMasterKeyFragment
import mpei.vkr.ui.settings.items.message.digest.SettingsMessageDigestFragment
import mpei.vkr.ui.settings.items.others.SettingsOthersFragment
import mpei.vkr.ui.settings.items.signature.SettingsSignatureFragment

class SettingsAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SettingsCipherFragment()
            1 -> SettingsMessageDigestFragment()
            2 -> SettingsSignatureFragment()
            3 -> SettingsMasterKeyFragment()
            4 -> LockdownFragment()
            5 -> SettingsOthersFragment()
            else -> SettingsCipherFragment()
        }
    }
}