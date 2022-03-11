package com.example.studyactivty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studyactivty.databinding.ActivtyLoginBinding
import com.example.studyactivty.databinding.RecyclerRowBinding

class PdfRecyclerAdapter(private val pdfList: ArrayList<PDF>) : RecyclerView.Adapter<PdfRecyclerAdapter.PDFHolder>() {

    class PDFHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDFHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PDFHolder(binding)
    }

    override fun onBindViewHolder(holder: PDFHolder, position: Int) {

        holder.binding.imageViewRow.setImageResource(R.drawable.aa)
        holder.binding.textViewRow.text = pdfList[position].pdfName

        holder.binding.cardView.setOnClickListener {
            holder.itemView.context.startActivity(PdfViewActivity.newIntent(holder.itemView.context, pdfList[position]))
        }
    }

    override fun getItemCount(): Int {
        return pdfList.size
    }
}