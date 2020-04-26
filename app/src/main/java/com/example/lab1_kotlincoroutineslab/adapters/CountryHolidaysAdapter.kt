package com.example.lab1_kotlincoroutineslab.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1_kotlincoroutineslab.R
import com.example.lab1_kotlincoroutineslab.models.DisplayHoliday
import kotlinx.android.synthetic.main.holiday_layout.view.*

class CountryHolidaysAdapter(private val holidays:List<DisplayHoliday>) :
    RecyclerView.Adapter<CountryHolidaysAdapter.HolidayViewHolder>()
{
    class HolidayViewHolder(val holidayLayout: ConstraintLayout) : RecyclerView.ViewHolder(holidayLayout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.holiday_layout, parent, false) as ConstraintLayout
        return HolidayViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return holidays.count()
    }

    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        holder.holidayLayout.description.text = holidays[position].description
        holder.holidayLayout.type.text = holidays[position].type
        holder.holidayLayout.country_code.text = holidays[position].countryCode
    }


}