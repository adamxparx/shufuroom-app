package com.example.shufuroomapp.features.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shufuroomapp.core.utils.hide
import com.example.shufuroomapp.core.utils.show
import com.example.shufuroomapp.core.utils.toast
import com.example.shufuroomapp.databinding.ActivityRegisterBinding
import com.example.shufuroomapp.features.auth.login.LoginActivity

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = RegisterPresenter(this, RegisterRepository())

        binding.btnRegister.setOnClickListener {
            val fName = binding.etFirstName.text.toString().trim()
            val lName = binding.etLastName.text.toString().trim()
            val email = binding.etRegEmail.text.toString().trim()
            val pass = binding.etRegPassword.text.toString().trim()
            val cPass = binding.etRegConfirmPassword.text.toString().trim()

            presenter.register(fName, lName, email, pass, cPass)
        }

        binding.btnLogin.setOnClickListener {
            finish()
        }

    }

    override fun showLoading() {
        binding.regProgressBar.show()
        binding.btnRegister.isEnabled = false
    }

    override fun hideLoading() {
        binding.regProgressBar.hide()
        binding.btnRegister.isEnabled = true
    }

    override fun onRegisterSuccess(message: String) {
        toast(message)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRegisterError(error: String) {
        toast(error)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}