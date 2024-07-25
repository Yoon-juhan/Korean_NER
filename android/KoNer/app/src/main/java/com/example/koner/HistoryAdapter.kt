//package com.example.koner
//
//import android.view.LayoutInflater
//import android.view.View
//
//import android.view.ViewGroup
//
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class HistoryAdapter:RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
//
//    fun interface OnItemClickListener {
//        fun onItemClick(v:View, position:Int)
//    }
//    private var listener: OnItemClickListener? = null
//
//    fun setListener(listener: OnItemClickListener) {
//        this.listener = listener
//    }
//
//    private var data:MutableList<NewsItem> = mutableListOf()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
//        val view = LayoutInflater
//            .from(parent.context)
//            .inflate(R.layout.history_item, parent, false)
//        return HistoryViewHolder(view, listener)
//    }
//
//    // data의 개수를 알려줌
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    // data 의 내용을 넣는 작업
//    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
//        val item = data[position]
//
//        holder.historyText.text = item.title
//
//    }
//
//    class HistoryViewHolder(view: View, listener: OnItemClickListener?): RecyclerView.ViewHolder(view){
//
//        val historyText:TextView = view.findViewById(R.id.historyText)
//
//        init {
//            view.setOnClickListener {
//                listener?.onItemClick(view, this.layoutPosition)
//            }
//        }
//    }
//
//    fun setData(data:MutableList<NewsItem>) {
//        this.data = data
//        notifyDataSetChanged()
//    }
//
//    fun getItem(position:Int):NewsItem {
//        return data[position]
//    }
//
//
//
//}
