package umc.study.umc_7th

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.study.umc_7th.databinding.ItemLockerAlbumBinding

class SavedAlbumRVAdapter (): RecyclerView.Adapter<SavedAlbumRVAdapter.ViewHolder>() {
    private val albums = ArrayList<Album>()

    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedAlbumRVAdapter.ViewHolder {
        val binding: ItemLockerAlbumBinding = ItemLockerAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedAlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albums[position])
        holder.binding.itemLockerAlbumMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(albums[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = albums.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAlbums(albums: ArrayList<Album>) {
        this.albums.clear()
        this.albums.addAll(albums)

        notifyDataSetChanged()
    }

    fun removeSong(position: Int){
        albums.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLockerAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemLockerAlbumCoverImgIv.setImageResource(album.coverImage!!)
            binding.itemLockerAlbumTitleTv.text = album.title
            binding.itemLockerAlbumSingerTv.text = album.singer
        }
    }

}