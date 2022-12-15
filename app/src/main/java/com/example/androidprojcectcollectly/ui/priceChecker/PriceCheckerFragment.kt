package com.example.androidprojcectcollectly.ui.priceChecker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojcectcollectly.databinding.FragmentPricecheckerBinding

class PriceCheckerFragment : Fragment() {

    private var _binding: FragmentPricecheckerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentPricecheckerBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.searchButton.setOnClickListener {
            //Todo send request to api of steam to get game hystory of the profile

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}