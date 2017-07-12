package com.tpb.hnk.presenters

import android.content.res.Resources
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.format.DateUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tpb.hnk.R
import com.tpb.hnk.data.ItemLoader
import com.tpb.hnk.data.models.HNItem
import com.tpb.hnk.util.info
import kotlinx.android.synthetic.main.viewholder_item.view.*

/**
 * Created by theo on 09/07/17.
 */
class ItemAdapter(val loader: ItemLoader, val resources: Resources) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), ItemReceiver {

    var recycler: RecyclerView? = null
    val emptyText: String = resources.getString(R.string.empty_text)
    val infoFormat: String = resources.getString(R.string.format_item_info)
    val loadFailText: String = resources.getString(R.string.error_cannot_load_item)

    var data = ArrayList<Triple<Long, HNItem?, Boolean>>(0)

    override fun receiveIds(ids: List<Long>) {
        data.clear()
        ids.forEach { data.add(Triple(it, null, false)) }
        notifyDataSetChanged()
    }

    override fun receiveItem(item: HNItem) {
        data.forEachIndexed { index, (first) ->
            if (first == item.id) {
                data[index] = Triple(first, item, false)
                notifyItemChanged(index)
                return
            }
        }
    }

    override fun itemLoadError(id: Long) {
        data.forEachIndexed { index, (first) ->
            if (first == id) {
                data[index] = Triple(id, null, true)
                notifyItemChanged(index)
                return
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recycler = recyclerView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        if (item.second == null) {
            if (item.third) {
                holder.title.text = loadFailText
            } else {
                holder.title.text = emptyText
                loader.loadItem(item.first)
            }
            holder.info.text = emptyText
            holder.comments.text = emptyText

        } else {
            holder.title.text = item.second?.title
            val info = SpannableString(
                    String.format(infoFormat, item.second?.by,
                        item.second?.domain(resources),
                        DateUtils.getRelativeTimeSpanString(1000 * (item.second?.time?: 0))
                )
            )
            info.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorAccent)), 0, item.second?.by?.length ?: 0, 0)
            info.setSpan(StyleSpan(Typeface.BOLD), 0, item.second?.by?.length ?: 0, 0)
            holder.info.text = info


            holder.comments.text = resources.getQuantityString(
                    R.plurals.plural_comments,
                    item.second?.descendants ?: 0,
                    item.second?.descendants)
        }
        holder.itemView.setOnClickListener {
            info(item.second?.toString())
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.title
        val info: TextView = view.info
        val comments: TextView = view.comments

    }

}


interface ItemReceiver {

    fun receiveIds(ids: List<Long>)

    fun itemLoadError(id: Long)

    fun receiveItem(item: HNItem)

}