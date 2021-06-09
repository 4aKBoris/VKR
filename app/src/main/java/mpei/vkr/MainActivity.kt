@file:Suppress("DEPRECATION")

package mpei.vkr

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.os.StatFs
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Constants.PATH_KEY_STORE
import mpei.vkr.Constants.path
import mpei.vkr.Others.Permissions
import mpei.vkr.databinding.ActivityMainBinding
import mpei.vkr.ui.settings.SettingsFragment
import java.io.File
import java.io.FileOutputStream
import java.security.KeyStore


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var password: String

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.menu.findItem(R.id.freeMemory).title = freeMemory()

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_encrypt, R.id.nav_decrypt, R.id.nav_settings
            ), drawerLayout
        )

        SettingsFragment().arguments = Bundle().apply{ putString(ARG_MASTER_KEY, "dwa") }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val p = Permissions()
        p.requestMultiplePermissions(this, PERMISSION_REQUEST_CODE)
        //password = intent.extras!!.getString(ARG_MASTER_KEY)!!

        password = "12345678"
        if(!File(PATH_KEY_STORE).exists()) {
            val keyStore = KeyStore.getInstance("PKCS12")
            keyStore.load(null, password.toCharArray())
            val keyStoreOutputStream = FileOutputStream(PATH_KEY_STORE)
            keyStore.store(keyStoreOutputStream, password.toCharArray())
        }

        val permissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        Log.d("Права", permissionStatus.toString())

        createDir("VKR")
        createDir("VKR/CipherFiles")
        createDir("VKR/ClearFiles")
        createDir("VKR/Certificates")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 0
        private const val rule = "%.2f"
        private const val TEN = 1000000000

        private fun freeMemory(): String {
            val statFs = StatFs(path)
            val freeSpace = String.format(rule, statFs.freeBytes.toDouble() / TEN)
            val fullSpace = String.format(rule, statFs.totalBytes.toDouble() / TEN)
            return "Свободно $freeSpace / $fullSpace ГБ"
        }

        private fun createDir(name: String): File? {
            val baseDir: File?
            baseDir = File(path)
            val folder = File(baseDir, name)
            if (folder.exists()) return folder
            if (folder.isFile) folder.delete()
            return if (folder.mkdirs()) folder else getExternalStorageDirectory()
        }
    }

}