package io.mapomi.android.ui.main.accompany.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.mapomi.android.R
import io.mapomi.android.databinding.HolderMainPostBinding

class AccompanyAdapter(
    val onItemClick : (()->Unit)? = null
) : RecyclerView.Adapter<AccompanyAdapter.ViewHolder>() {

    private var postList : List<String> = listOf("동행요청 글 1", "동행요청 글 2" ,"동행요청 글 3", "동행요청 글 4", "동행요청 글 5", "동행요청 글 6", "동행요청 글 7")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_main_post, null, true)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    inner class ViewHolder(val bind : HolderMainPostBinding) : RecyclerView.ViewHolder(bind.root)
    {
        fun bind(title : String)
        {
            bind.title = title
            bind.setOnItemClick {
                onItemClick!!
            }
        }
    }

}