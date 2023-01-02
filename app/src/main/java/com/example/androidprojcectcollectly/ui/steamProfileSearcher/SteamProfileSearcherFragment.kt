package com.example.androidprojcectcollectly.ui.steamProfileSearcher

import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidprojcectcollectly.MainActivity
import com.example.androidprojcectcollectly.R
import com.example.androidprojcectcollectly.api.SteamApi
import com.example.androidprojcectcollectly.databinding.FragmentSteamProfileSearcherBinding
import com.example.androidprojcectcollectly.models.SteamProfile
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException

class SteamProfileSearcherFragment : Fragment() {

    private var _binding: FragmentSteamProfileSearcherBinding? = null
    private lateinit var searchSteamProfile: EditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //define inflatertransition when switching in fragments
        val inflaterTran = TransitionInflater.from(requireContext())
        exitTransition = inflaterTran.inflateTransition(R.transition.fade)
        enterTransition = inflaterTran.inflateTransition(R.transition.slide_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)




        _binding = FragmentSteamProfileSearcherBinding.inflate(inflater, container, false)
        searchSteamProfile = binding.SteamIdField

        val root: View = binding.root
        binding.noInternet.setVisibility(View.GONE)

        /**
         * Define listner when user press the search profile button to check it is not empty
         * and makes an api call to the steam api if it is not empty and does have internet connection
         */
        binding.searchButton.setOnClickListener {

            if (TextUtils.isEmpty(searchSteamProfile.text)) {
                Toast.makeText(
                    context,
                    R.string.error_steamProfile_empty,
                    Toast.LENGTH_LONG
                ).show()
            } else {

                //create connectivity manager to check if the device is connected to the internet
                val connectivityManager =
                    getSystemService(context!!, ConnectivityManager::class.java)
                if (connectivityManager?.getNetworkCapabilities(connectivityManager?.getActiveNetwork()) != null) {
                    //Delete the no internet banner
                    binding.noInternet.setVisibility(View.GONE)
                    //Get all the information of the api and put it in a bundel to show in the next fragment
                    var steamId = searchSteamProfile.text.toString()
                    SteamApi.getData(steamId) { profile: SteamProfile? ->
                        if (profile != null && profile.response.players.isNotEmpty()) {
                            val bundle = Bundle()
                            println(profile)
                            bundle.putString("personame", profile.response.players[0].personaname)
                            bundle.putString(
                                "loccountrycode",
                                profile.response.players[0].loccountrycode
                            )
                            bundle.putString("steamid", profile.response.players[0].steamid)
                            bundle.putString("avatarLink", profile.response.players[0].avatarfull)

                            (activity as MainActivity).navigate(
                                R.id.navigation_Steam_Profile,
                                bundle
                            )
                        } else {
                            Toast.makeText(
                                context,
                                R.string.Steam_profile_not_found,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    //Add the no internet banner because it is not connected to the internet
                    binding.noInternet.setVisibility(View.VISIBLE)

                    Toast.makeText(
                        context,
                        R.string.No_internet_connection,
                        Toast.LENGTH_LONG
                    ).show()
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