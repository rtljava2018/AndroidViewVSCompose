package com.rtllabs.testing.viewbased

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rtllabs.testing.R
import com.rtllabs.testing.domain.Posts
import org.w3c.dom.Text

class PostAdapter(): RecyclerView.Adapter<PostViewHolder>() {
    private var items: List<Posts> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        holder.bind(items[position],position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<Posts>){
        this.items=items
        notifyDataSetChanged()
    }
}

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title=itemView.findViewById<TextView>(R.id.title)
    val body=itemView.findViewById<TextView>(R.id.body)

    fun bind(posts: Posts,position: Int){
       title.text=posts.title
       body.text=posts.body
    }


}
