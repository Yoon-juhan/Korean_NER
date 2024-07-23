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
import com.example.koner.databinding.FragmentJoinBinding
import com.example.koner.databinding.FragmentLoginBinding
import com.example.koner.viewmodel.JoinViewModel
import com.example.koner.viewmodel.LoginViewModel

class JoinFragment : Fragment() {

    private val viewModel: JoinViewModel by viewModels()

    private var _binding: FragmentJoinBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.joinBtn.setOnClickListener {
            val id = binding.joinId.text.toString()
            val pw = binding.joinPw.text.toString()
            val nickname = binding.joinNickname.text.toString()

            Log.d("요청", id + pw)
            viewModel.join(id, pw, nickname)
        }

        viewModel.joinResponse.observe(viewLifecycleOwner) { response ->
            if (response.status == true) {
                Toast.makeText(requireContext(), "가입 성공! 로그인 해주세요", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_joinFragment_to_loginFragment)
            } else if (response.status == false) {
                Toast.makeText(requireContext(), "가입 실패!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_joinFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}