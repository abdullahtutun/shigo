package com.shi.shigo.view

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.shi.shigo.R
import com.shi.shigo.databinding.FragmentProfileBinding
import com.shi.shigo.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    @Inject
    lateinit var auth: FirebaseAuth
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container,false)

        binding.buttonLogOut.setOnClickListener {
            viewModel.logout()
        }

        viewModel.logoutResponse.observe(viewLifecycleOwner){response ->
            if(response){
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }

        return binding.root
    }

}