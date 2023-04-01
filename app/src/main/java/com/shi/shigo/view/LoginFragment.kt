package com.shi.shigo.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.shi.shigo.R
import com.shi.shigo.databinding.FragmentLoginBinding
import com.shi.shigo.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var isShowPassword = false
    private var email: String = ""
    private var password: String = ""
    private val viewModel: LoginViewModel by viewModels()
    @Inject
    lateinit var auth: FirebaseAuth
    @Inject
    lateinit var gsc: GoogleSignInClient
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerForActivityResult()
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

    private fun registerForActivityResult(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val signInAccountTask  = GoogleSignIn.getSignedInAccountFromIntent(data)

                if (signInAccountTask.isSuccessful) {
                    try {
                        val googleSignInAccount: GoogleSignInAccount = signInAccountTask .getResult(ApiException::class.java)

                        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)

                        auth.signInWithCredential(authCredential)
                            .addOnCompleteListener(requireActivity()) { task ->
                                if (task.isSuccessful) {
                                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                                } else {
                                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                    } catch (e: ApiException) {
                        e.printStackTrace()
                        Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initUI() {
        binding.btnRegister.setOnClickListener (this::onRegister)
        binding.btnLogin.setOnClickListener (this::onLogin)
        binding.ivHidePassw.setOnClickListener (this::onHidePassword)
        binding.tvForgotPassword.setOnClickListener (this::onForgotPassword)
        binding.ivGoogle.setOnClickListener (this::onGoogleSignIn)
    }
    private fun onGoogleSignIn(v: View){
        val intent = gsc.signInIntent
        activityResultLauncher.launch(intent)
//        startActivityForResult(intent, 100)
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

    private fun onHidePassword(v:View) {
        isShowPassword = !isShowPassword
        showPassword(isShowPassword)
    }

    private fun onForgotPassword(v: View) {
        findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
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