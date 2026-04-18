package com.example.shufuroomapp.features.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shufuroomapp.core.utils.hide
import com.example.shufuroomapp.core.utils.show
import com.example.shufuroomapp.core.utils.toast
import com.example.shufuroomapp.databinding.ActivityLoginBinding
import com.example.shufuroomapp.features.auth.register.RegisterActivity
import com.example.shufuroomapp.features.dashboard.DashboardActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this, LoginRepository())

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val pass = binding.etPassword.text.toString().trim()
            presenter.login(email, pass)
        }

        binding.btnRegister.setOnClickListener {
            navigateToRegister()
        }

    }

    override fun showLoading() {
        binding.progressBar.show()
        binding.btnLogin.isEnabled = false
    }

    override fun hideLoading() {
        binding.progressBar.hide()
        binding.btnLogin.isEnabled = true
    }

    override fun onLoginSuccess(message: String) {
        toast(message)
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onLoginError(error: String) {
        toast(error)
    }

    override fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}