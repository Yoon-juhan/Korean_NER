package com.example.koner.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
//import com.example.koner.HistoryAdapter
import com.example.koner.R
import com.example.koner.UserSharedPreferences
import com.example.koner.databinding.FragmentLoginBinding
import com.example.koner.databinding.FragmentMypageBinding
import com.example.koner.viewmodel.HomeViewModel
import com.example.koner.viewmodel.MypageViewModel

class MypageFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val viewModel: MypageViewModel by viewModels()
//    private lateinit var adapter: HistoryAdapter

    private var _binding: FragmentMypageBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("응답 기록", UserSharedPreferences.sharedPreferences.getString("id", null).toString())
        viewModel.getHistory(UserSharedPreferences.sharedPreferences.getString("id", null).toString())

        viewModel.historyResponse.observe(viewLifecycleOwner) { historyList ->
//            adapter = HistoryAdapter(historyList)
//            recyclerView.adapter = adapter
            Log.d("응답 기록", historyList.toString())
        }

        binding.myPageId.text = Editable.Factory.getInstance().newEditable(UserSharedPreferences.sharedPreferences.getString("id", null))
        binding.myPagePw.text = Editable.Factory.getInstance().newEditable(UserSharedPreferences.sharedPreferences.getString("pw", null))
        binding.myPageNickname.text = Editable.Factory.getInstance().newEditable(UserSharedPreferences.sharedPreferences.getString("nickname", null))

        Log.d("마이페이지 응답", UserSharedPreferences.sharedPreferences.getString("id", null).toString())
        binding.navBar.homeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mypageFragment_to_homeFragment)
        }

        // 로그아웃 버튼
        binding.logoutBtn.setOnClickListener {
            val options = NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true) // true 하면 도착점도 pop
                .build()

            findNavController().navigate(R.id.action_mypageFragment_to_loginFragment, null, options)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}