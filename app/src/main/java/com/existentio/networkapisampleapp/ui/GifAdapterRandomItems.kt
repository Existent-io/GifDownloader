package com.existentio.networkapisampleapp.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.existentio.networkapisampleapp.R
import com.existentio.networkapisampleapp.model.GifItem

class GifAdapterRandomItems :
    RecyclerView.Adapter<GifAdapterRandomItems.GifAdapterViewHolder>() {

    var gifsRandom = mutableListOf<GifItem?>()

    fun setGifs(gifItem: MutableList<GifItem?>) {
        this.gifsRandom = gifItem
        notifyDataSetChanged()
    }

    class GifAdapterViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val imageView: ImageView = view!!.findViewById(R.id.image_view)
        var progressBar: ProgressBar = view!!.findViewById(R.id.progress_bar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GifAdapterViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.gif_item_horizontal_short, parent, false)
        return GifAdapterViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(
        holder: GifAdapterViewHolder,
        position: Int
    ) {
        val gif = gifsRandom[position]
        val resourceListener = object : RequestListener<Drawable> {
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.progressBar.visibility = View.INVISIBLE
                return false
            }

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.progressBar.visibility = View.VISIBLE
                return true
            }
        }

        Glide.with(holder.itemView.context)
            .load(gif?.data?.images?.original!!.url)
            .apply(RequestOptions().override(150, 150))
            .centerCrop()
            .listener(resourceListener)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = gifsRandom.size

}