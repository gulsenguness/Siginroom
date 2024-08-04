package com.example.ecommerce.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecommerce.R
import com.example.ecommerce.data.AppDatabase
import com.example.ecommerce.data.UserDAO
import com.example.ecommerce.databinding.FragmentSignInBinding
import kotlinx.coroutines.launch


class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var userDAO: UserDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view=binding.root
        userDAO=AppDatabase.getDatabase(requireContext()).userDao()

        binding.signin.setOnClickListener{
            loginUser()
        }
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gosignup.setOnClickListener {
            findNavController().navigate(R.id.SignIntoSignUp)
        }
    }

    fun loginUser(){
        val usernamein=binding.usernamein.text.toString()
        val passwordin=binding.passwordin.text.toString()

        if (usernamein.isNotEmpty() && passwordin.isNotEmpty()) {
            lifecycleScope.launch {
                val user = userDAO.login(usernamein,passwordin)
                if (user != null) {
                    binding.signin.setOnClickListener{
                        findNavController().navigate(R.id.sales)
                    }
                } else {
                    Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

}