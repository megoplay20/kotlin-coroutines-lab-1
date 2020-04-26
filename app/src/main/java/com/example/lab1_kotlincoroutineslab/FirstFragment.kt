package com.example.lab1_kotlincoroutineslab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1_kotlincoroutineslab.adapters.CountryHolidaysAdapter
import com.example.lab1_kotlincoroutineslab.viewModels.CountryHolidaysViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //https://developer.android.com/topic/libraries/architecture/viewmodel#kotlin
        val model: CountryHolidaysViewModel by activityViewModels()

        holidays_list.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL, false )

        model.holidays.observe(this.viewLifecycleOwner, Observer {
            holidays_list.adapter = CountryHolidaysAdapter(it)
                            fetch_holidays.isEnabled = true
        })


        fetch_holidays.setOnClickListener {
                fetch_holidays.isEnabled = false
                model.fetchHolidaysForCountry(country_code.text.toString()){
                    Snackbar.make(view,it, Snackbar.LENGTH_LONG).show()
                    fetch_holidays.isEnabled = true
                }
        }

    }
}
