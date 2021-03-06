package com.example.android.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.R.id.action_titleFragment_to_gameFragment
import com.example.android.navigation.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding: FragmentTitleBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        binding.playButton.setOnClickListener { view: View ->
//            // 1. verbose code here can be more concise and idiomatic using ktx...
//            Navigation.findNavController(view).navigate(R.id.action_titleFragment_to_gameFragment)
//            // 2. use ktx (kotlin extension) instead, ie.  androidx.navigation.findNavController
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
            // 3. the most concise way to handle a click event...
//            Navigation.createNavigateOnClickListener(action_titleFragment_to_gameFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController())
            || return super.onOptionsItemSelected(item)
    }
}