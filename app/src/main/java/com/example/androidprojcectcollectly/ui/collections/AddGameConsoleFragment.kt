package com.example.androidprojcectcollectly.ui.collections

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.androidprojcectcollectly.*
import com.example.androidprojcectcollectly.databinding.FragmentAddGameConsoleBinding
import com.example.androidprojcectcollectly.entities.GameConsole


class AddGameConsoleFragment : Fragment() {
    private val gameConsoleViewModel: GameConsoleViewModel by viewModels {
        GameConsoleViewModelFactory((activity?.application as GameConsolesApplication).repository)
    }

    private lateinit var editGameConsoleView: EditText

    private var _binding: FragmentAddGameConsoleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddGameConsoleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        editGameConsoleView = binding.editGameConsole

        val button = binding.buttonSave
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editGameConsoleView.text)) {

                Toast.makeText(
                    context,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            } else {



                val gameConsole = GameConsole(null, editGameConsoleView.text.toString())

                gameConsoleViewModel.insert(gameConsole)

                (activity as MainActivity).navigate(R.id.navigation_home)

            }


        }
        // Inflate the layout for this fragment
        return root
    }

}