package com.team3.indexify.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.team3.indexify.R
import com.team3.indexify.databinding.FragmentHomeBinding
import com.team3.indexify.models.SensorInfoModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel

        val spinner: Spinner = binding.stationSpinner
        ArrayAdapter.createFromResource(
            this.requireActivity(),
            R.array.station_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.v("Spinner", "nothingSelected")
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val station = parent?.getItemAtPosition(position).toString()
                    homeViewModel.setStation(station)
                    homeViewModel.refreshView()
                }

            }
        }

        // ----------------------------------------------------------------------------------------
        // ----------------------------------------------------------------------------------------

        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            HomeOnBackPressedCallback(slidingPaneLayout)
        )

        binding.sensorTable.condCard.setOnClickListener {
            val sensorInfo = SensorInfoModel(title = "Conductivity", description = "Conductivity is a measure of the ability of water to pass an electrical current. Because dissolved salts and other inorganic chemicals conduct electrical current, conductivity increases as salinity increases.")
            homeViewModel.updateSelectedSensor(sensorInfo)
            val action = HomeFragmentDirections.actionNavigationHomeToSensorFragment()
            this.findNavController().navigate(action)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class HomeOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),
    SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }
    /**
     * Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
     */
    override fun handleOnBackPressed() {
        slidingPaneLayout.closePane()
    }

    /**
     * Called when a detail view's position changes.
     *
     * @param panel       The child view that was moved
     * @param slideOffset The new offset of this sliding pane within its range, from 0-1
     */
    override fun onPanelSlide(panel: View, slideOffset: Float) {
    }

    /**
     * Called when a detail view becomes slid completely open.
     *
     * @param panel The detail view that was slid to an open position
     */
    override fun onPanelOpened(panel: View) {
        isEnabled = true
    }

    /**
     * Called when a detail view becomes slid completely closed.
     *
     * @param panel The detail view that was slid to a closed position
     */
    override fun onPanelClosed(panel: View) {
        isEnabled = false
    }
}
