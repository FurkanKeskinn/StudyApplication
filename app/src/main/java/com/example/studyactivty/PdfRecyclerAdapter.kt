package com.example.studyactivty

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.android.synthetic.main.activity_home_page.view.*
import kotlinx.android.synthetic.main.activity_pdf_view.view.*
import kotlinx.android.synthetic.main.activity_shared_pdf_activty.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*

class PdfRecyclerAdapter(val pdfList: ArrayList<PDF>): RecyclerView.Adapter<PdfRecyclerAdapter.PDFHolder>() {

    private lateinit var mCtx :Context
    class PDFHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFHolder {
        this.mCtx = parent.context
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return PDFHolder(view)
    }

    override fun onBindViewHolder(holder: PDFHolder, position: Int) {

        holder.itemView.imageView_row.setImageResource(R.drawable.aa)
        holder.itemView.textView_row.text = pdfList[position].pdfName

        holder.itemView.cardView.setOnClickListener(View.OnClickListener {

                val intent = Intent(mCtx,PdfViewActivity::class.java)
            intent.putExtra("pdfUrl",pdfList[position].pdfUrl)
            intent.putExtra("pdfName",pdfList[position].pdfName)
            mCtx.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return pdfList.size
    }
}