package ru.zemlyanaya.getonbus.mainactivity.routing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fav_route_card.view.*
import ru.zemlyanaya.getonbus.R
import ru.zemlyanaya.getonbus.mainactivity.database.FavRoute

class FavRoutesRecyclerViewAdapter constructor(private val onCardClickListener: (FavRoute) -> Unit):
    RecyclerView.Adapter<FavRoutesRecyclerViewAdapter.RouteCardViewHolder>() {

    private var favRoutes = ArrayList<FavRoute>()

    inner class RouteCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val routeName: TextView = itemView.routeName
        val routeDestination: TextView = itemView.routeDestination
        val routeIcon: ImageButton = itemView.routeIcon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteCardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fav_route_card, parent, false)
        return RouteCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RouteCardViewHolder, position: Int) {
        val current = favRoutes[position]
        holder.itemView.setOnClickListener { onCardClickListener.invoke(current) }
        holder.routeName.text = current.name
        holder.routeDestination.text = current.destination
        holder.routeIcon.setImageResource(current.icon)
    }

    internal fun setData(routes: ArrayList<FavRoute>) {
        this.favRoutes = routes
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        favRoutes.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: FavRoute, position: Int) {
        favRoutes.add(position, item)
        notifyItemInserted(position)
    }

    fun getData(): ArrayList<FavRoute> {
        return favRoutes
    }

    override fun getItemCount() = favRoutes.size

}