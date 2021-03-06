@file:Suppress("DEPRECATION", "IMPLICIT_CAST_TO_ANY")

package mpei.vkr

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.os.StatFs
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.Menu
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Constants.MasterKey
import mpei.vkr.Constants.PATH_KEY_STORE
import mpei.vkr.Constants.UseMasterKey
import mpei.vkr.Constants.path
import mpei.vkr.Crypto.MasterKey
import mpei.vkr.Others.Permissions
import mpei.vkr.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.security.KeyStore


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var password: String
    private lateinit var sp: SharedPreferences

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val p = Permissions()
        p.requestMultiplePermissions(this, PERMISSION_REQUEST_CODE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.menu.findItem(R.id.freeMemory).title = freeMemory()

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_encrypt,
                R.id.nav_decrypt,
                R.id.nav_certificate,
                R.id.nav_settings
            ), drawerLayout
        )
        sp = getDefaultSharedPreferences(this)
        password = intent.extras!!.getString(ARG_MASTER_KEY)!!
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val switch =
            MenuItemCompat.getActionView(binding.navView.menu.findItem(R.id.app_bar_switch))
                .findViewById<View>(
                    R.id.switch_master_key
                ) as Switch
        switch.isChecked = sp.getBoolean(UseMasterKey, false)
        switch.setOnCheckedChangeListener { _, isChecked ->
            sp.edit().apply {
                putBoolean(UseMasterKey, isChecked)
                apply()
            }
        }

        val masterKey = MasterKey(password).getMasterKey()
        sp.edit().apply {
            putString(MasterKey, masterKey)
            apply()
            binding.root.invalidate()
        }
        if (!File(PATH_KEY_STORE).exists()) createKeyStore(masterKey.toCharArray())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @SuppressLint("CommitPrefEdits")
    override fun onDestroy() {
        super.onDestroy()
        sp.edit().apply {
            remove(MasterKey)
            apply()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 0
        private const val rule = "%.2f"
        private const val TEN = 1000000000

        private fun freeMemory(): String {
            val statFs = StatFs(path)
            val freeSpace = String.format(rule, statFs.freeBytes.toDouble() / TEN)
            val fullSpace = String.format(rule, statFs.totalBytes.toDouble() / TEN)
            return "???????????????? $freeSpace / $fullSpace ????"
        }

        private fun createKeyStore(password: CharArray) {
            val keyStore = KeyStore.getInstance("PKCS12")
            keyStore.load(null, password)
            val keyStoreOutputStream = FileOutputStream(PATH_KEY_STORE)
            keyStore.store(keyStoreOutputStream, password)
        }
    }

}