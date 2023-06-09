package io.mapomi.android.ui.main.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.mapomi.android.R
import io.mapomi.android.databinding.HolderMainPostBinding
import io.mapomi.android.remote.dataclass.post.Post
import io.mapomi.android.system.LogDebug

class PostAdapter(
    val onItemClick : (()->Unit)? = null
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var postList : List<Post> = listOf()

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

    fun setPosts(posts : List<Post>)
    {
        if(this.postList != posts)
        {
            val oldSize = itemCount
            this.postList = posts
            if(oldSize >= itemCount)
                notifyDataSetChanged()
            else
                notifyItemRangeInserted(oldSize,itemCount - oldSize)
            LogDebug(javaClass.name,"[POST ADAPTER] 새로운 데이터 수신 $itemCount 개")
        }

        postList = listOf(Post("1","홍대입구에 가고싶어요","2023-05-31 15:10","연남동","홍대입구 4번 출구",null,null,false,"30분","빨리 가고 싶어요"))
        notifyDataSetChanged()

    }

    private fun setMatchParentToRecyclerView(view: View) {
        val layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = layoutParams
    }

    inner class ViewHolder(val bind : HolderMainPostBinding) : RecyclerView.ViewHolder(bind.root)
    {
        fun bind(post : Post)
        {
            setMatchParentToRecyclerView(bind.root)
            bind.post = post
            bind.setOnItemClick {
                onItemClick!!()
            }
        }
    }

}