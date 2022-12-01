package com.example.androidprojcectcollectly.ui.game

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
import com.example.androidprojcectcollectly.databinding.FragmentAddGameBinding
import com.example.androidprojcectcollectly.entities.Game


class AddGameFragment : Fragment() {

    private lateinit var editGameView: EditText
    private var _binding: FragmentAddGameBinding? = null
    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory((activity?.application as GameConsolesApplication).game_repository)
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddGameBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Inflate the layout for this fragment
        editGameView = binding.editGame

        val button = binding.buttonSave
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editGameView.text)) {
                Toast.makeText(
                    context,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val game = Game(null,editGameView.text.toString(),arguments?.getString("gameConsole")!!)


                gameViewModel.insert(game)

                (activity as MainActivity).navigate(R.id.navigation_games)
            }

        }
        return root
    }


}