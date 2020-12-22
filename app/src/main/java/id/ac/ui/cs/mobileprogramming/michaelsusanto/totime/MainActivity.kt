package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.PermissionChecker
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.ActivityMainBinding
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.CovidActivity
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.permdenied.PermDeniedActivity
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.profile.ProfileActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
        setupNavController()
        setupBottomNavigation()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun setupNavController() {
        navController = findNavController(R.id.nav_controller)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationMain.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_profile -> goToProfileActivity()
            R.id.action_covid -> goToCovidActivity()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToProfileActivity(): Boolean {
        checkPermissions()
        return true
    }

    private fun goToCovidActivity(): Boolean {
        val intent = Intent(this, CovidActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun checkPermissions() {
        if (
            PermissionChecker.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_DENIED ||
            PermissionChecker.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PermissionChecker.PERMISSION_DENIED
        ) {
            val permissions = arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                requestPermissions(permissions, PERMISSION_CODE)
                Log.d("permission:", "request")
            }
        } else {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                } else {
                    val permDeniedIntent = Intent(this, PermDeniedActivity::class.java)
                    startActivity(permDeniedIntent)
                }
                return
            }
        }
    }
}