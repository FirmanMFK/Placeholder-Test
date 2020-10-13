package id.firman.placeholder_test.views.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.firman.placeholder_test.R
import id.firman.placeholder_test.data.model.AdapterClick
import id.firman.placeholder_test.data.model.post.PostResponse
import id.firman.placeholder_test.utils.inflate
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter(private val itemClick:(AdapterClick)-> Unit) : ListAdapter<PostResponse, PostAdapter.ViewHolder>(PostDiffCallback()){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.post_item), itemClick = itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(getItem(position), position)
    }

    inner class ViewHolder(itemView: View, var itemClick: (AdapterClick) -> Unit) : RecyclerView.ViewHolder(itemView){
        fun bindTo(post: PostResponse, position: Int){
            with(post){
                itemView.body_text.text = body
                itemView.tittle_text.text = title
                itemView.setOnClickListener {
                    itemClick(AdapterClick(post.id, false,favorite))
                } }

        }


    }

    class PostDiffCallback : DiffUtil.ItemCallback<PostResponse>() {

        override fun areItemsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean {
            return oldItem == newItem
        }
    }

}
