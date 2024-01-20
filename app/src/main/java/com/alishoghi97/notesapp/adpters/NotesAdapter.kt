package com.alishoghi97.notesapp.adpters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.provider.ContactsContract.CommonDataKinds.Note
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alishoghi97.notesapp.R
import com.alishoghi97.notesapp.models.NoteEntity


class NotesAdapter(
    private val context: Context
) : RecyclerView.Adapter<NotesAdapter.NoteHolder>() {

    private val dataList = ArrayList<NoteEntity>()
    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteHolder(inflate)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val item = dataList[position]
        holder.TitleTxt.text = item.title
        holder.ContentTxt.text = item.content
        holder.dateTExtview.text = item.createDateformated


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataList(list: List<NoteEntity>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun getNote(position: Int): NoteEntity {
        return dataList[position]
    }

    inner class NoteHolder(v: View) : RecyclerView.ViewHolder(v) {
        var TitleTxt: TextView = v.findViewById(R.id.textTitle)
        var ContentTxt: TextView = v.findViewById(R.id.textContent)
        var dateTExtview: TextView = v.findViewById(R.id.textDate)

        init {
            v.setOnClickListener {
                onItemClickListener.onItemClick(dataList[adapterPosition])
            }
        }
    }





    interface OnItemClickListener {
        fun onItemClick(note: NoteEntity)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}
