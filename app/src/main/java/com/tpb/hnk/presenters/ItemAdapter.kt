package com.tpb.hnk.presenters

import android.graphics.Color
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
import com.tpb.hnk.data.models.HNItem
import kotlinx.android.synthetic.main.viewholder_item.view.*

/**
 * Created by theo on 09/07/17.
 */
class ItemAdapter(val loader: ItemLoader) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), ItemReceiver {

    var recycler: RecyclerView? = null
    var emptyText: String? = null
    var infoFormat: String = ""

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
        infoFormat = recycler?.context?.getString(R.string.format_item_info) ?: infoFormat
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        if (item.second == null) {
            loader.loadItem(item.first)

            holder.title.text = emptyText
            holder.info.text = emptyText
            holder.comments.text = emptyText

        } else {
            holder.title.text = item.second?.title
            val info = SpannableString(String.format(infoFormat, item.second?.by,
                    item.second?.domain(),
                    DateUtils.getRelativeTimeSpanString(1000 * (item.second?.time?: 0))))
            info.setSpan(ForegroundColorSpan(Color.MAGENTA), 0, item.second?.by?.length ?: 0, 0)
            info.setSpan(StyleSpan(Typeface.BOLD), 0, item.second?.by?.length ?: 0, 0)
            holder.info.text = info


            holder.comments.text = item.second?.descendants.toString()
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

interface ItemLoader {

    fun loadRange(ids: LongArray)

    fun loadItem(id: Long)

}

interface ItemReceiver {

    fun receiveIds(ids: List<Long>)

    fun receiveItem(item: HNItem)

}