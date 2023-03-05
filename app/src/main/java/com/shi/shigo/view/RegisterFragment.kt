package com.shi.shigo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.shi.shigo.R
import com.shi.shigo.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private var email: String = ""
    private var password: String = ""
    private var passwordAgain: String = ""
    private var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container,false)

        init()

        return binding.root
    }

    private fun init() {
        binding.btnRegister.setOnClickListener (this::onRegister)
        binding.btnLogin.setOnClickListener (this::onLogin)
    }

    private fun onRegister(v: View){
        //Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment)
        email = binding.etEmail.text.toString().trim()
        name = binding.etName.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        passwordAgain = binding.etPasswordAgain.text.toString().trim()
        if(checkRequirements()){

        }

    }

    private fun checkRequirements(): Boolean{
        if(!email.isValidEmail()){
            Toast.makeText(requireContext(), R.string.warning_email, Toast.LENGTH_SHORT).show()
            return false
        } else if(name.isEmpty()){
            Toast.makeText(requireContext(), R.string.warning_name, Toast.LENGTH_SHORT).show()
            return false
        } else if(password.isEmpty()){
            Toast.makeText(requireContext(), R.string.warning_password, Toast.LENGTH_SHORT).show()
            return false
        } else if(password != passwordAgain){
            Toast.makeText(requireContext(), R.string.warning_pass_match, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun String.isValidEmail() =
        isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun onLogin(v: View){
        binding.root.findNavController().popBackStack()
    }

}