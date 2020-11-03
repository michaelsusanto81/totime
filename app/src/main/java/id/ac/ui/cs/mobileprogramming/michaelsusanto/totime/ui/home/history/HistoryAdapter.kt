package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.home.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity
import kotlinx.android.synthetic.main.card_history.view.*

class HistoryAdapter(private val userActivities: List<UserActivity>): RecyclerView.Adapter<HistoryAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_history, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryAdapter.Holder, position: Int) {
        holder.view.user_activity.text = userActivities[position].name
        holder.view.place.text = userActivities[position].place
        holder.view.date.text = userActivities[position].dateStart

        val hours = userActivities[position].hours
        val minutes = userActivities[position].minutes
        val seconds = userActivities[position].seconds
        holder.view.time.text = "$hours:$minutes:$seconds"
    }

    override fun getItemCount(): Int = userActivities.size

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view)
}