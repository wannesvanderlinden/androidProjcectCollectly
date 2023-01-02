package com.example.androidprojcectcollectly.ui.steamProfileSearcher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidprojcectcollectly.R
import com.example.androidprojcectcollectly.databinding.FragmentSteamProfileBinding
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SteamProfileFragment : Fragment() {
    private var _binding: FragmentSteamProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSteamProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Load the image of the steam profile with the link that was given by the bundel.
        Picasso.get().load(arguments?.get("avatarLink").toString()).into(binding.imageView2)

        //Getting the steam profile data of the bundle
        binding.steamProfileName.text = arguments?.get("personame").toString()
        binding.SteamId.text = arguments?.get("steamid").toString()

        //Checking if the country code is not private
        if (arguments?.get("loccountrycode") != null) {
            binding.Country.text = arguments?.get("loccountrycode").toString()

        } else {
            var country = getString(R.string.Country_unavailable)
            binding.Country.text = country

        }


        // Inflate the layout for this fragment
        return root
    }


}