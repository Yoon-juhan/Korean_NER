package com.example.koner.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koner.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

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
            Log.d("요청", "홈 화면")
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }