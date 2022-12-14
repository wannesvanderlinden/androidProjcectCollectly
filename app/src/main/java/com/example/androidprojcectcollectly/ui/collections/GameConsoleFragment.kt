package com.example.androidprojcectcollectly.ui.collections

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidprojcectcollectly.*
import com.example.androidprojcectcollectly.adapters.GameConsoleListAdapter
import com.example.androidprojcectcollectly.databinding.FragmentCollectionsBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult

class GameConsoleFragment : Fragment() {
    private val gameConsoleViewModel: GameConsoleViewModel by viewModels {
        GameConsoleViewModelFactory((activity?.application as GameConsolesApplication).repository)
    }


    private var _binding: FragmentCollectionsBinding? = null

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

        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //Define the recyclerview
        val recyclerView = binding.recyclerview

        //create the adapter and let some listeners with there attribute
        val adapter = GameConsoleListAdapter() {
            val bundle = Bundle()
            bundle.putString("gameConsole", it)

            (activity as MainActivity).navigate(R.id.navigation_games, bundle)

        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        /**
         *Observer when gameconsoles are deleted or added
         */
        gameConsoleViewModel.allGameConsoles.observe(viewLifecycleOwner) { gameConsoles ->
            // Update the cached copy of the gameconsoles in the adapter.
            gameConsoles.let { adapter.submitList(it) }
        }
        val fab = binding.fab
        /**
         *Listner when you press the plus button and will navigate you to the add gameconsole fragment
         */
        fab.setOnClickListener {
            val bundle = Bundle()
            (activity as MainActivity).navigate(R.id.navigation_add_gameConsole, bundle)


        }


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}