package com.example.androidprojcectcollectly.ui.game

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.androidprojcectcollectly.*
import com.example.androidprojcectcollectly.databinding.FragmentAddGameBinding
import com.example.androidprojcectcollectly.entities.Game
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddGameFragment : Fragment() {

    private lateinit var editGameView: EditText
    private var _binding: FragmentAddGameBinding? = null
    private var not_exist = true
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
        val db = Firebase.firestore
        db.collection("games").get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")


                if (document.data.get("barcode") == arguments?.getString("barcode")) {
                    root.findViewById<TextView>(R.id.edit_game).text =
                        document.data.get("title").toString()
                    //val game = Game(null,document.data.get(""),arguments?.getString("gameConsole")
                    not_exist = false
                } else {
                    not_exist = true
                }
            }

        }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        val button = binding.buttonSave
        button.setOnClickListener {

            if (TextUtils.isEmpty(editGameView.text)) {
                Toast.makeText(
                    context,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val game =
                    Game(null, editGameView.text.toString(), arguments?.getString("gameConsole")!!)

                if (not_exist) {
                    val gamefire = hashMapOf("title" to editGameView.text.toString(), "barcode" to arguments?.getString("barcode"),"gameConsole" to arguments?.getString("gameConsole")!! )
                    db.collection("games").add(gamefire).addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                }

                gameViewModel.insert(game)

                (activity as MainActivity).navigate(R.id.navigation_games)
            }

        }
        return root
    }


}