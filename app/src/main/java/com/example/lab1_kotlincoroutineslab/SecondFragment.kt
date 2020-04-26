package com.example.lab1_kotlincoroutineslab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1_kotlincoroutineslab.adapters.LongestCountryHolidayAdapter
import com.example.lab1_kotlincoroutineslab.viewModels.CountryHolidaysViewModel
import com.example.lab1_kotlincoroutineslab.viewModels.LongestHolidaysViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: LongestHolidaysViewModel by activityViewModels()

        longest_holidays_list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false )



        model.longestHolidays.observe(viewLifecycleOwner, Observer {
            collectDataButton.isEnabled = true
            longest_holidays_list.adapter = LongestCountryHolidayAdapter(it)
        })

        collectDataButton.setOnClickListener {
                model.collectHolidaysStatistics(county_list.text.toString()){Snackbar.make(view,it, Snackbar.LENGTH_LONG).show()}
            collectDataButton.isEnabled = false
        }

    }
}
