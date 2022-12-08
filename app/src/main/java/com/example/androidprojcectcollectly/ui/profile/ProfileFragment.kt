package com.example.androidprojcectcollectly.ui.profile

import android.os.Bundle
import android.os.LocaleList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojcectcollectly.MainActivity
import com.example.androidprojcectcollectly.databinding.FragmentProfileBinding
import com.example.androidprojcectcollectly.languageLogic.LanguageChanger
import java.util.Locale

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


       var radio_group= binding.radioGroup
        // Get radio group selected item using on checked change listener
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->

                val radio: RadioButton = root.findViewById(checkedId)
                if(radio.text=="Dutch" || radio.text =="Nederlands"){
                    val locale = Locale("nl")
                    Locale.setDefault(locale)

                    var resources = context?.resources

                    var configuration = resources?.configuration

                    configuration?.locale = locale
                    configuration?.setLayoutDirection(locale)

                    resources?.updateConfiguration(configuration, resources.displayMetrics)

                }
                Toast.makeText(context," On checked change :"+
                        " ${radio.text}",
                    Toast.LENGTH_SHORT).show()
            })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}