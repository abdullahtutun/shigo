package com.shi.shigo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shi.shigo.databinding.FragmentBasketBinding
import com.shi.shigo.databinding.FragmentLoginBinding
import com.shi.shigo.databinding.FragmentRegisterBinding

class BasketFragment : Fragment() {
    private lateinit var binding: FragmentBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(layoutInflater, container,false)

        return binding.root
    }

}