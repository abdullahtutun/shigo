package com.shi.shigo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.shi.shigo.R
import com.shi.shigo.databinding.FragmentForgotPasswordBinding
import com.shi.shigo.viewmodel.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container,false)

        initUI()

        viewModel.forgotPasswordResponse.observe(viewLifecycleOwner){ response ->
            if(response){
                Toast.makeText(requireContext(), R.string.successful_reset_email, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    private fun initUI() {
        binding.btnSubmit.setOnClickListener (this::onBtnSubmit)
    }

    private fun onBtnSubmit(v: View) {
        val email = binding.etEmail.text.toString().trim()

        if(email.isNotEmpty()){
            viewModel.resetPassword(email)
        } else {
            Toast.makeText(requireContext(), R.string.warning_email, Toast.LENGTH_SHORT).show()
        }

    }


}