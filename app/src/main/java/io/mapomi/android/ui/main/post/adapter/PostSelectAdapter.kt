package io.mapomi.android.ui.main.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.mapomi.android.R
import io.mapomi.android.databinding.HolderPostSelectBinding

class PostSelectAdapter(

) : RecyclerView.Adapter<PostSelectAdapter.ViewHolder>() {

    private var applyList : List<String> = listOf("동행 지원자 1","동행 지원자 2","동행 지원자 3")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_post_select, null, true)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(applyList[position])
    }

    override fun getItemCount(): Int {
        return applyList.size
    }

    inner class ViewHolder(val bind : HolderPostSelectBinding) : RecyclerView.ViewHolder(bind.root)
    {
        fun bind(nickname : String)
        {
            bind.nickName = nickname
        }
    }

}