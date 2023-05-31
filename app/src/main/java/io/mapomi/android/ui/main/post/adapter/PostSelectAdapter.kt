package io.mapomi.android.ui.main.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.mapomi.android.R
import io.mapomi.android.databinding.HolderPostSelectBinding
import io.mapomi.android.remote.dataclass.post.PostVolunteer

class PostSelectAdapter(

) : RecyclerView.Adapter<PostSelectAdapter.ViewHolder>() {

    private var applyList : List<PostVolunteer> = listOf(PostVolunteer("지원자1","남","26",true),PostVolunteer("지원자2","여","28",false),PostVolunteer("지원자3","남","32",false))
    private var selectPosition = 0

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
        fun bind(volunteer: PostVolunteer)
        {
            bind.volunteer = volunteer
            bind.root.setOnClickListener {
                onClickVolunteer(adapterPosition)
            }
        }
    }

    private fun onClickVolunteer(position : Int)
    {
        applyList[selectPosition].isSelect =false
        notifyItemChanged(selectPosition)
        selectPosition = position
        applyList[selectPosition].isSelect = true
        notifyItemChanged(selectPosition)
    }

}