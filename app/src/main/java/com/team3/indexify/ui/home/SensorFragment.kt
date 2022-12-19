package com.team3.indexify.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team3.indexify.R
import com.team3.indexify.databinding.FragmentSensorBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SensorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SensorFragment : Fragment() {
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSensorBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSensorBinding.bind(view)

        // Attach an observer on the currentSport to update the UI automatically when the data
        // changes.
        homeViewModel.selectedSensor.observe(this.viewLifecycleOwner) {
            binding.titleDetail.text = it.title
            binding.newsDetail.text = it.description
        }
    }
}