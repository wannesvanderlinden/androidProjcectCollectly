package com.example.androidprojcectcollectly.ui.profile

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojcectcollectly.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private var isEnabled = false
    val key_isEnabled = "isEnabled"


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


        var radio_group = binding.radioGroup

        //Darkmode switch
        var darkmodeSwitch = binding.darkMode

       //checking if dark mode was on and change the state of the switch
        if (this.activity?.getSharedPreferences("save", Context.MODE_PRIVATE)
                ?.getBoolean(key_isEnabled, false) == true
        ) {
            darkmodeSwitch.setChecked(true)
        }


        //Listener for nightmode
        darkmodeSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            //Looking when it is checked or not and than change it
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)


            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
            isEnabled = isChecked
            //Edit the preference to save the state of the switch and that dark mode is on
            var edit = this.activity?.getSharedPreferences("save", Context.MODE_PRIVATE)?.edit()

            edit?.putBoolean(key_isEnabled, isEnabled)
            //Async applying
            edit?.apply()
        })


        // Get radio group selected item using on checked change listener
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->

                val radio: RadioButton = root.findViewById(checkedId)
                if (radio.text == "Dutch" || radio.text == "Nederlands") {


                }
                Toast.makeText(
                    context, " On checked change :" +
                            " ${radio.text}",
                    Toast.LENGTH_SHORT
                ).show()
            })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}