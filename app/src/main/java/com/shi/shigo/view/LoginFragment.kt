package com.shi.shigo.view

import android.app.Activity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shi.shigo.R
import com.shi.shigo.databinding.FragmentLoginBinding
import com.shi.shigo.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var isShowPassword = false
    private var email: String = ""
    private var password: String = ""
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container,false)

        initUI()

        viewModel.loginResponse.observe(viewLifecycleOwner){response ->
            if(response){
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }

        return binding.root
    }

    private fun initUI() {
        binding.btnRegister.setOnClickListener (this::onRegister)
        binding.btnLogin.setOnClickListener (this::onLogin)
        binding.ivHidePassw.setOnClickListener (this::onHidePassw)
    }

    private fun onRegister(v: View){
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun onLogin(v: View){
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()

        if(checkRequirements()){
            viewModel.login(email, password)

        }
    }

    private fun onHidePassw(v:View) {
        isShowPassword = !isShowPassword
        showPassword(isShowPassword)
    }

    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            binding.etPassword.transformationMethod= HideReturnsTransformationMethod.getInstance()
            binding.ivHidePassw.setImageResource(R.drawable.lock_eye)
        } else {
            binding.etPassword.transformationMethod= PasswordTransformationMethod.getInstance()
            binding.ivHidePassw.setImageResource(R.drawable.hidden_eye)
        }
    }

    private fun checkRequirements(): Boolean{
        if(!email.isValidEmail()){
            Toast.makeText(requireContext(), R.string.warning_email, Toast.LENGTH_LONG).show()
            return false
        } else if(password.isEmpty()){
            Toast.makeText(requireContext(), R.string.warning_password, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun String.isValidEmail() =
        isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}