package io.mapomi.android.ui.main.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.mapomi.android.R
import io.mapomi.android.databinding.HolderPostDateBinding
import io.mapomi.android.remote.dataclass.local.PostDate
import kotlinx.coroutines.flow.MutableStateFlow

class PostDateAdapter(

) : RecyclerView.Adapter<PostDateAdapter.ViewHolder>() {

    private var dateList : List<PostDate> = listOf()
    private var selectPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_post_date, null, true)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dateList[position])
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    fun setDateList(postDates : List<PostDate>)
    {
        dateList = postDates
    }

    fun getSelectedDate() : String
    {
        return dateList[selectPosition].fullDate
    }

    inner class ViewHolder(val bind : HolderPostDateBinding) : RecyclerView.ViewHolder(bind.root)
    {
        fun bind(postDate : PostDate)
        {
            bind.postDate = postDate
            bind.root.setOnClickListener{
                onClickDate(adapterPosition)
            }
        }
    }

    private fun onClickDate(position : Int)
    {
        dateList[selectPosition].select =false
        notifyItemChanged(selectPosition)
        selectPosition = position
        dateList[selectPosition].select = true
        notifyItemChanged(selectPosition)
    }

}