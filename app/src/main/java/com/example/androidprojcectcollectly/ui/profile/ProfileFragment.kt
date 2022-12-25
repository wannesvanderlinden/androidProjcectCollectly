package com.example.androidprojcectcollectly.ui.profile

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojcectcollectly.*
import com.example.androidprojcectcollectly.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private val gameConsoleViewModel: GameConsoleViewModel by viewModels {
        GameConsoleViewModelFactory((activity?.application as GameConsolesApplication).repository)
    }
    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory((activity?.application as GameConsolesApplication).game_repository)
    }

    private var _binding: FragmentProfileBinding? = null
    private var isEnabled = false
    val KEY_IS_ENABLED = "isEnabled"


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //define inflatertransition
        val inflaterTran = TransitionInflater.from(requireContext())
        exitTransition = inflaterTran.inflateTransition(R.transition.fade)
        enterTransition = inflaterTran.inflateTransition(R.transition.slide_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //Darkmode switch
        var darkmodeSwitch = binding.darkMode

        var gameConsoleDeleteBtn = binding.btnDeleteGameConsole
        var gameDeleteBtn = binding.buttonDeleteGame
        var deleteGame = binding.editGameField

        //checking if dark mode was on and change the state of the switch
        if (this.activity?.getSharedPreferences("save", Context.MODE_PRIVATE)
                ?.getBoolean(KEY_IS_ENABLED, false) == true
        ) {
            darkmodeSwitch.setChecked(true)
        }

        gameConsoleDeleteBtn.setOnClickListener {
            gameConsoleViewModel.deleteAll()
            gameViewModel.deleteAll()
            Toast.makeText(
                context,
                "gameConsoles are deleted and all the games",
                Toast.LENGTH_LONG
            ).show()


        }
        gameDeleteBtn.setOnClickListener {
            if (TextUtils.isEmpty(deleteGame.text)) {

                Toast.makeText(
                    context,
                    "You can not leave the gameConsole Field blank",
                    Toast.LENGTH_LONG
                ).show()
            }
            else{
                gameViewModel.deleteGamesOfGameconsole(deleteGame.text.toString())
                Toast.makeText(
                    context,
                    "Successful deleted",
                    Toast.LENGTH_LONG
                ).show()
            }
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

            edit?.putBoolean(KEY_IS_ENABLED, isEnabled)
            //Async applying
            edit?.apply()
        })





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}