package com.example.koner.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.koner.R
import com.example.koner.databinding.FragmentHomeBinding
import com.example.koner.viewmodel.HomeViewModel
import com.example.koner.viewmodel.LoginViewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

        private var _binding: FragmentHomeBinding?=null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding.predictBtn.setOnClickListener {
                val text = binding.inputText.text.toString()

                Log.d("텍스트 요청", text)
                viewModel.predict(text)
            }

            viewModel.predictResponse.observe(viewLifecycleOwner) { response ->

                Log.d("텍스트 응답", response.toString())
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }