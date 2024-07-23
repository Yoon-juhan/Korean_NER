package com.example.koner.fragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koner.R
import com.example.koner.viewmodel.NavBarViewModel

class NavBarFragment : Fragment() {

    companion object {
        fun newInstance() = NavBarFragment()
    }

    private val viewModel: NavBarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_nav_bar, container, false)
    }
}