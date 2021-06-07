package mpei.vkr

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.StatFs
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavArgument
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import mpei.vkr.Constants.ARG_MASTER_KEY
import mpei.vkr.Constants.path
import mpei.vkr.Others.Permissions
import mpei.vkr.databinding.ActivityMainBinding
import mpei.vkr.ui.settings.SettingsFragment


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

        val permissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        Log.d("Права", permissionStatus.toString())

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
    }

}