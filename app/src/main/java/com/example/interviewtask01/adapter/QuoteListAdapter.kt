package com.example.interviewtask01.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.interviewtask01.R
import com.example.interviewtask01.base.BaseViewHolder
import com.example.interviewtask01.databinding.ListItemQuoteBinding
import com.example.interviewtask01.model.QuoteModel

class QuoteListAdapter : ListAdapter<QuoteModel.QuoteItem, BaseViewHolder>(DiffCallback) {

    private var click: ((item: QuoteModel.QuoteItem) -> Unit)? = null

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.onBind(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_quote, parent, false)
    )

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        private val binding = ListItemQuoteBinding.bind(view)

        override fun clear() {
            binding.tvID.text = ""
            binding.tvQuoteAuthor.text = ""
            binding.tvQuoteText.text = ""
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {
            with(getItem(position)) {
                id?.let { id -> binding.tvID.text = id.toString() }
                quote?.let { quote -> binding.tvQuoteText.text = quote }
                author?.let { author -> binding.tvQuoteAuthor.text = author }

                itemView.setOnClickListener {
                    click?.invoke(this)
                }
            }
        }
    }

    fun click(click: (item: QuoteModel.QuoteItem) -> Unit) {
        this.click = click
    }

    private object DiffCallback : DiffUtil.ItemCallback<QuoteModel.QuoteItem>() {
        override fun areItemsTheSame(
            oldItem: QuoteModel.QuoteItem,
            newItem: QuoteModel.QuoteItem
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: QuoteModel.QuoteItem,
            newItem: QuoteModel.QuoteItem
        ): Boolean =
            oldItem == newItem
    }

}

