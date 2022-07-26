package com.existentio.networkapisampleapp.ui

import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.existentio.networkapisampleapp.R
import com.existentio.networkapisampleapp.data.Gif

class GifAdapterTrendingItems :
    RecyclerView.Adapter<GifAdapterTrendingItems.GifAdapterViewHolder>() {

    var gifs = listOf<Gif>()

    fun setGifCollection(gifs: List<Gif>) {
        this.gifs = gifs
        Log.d("gif22", gifs.size.toString())

        notifyDataSetChanged()
    }

    class GifAdapterViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val imageView: ImageView = view!!.findViewById(R.id.image_view)
        var progressBar: ProgressBar = view!!.findViewById(R.id.progress_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifAdapterViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_gif_item_short, parent, false)
        return GifAdapterViewHolder(adapterLayout)
    }

    fun attach(holder : GifAdapterViewHolder): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.progressBar.visibility = View.INVISIBLE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.progressBar.visibility = View.VISIBLE
                return true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: GifAdapterViewHolder, position: Int) {
        Log.d("adapterItems standard gifs", gifs[position].toString())

        val item = gifs[position]
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
            .load(item.images.original.url)
            .apply(RequestOptions().override(150, 150))
            .centerCrop()
            .listener(resourceListener)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            Log.d("pressed", holder.itemView.isVisible.toString())
        }

        holder.imageView.adjustViewBounds = true
    }

    override fun getItemCount(): Int = gifs.size


}
