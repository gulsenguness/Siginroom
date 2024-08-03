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
import com.example.ecommerce.data.User
import com.example.ecommerce.data.UserDAO
import com.example.ecommerce.databinding.FragmentSignUpBinding
import kotlinx.coroutines.launch


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var userDAO: UserDAO


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view=binding.root
        userDAO = AppDatabase.getDatabase(requireContext()).userDao() //DAO nesnesini almamızı sağlar

        binding.createaccount.setOnClickListener{
            record()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gosignin.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }
    }

    fun record(){
        val username=binding.username.text.toString()
        val email=binding.emailup.text.toString()
        val password=binding.passwordup.text.toString()
        val password2=binding.passwordup2.text.toString()

        if (password != password2){
            Toast.makeText(requireContext(),"Your passwords do not match",Toast.LENGTH_SHORT).show()
            //Buraya signıne geçiş yapıyor onu engelle
        }

        if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            val user = User(username = username, email = email, password = password) //Yeni kullanıcı adı oluşturma
            lifecycleScope.launch { //Veri tabanına kullanıcıyı ekleriz.
                try {
                    userDAO.insert(user)
                    Toast.makeText(requireContext(), "User Registered", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.signUptosignin)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Registration failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }


}