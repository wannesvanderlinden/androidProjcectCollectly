package com.example.androidprojcectcollectly.ui.priceChecker

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojcectcollectly.MainActivity
import com.example.androidprojcectcollectly.R
import com.example.androidprojcectcollectly.api.SteamApi
import com.example.androidprojcectcollectly.databinding.FragmentPricecheckerBinding
import com.example.androidprojcectcollectly.models.SteamProfile

class PriceCheckerFragment : Fragment() {

    private var _binding: FragmentPricecheckerBinding? = null
    private lateinit var searchSteamProfile: EditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentPricecheckerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        searchSteamProfile = binding.SteamIdField

        binding.searchButton.setOnClickListener {
            //Todo send request to api of steam to get game hystory of the profile
            if (TextUtils.isEmpty(searchSteamProfile.text)) {
                Toast.makeText(
                    context,
                    "Don't leave the steamId empty",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                var steamId = searchSteamProfile.text.toString()
                SteamApi.getData(steamId){profile: SteamProfile? ->
                    if(profile != null && profile.response.players.isNotEmpty()){
                        val bundle = Bundle()
                        println(profile)
                        bundle.putString("personame",profile.response.players[0].personaname)
                        bundle.putString("loccountrycode",profile.response.players[0].loccountrycode)
                        bundle.putString("steamid",profile.response.players[0].steamid)
                        bundle.putString("avatarLink",profile.response.players[0].avatarfull)

                        (activity as MainActivity).navigate(R.id.navigation_Steam_Profile,bundle)
                    }
                    else{
                        Toast.makeText(
                            context,
                            "Player not found",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}