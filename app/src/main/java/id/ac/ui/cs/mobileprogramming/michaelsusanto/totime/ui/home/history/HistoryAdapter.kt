package id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.ui.home.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.R
import id.ac.ui.cs.mobileprogramming.michaelsusanto.totime.data.model.UserActivity
import kotlinx.android.synthetic.main.card_history.view.*

class HistoryAdapter(
    private val context: HistoryFragment,
    private val userActivities: ArrayList<UserActivity>
): RecyclerView.Adapter<HistoryAdapter.Holder>() {
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

        val hours = userActivities[position].hours.toString().padStart(2, '0')
        val minutes = userActivities[position].minutes.toString().padStart(2, '0')
        val seconds = userActivities[position].seconds.toString().padStart(2, '0')
        holder.view.time.text = "$hours:$minutes:$seconds"
    }

    override fun getItemCount(): Int = userActivities.size

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var communicator: HistoryCommunicator
        init {
            view.btn_remove.setOnClickListener {
                val position = adapterPosition
                val clickedItem = userActivities[position]
                communicator = context
                communicator.clickedItem(clickedItem)
                notify(position)
            }
        }

        private fun notify(position: Int) {
            userActivities.removeAt(position)
            notifyDataSetChanged()
        }
    }
}