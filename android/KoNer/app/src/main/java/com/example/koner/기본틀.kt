//package com.example.koner
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.example.shortnews.databinding.FragmentFindPwBinding
//
//class 기본틀 {
//
//
//    class FindPwFragment : Fragment() {
//        // 여기부터
//        private var _binding: FragmentFindPwBinding?=null
//        private val binding get() = _binding!!
//
//        override fun onCreateView(
//            inflater: LayoutInflater, container: ViewGroup?,
//            savedInstanceState: Bundle?
//        ): View? {
//            _binding = FragmentFindPwBinding.inflate(inflater, container, false)
//            return binding.root
//        }
//
//        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//            super.onViewCreated(view, savedInstanceState)
//
//        }
//
//        override fun onDestroyView() {
//            super.onDestroyView()
//            _binding = null
//        }
//
//    }
//
//
//}