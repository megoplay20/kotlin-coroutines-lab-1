package com.example.lab1_kotlincoroutineslab.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1_kotlincoroutineslab.R
import com.example.lab1_kotlincoroutineslab.models.LongestHoliday
import kotlinx.android.synthetic.main.holiday_layout.view.country_code
import kotlinx.android.synthetic.main.longest_holiday_layout.view.*

class LongestCountryHolidayAdapter(private val holidays:List<LongestHoliday>) :
    RecyclerView.Adapter<LongestCountryHolidayAdapter.LongestHolidayViewHolder>()
{
    class LongestHolidayViewHolder(val holidayLayout: ConstraintLayout) : RecyclerView.ViewHolder(holidayLayout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LongestHolidayViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.longest_holiday_layout, parent, false) as ConstraintLayout
        return LongestHolidayViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return holidays.count()
    }

    override fun onBindViewHolder(holder: LongestHolidayViewHolder, position: Int) {
        holder.holidayLayout.country_code.text = holidays[position].countryCode
        holder.holidayLayout.local_name.text = holidays[position].localName
        holder.holidayLayout.daysCount.text = holidays[position].days.toString()
    }


}