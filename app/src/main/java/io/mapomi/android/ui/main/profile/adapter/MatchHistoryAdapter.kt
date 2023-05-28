package io.mapomi.android.ui.main.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.mapomi.android.R
import io.mapomi.android.databinding.HolderMatchHistoryBinding

class MatchHistoryAdapter(
    val onItemClick : (()->Unit)? = null
) : RecyclerView.Adapter<MatchHistoryAdapter.ViewHolder>() {

    private var matchHistoryList : List<String> = listOf("요청사항 타이틀1","요청사항 타이틀2","요청사항 타이틀3","요청사항 타이틀4","요청사항 타이틀5")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_match_history, null, true)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(matchHistoryList[position])
    }

    override fun getItemCount(): Int {
        return matchHistoryList.size
    }

    private fun setMatchParentToRecyclerView(view: View) {
        val layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = layoutParams
    }

    inner class ViewHolder(val bind : HolderMatchHistoryBinding) : RecyclerView.ViewHolder(bind.root)
    {
        fun bind(title : String)
        {

            setMatchParentToRecyclerView(bind.root)
            bind.title = title

            bind.root.setOnClickListener {
                onItemClick!!()
            }
        }
    }


}