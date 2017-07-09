package com.tpb.hnk.presenters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tpb.hnk.R
import com.tpb.hnk.data.models.HNItem
import kotlinx.android.synthetic.main.viewholder_item.view.*

/**
 * Created by theo on 09/07/17.
 */
class ItemAdapter(val loader: ItemLoader) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), ItemReceiver {

    var recycler: RecyclerView? = null
    var emptyText: String? = null

    var data = ArrayList<Pair<Long, HNItem?>>(0)

    override fun receiveIds(ids: List<Long>) {
        data.clear()
        ids.forEach { data.add(Pair(it, null)) }
        notifyDataSetChanged()
    }

    override fun receiveItem(item: HNItem) {
        data.forEachIndexed { index, (first) ->
            if (first == item.id) {
                data[index] = Pair(first, item)
                notifyItemChanged(index)
                return
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recycler = recyclerView
        emptyText = recycler?.context?.getString(R.string.empty_text)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        if (item.second == null) {
            loader.loadItem(item.first)

            holder.title.text = emptyText
            holder.username.text = emptyText
            holder.domain.text = null
            holder.time.text = null
            holder.comments.text = emptyText

        } else {
            holder.title.text = item.second?.title
            holder.username.text = item.second?.by
            holder.domain.text = item.second?.domain()
            holder.comments.text = item.second?.descendants.toString()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.title
        val username: TextView = view.user
        val domain: TextView = view.domain
        val time: TextView = view.time
        val comments: TextView = view.comments

    }

}

interface ItemLoader {

    fun loadRange(ids: LongArray)

    fun loadItem(id: Long)

}

interface ItemReceiver {

    fun receiveIds(ids: List<Long>)

    fun receiveItem(item: HNItem)

}