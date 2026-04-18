package com.example.shufuroomapp.features.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shufuroomapp.R
import com.example.shufuroomapp.features.auth.login.LoginActivity
import com.example.shufuroomapp.core.utils.PrefManager
import com.example.shufuroomapp.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity(), DashboardContract.View {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var presenter: DashboardContract.Presenter
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefManager = PrefManager(this)
        if (prefManager.getToken() == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DashboardPresenter(this)


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            presenter.handleNavigation(item.itemId)
        }

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.nav_home
        }
    }

    override fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}