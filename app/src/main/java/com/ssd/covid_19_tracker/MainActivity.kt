package com.ssd.covid_19_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: CovidListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = CovidListAdapter()
        recyclerView.adapter = mAdapter
        loadCovidData()

        var swipeLayout: SwipeRefreshLayout
//        For swipe down to Refresh
        swipeRefreshLayout.setOnRefreshListener {
            loadCovidData()
            mAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun loadCovidData() {
        val url = "https://api.covid19india.org/data.json"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->

                    val covidJsonArray = response.getJSONArray("statewise")
                    val covidArray = ArrayList<Covid>()
                    for (i in 0 until covidJsonArray.length()){
                        val covidJsonObject = covidJsonArray.getJSONObject(i)
                        val covid = Covid(
                                covidJsonObject.getString("state"),
                                covidJsonObject.getString("confirmed"),
                                covidJsonObject.getString("active"),
                                covidJsonObject.getString("recovered"),
                                covidJsonObject.getString("deaths")
                        )
                        covidArray.add(covid)
                    }
                    mAdapter.updateCovid(covidArray)
                },
                { error ->
                    Toast.makeText(this, "Oooopppssss Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}