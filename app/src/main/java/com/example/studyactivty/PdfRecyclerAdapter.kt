package com.example.studyactivty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_row.view.*

class PdfRecyclerAdapter(private val pdfList: ArrayList<PDF>) : RecyclerView.Adapter<PdfRecyclerAdapter.PDFHolder>() {

    class PDFHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row, parent, false)
        return PDFHolder(view)
    }

    override fun onBindViewHolder(holder: PDFHolder, position: Int) {

        holder.itemView.imageView_row.setImageResource(R.drawable.aa)
        holder.itemView.textView_row.text = pdfList[position].pdfName

        holder.itemView.cardView.setOnClickListener {
            holder.itemView.context.startActivity(PdfViewActivity.newIntent(holder.itemView.context, pdfList[position]))
        }
    }

    override fun getItemCount(): Int {
        return pdfList.size
    }
}