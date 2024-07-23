package com.example.koner.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.koner.R
import com.example.koner.databinding.FragmentLoginBinding
import com.example.koner.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding?=null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentLoginBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding.loginBtn.setOnClickListener {
                val id = binding.loginId.text.toString()
                val pw = binding.loginPw.text.toString()
                Log.d("요청", id + pw)
                viewModel.login(id, pw)
            }

            viewModel.loginResponse.observe(viewLifecycleOwner) { response ->
                Log.d("요청", response.toString())

                if (response.status == true) {
                    viewModel.loginResponse.removeObservers(viewLifecycleOwner)
                    viewModel.resetLoginResponse()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else if (response.id != " " && response.status == false) {
                    Toast.makeText(requireContext(), "없는 회원입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            binding.joinBtn.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_joinFragment)
            }

        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }