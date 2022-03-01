package com.example.studyactivty

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.google.rpc.context.AttributeContext
import io.grpc.internal.SharedResourceHolder
import kotlinx.android.synthetic.main.activity_home_page.view.*
import kotlinx.android.synthetic.main.activity_shared_pdf_activty.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*
import java.lang.reflect.Array.get

class PdfRecyclerAdapter(val pdfList : ArrayList<PDF>): RecyclerView.Adapter<PdfRecyclerAdapter.PDFHolder>() {
    lateinit var mCtx : Context
    class PDFHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return PDFHolder(view)
    }

    override fun onBindViewHolder(holder: PDFHolder, position: Int) {

        holder.itemView.imageView_row.setImageResource(R.drawable.aa)
        holder.itemView.textView_row.text = pdfList[position].pdfName

        holder.itemView.cardView.setOnClickListener(View.OnClickListener {


                val intent = Intent(mCtx,HomePageActivtiy::class.java)
            intent.putExtra("pdfUrl",pdfList[position].pdfUrl)
            intent.putExtra("pdfName",pdfList[position].pdfName)
            mCtx.startActivity(intent)


        })


    }

    override fun getItemCount(): Int {
        return pdfList.size
    }

}