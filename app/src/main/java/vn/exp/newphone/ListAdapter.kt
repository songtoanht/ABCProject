package vn.exp.newphone

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.exp.newphone.databinding.ItemEventBinding
import vn.exp.newphone.model.NumberPhone

class ListAdapter(val events: List<NumberPhone>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        val viewHolder = ItemViewHolder(v)
//        viewHolder.binding.root.setOnClickListener {
//            var position = viewHolder.adapterPosition
//            if (::onItemClickListener.isInitialized) {
//                onItemClickListener.onItemClick(listData[position - 1], position - 1)
//            }
//        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as ItemViewHolder
        h.data(events[position])
    }

    class ItemViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val binding: ItemEventBinding = DataBindingUtil.bind(view)!!

        fun data(news: NumberPhone) {
            binding.item = news
            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(data: NumberPhone, position: Int)

        fun onCloseNewsList()
    }

}