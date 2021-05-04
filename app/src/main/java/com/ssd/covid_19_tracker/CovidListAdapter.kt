package com.ssd.covid_19_tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CovidListAdapter : RecyclerView.Adapter<myHolder>() {

    private val items : ArrayList<Covid> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_covid, parent, false)

        return myHolder(view)
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {
        holder.myState.text = items[position].state
        holder.myConfirmed.text = items[position].confirmed
        holder.myActive.text = items[position].active
        holder.myRecovered.text = items[position].recovered
        holder.myDeaths.text = items[position].deaths
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateCovid(updatedNews: ArrayList<Covid>){
        items.clear()
        items.addAll(updatedNews)
        notifyDataSetChanged()
    }

}

class myHolder(myView: View): RecyclerView.ViewHolder(myView){
    val myState = myView.findViewById<TextView>(R.id.stateId)
    val myConfirmed = myView.findViewById<TextView>(R.id.confirmedId)
    val myActive = myView.findViewById<TextView>(R.id.activeId)
    val myRecovered = myView.findViewById<TextView>(R.id.recoveredId)
    val myDeaths = myView.findViewById<TextView>(R.id.deathsId)


}