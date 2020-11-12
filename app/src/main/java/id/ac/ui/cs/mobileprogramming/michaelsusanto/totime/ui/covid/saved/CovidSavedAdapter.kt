package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.covid.saved

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.CovidCase
import kotlinx.android.synthetic.main.card_covidcase.view.*

class CovidSavedAdapter(
    private val context: CovidSavedFragment,
    private val savedCovidCases: ArrayList<CovidCase>
): RecyclerView.Adapter<CovidSavedAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidSavedAdapter.Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_covidcase, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CovidSavedAdapter.Holder, position: Int) {
        holder.view.date.text = savedCovidCases[position].date
        holder.view.positive_value.text = savedCovidCases[position].positive
        holder.view.death_value.text = savedCovidCases[position].death
        holder.view.hospitalized_value.text = savedCovidCases[position].hospitalized
        holder.view.recovered_value.text = savedCovidCases[position].recovered
    }

    override fun getItemCount(): Int = savedCovidCases.size

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var communicator: CovidSavedCommunicator
        init {
            view.btn_remove.setOnClickListener {
                val position = adapterPosition
                val clickedItem = savedCovidCases[position]
                communicator = context
                communicator.clickedItem(clickedItem)
                notify(position)
            }
        }

        private fun notify(position: Int) {
            savedCovidCases.removeAt(position)
            notifyDataSetChanged()
        }
    }
}