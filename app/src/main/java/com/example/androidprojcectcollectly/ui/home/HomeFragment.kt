package com.example.androidprojcectcollectly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojcectcollectly.R
import com.example.androidprojcectcollectly.databinding.FragmentHomeBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var addButtonScanner = view?.findViewById<Button>(R.id.AddItemScanner)
        addButtonScanner?.setOnClickListener {
            var scanoption = ScanOptions()
            scanoption.setDesiredBarcodeFormats(ScanOptions.EAN_8, ScanOptions.EAN_13, ScanOptions.UPC_E)


            scanoption.setOrientationLocked(false)
            barcodeLauncher.launch(scanoption)


        }


        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }
    //zie documentatie https://github.com/journeyapps/zxing-android-embedded
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
            // this.findViewById<TextView>(R.id.resultText).text = result.contents
        } else {
            Toast.makeText(
                activity,
                "Scanned Upc: " + result.contents,
                Toast.LENGTH_LONG
            ).show()
            //this.findViewById<TextView>(R.id.resultText).text = result.contents
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}