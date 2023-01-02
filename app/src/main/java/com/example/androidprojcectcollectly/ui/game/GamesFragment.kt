package com.example.androidprojcectcollectly.ui.game

import android.content.ContentValues
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidprojcectcollectly.*
import com.example.androidprojcectcollectly.adapters.GameListAdapter
import com.example.androidprojcectcollectly.databinding.FragmentGamesBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class GamesFragment : Fragment() {

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory((activity?.application as GameConsolesApplication).game_repository)
    }
    private var _binding: FragmentGamesBinding? = null

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //define inflatertransition
        val inflaterTran = TransitionInflater.from(requireContext())

        //Define recyclerView
        val recyclerView = binding.recyclerview

        //create the adapter and let some listeners with there attribute
        val adapter = GameListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        /**
         * Adding observer when the data change
         */
        gameViewModel.allGames.observe(viewLifecycleOwner) { game ->

            // Update the cached copy of the words in the adapter.

            //get the gameConsole of the intent to filter
            var console = arguments?.getString("gameConsole")
            game.let { adapter.submitList(it.filter { singleGame -> singleGame.gameConsole == console }) }

        }
        val fab = binding.fab

        /**
         * Adding listener to open the barcode scanner
         */
        fab.setOnClickListener {
            var scanoption = ScanOptions()
            scanoption.setDesiredBarcodeFormats(
                ScanOptions.EAN_8,
                ScanOptions.EAN_13,
                ScanOptions.UPC_E
            )


            scanoption.setOrientationLocked(false)
            barcodeLauncher.launch(scanoption)


        }

        //findViewById<TextView>(R.id.gameConsoleText).text = intent.getStringExtra("gameConsole")
        // Inflate the layout for this fragment
        return root
    }

    /**
     *See documentatie https://github.com/journeyapps/zxing-android-embedded
     */
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()

            // this.findViewById<TextView>(R.id.resultText).text = result.contents
        } else {
            Toast.makeText(
                context,
                "Scanned Upc: " + result.contents,
                Toast.LENGTH_LONG
            ).show()
            val bundle = Bundle()
            bundle.putString("barcode", result.contents)
            bundle.putString("gameConsole", arguments?.getString("gameConsole"))

            (activity as MainActivity).navigate(R.id.navigation_add_games, bundle)


        }
    }

}