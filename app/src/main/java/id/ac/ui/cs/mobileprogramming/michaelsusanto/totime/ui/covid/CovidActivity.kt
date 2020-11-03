package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.databinding.ActivityCovidBinding

class CovidActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCovidBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_covid)
        setupToolbar()
        setupNavController()
        setupBottomNavigation()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.covid_19)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupNavController() {
        navController = findNavController(R.id.nav_controller_covid)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationCovid.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> pressBack()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun pressBack(): Boolean {
        onBackPressed()
        return true
    }
}