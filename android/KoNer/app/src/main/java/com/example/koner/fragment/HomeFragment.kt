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
import android.text.Spannable
import android.text.SpannableString
import android.graphics.Color
import android.text.style.ForegroundColorSpan
import android.widget.TextView

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var text = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navBar.myPageBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mypageFragment)
        }

        binding.predictBtn.setOnClickListener {
            text = binding.inputText.text.toString()

            Log.d("텍스트 요청", text)
            viewModel.predict(text)
        }

        viewModel.predictResponse.observe(viewLifecycleOwner) { response ->

            val entities = response.ner

            val entityTypes = mapOf(
                "PS" to Color.parseColor("#eeff00"),  // person
                "FD" to Color.parseColor("#47ffd7"),  // study_field
                "TR" to Color.parseColor("#0d47a1"),  // theory
                "AF" to Color.parseColor("#6a1b9a"),  // artifacts
                "OGG" to Color.parseColor("#ff82a1"), // organization
                "LC" to Color.parseColor("#ea4664"),  // location
                "CV" to Color.parseColor("#3bff3b9a"),// civilization
                "DT" to Color.parseColor("#9899e9"),  // date
                "TI" to Color.parseColor("#abccb6"),  // time
                "QT" to Color.parseColor("#fff27c"),  // quantity
                "EV" to Color.parseColor("#ff5e66"),  // event
                "AM" to Color.parseColor("#b4bed2"),  // animal
                "PT" to Color.parseColor("#4a148c"),  // plant
                "MT" to Color.parseColor("#01579b"),  // material
                "TM" to Color.parseColor("#a1e1ff")   // term
            )

            binding.person.text = ""
            binding.studyField.text = ""
            binding.theory.text = ""
            binding.artifacts.text = ""
            binding.organization.text = ""
            binding.location.text = ""
            binding.civilization.text = ""
            binding.date.text = ""
            binding.time.text = ""
            binding.quantity.text = ""
            binding.event.text = ""
            binding.animal.text = ""
            binding.plant.text = ""
            binding.material.text = ""
            binding.term.text = ""

            val spannable = SpannableString(text)

            entities.keys.forEach { key ->
                val entityList = entities[key]

                if (entityList is List<*>) {
                    val result = entities[key].toString().replace("[", "").replace("]", "").replace(", ", "\n")
                    when (key) {
                        "PS"  -> binding.person.text = result
                        "FD"  -> binding.studyField.text = result
                        "TR"  -> binding.theory.text = result
                        "AF"  -> binding.artifacts.text = result
                        "OGG" -> binding.organization.text = result
                        "LC"  -> binding.location.text = result
                        "CV"  -> binding.civilization.text = result
                        "DT"  -> binding.date.text = result
                        "TI"  -> binding.time.text = result
                        "QT"  -> binding.quantity.text = result
                        "EV"  -> binding.event.text = result
                        "AM"  -> binding.animal.text = result
                        "PT"  -> binding.plant.text = result
                        "MT"  -> binding.material.text = result
                        "TM"  -> binding.term.text = result
                    }

                    for (entity in entityList) {
                        if (entity is String) {
                            val startIndex = text.indexOf(entity)
                            if (startIndex >= 0) {
                                val endIndex = startIndex + entity.length
                                spannable.setSpan(
                                    ForegroundColorSpan(entityTypes[key] ?: Color.BLACK),
                                    startIndex,
                                    endIndex,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                            }
                        }
                    }
                }

            }

            Log.d("span 응답", spannable.toString())
            binding.inputText.setText(spannable, TextView.BufferType.SPANNABLE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}