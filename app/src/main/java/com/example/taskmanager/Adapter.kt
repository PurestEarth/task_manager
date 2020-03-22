package com.example.taskmanager

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat


class Adapter(
    private val values: List<Task>,
    private val resources: Resources,
    private val mainActivity: MainActivity
): RecyclerView.Adapter<Adapter.ViewHolder>(){

    override fun getItemCount() = values.size;
    val form: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        itemView.setOnTouchListener(OnSwipeTouchListener(mainActivity))
        return ViewHolder(itemView).listen { pos, type ->
            Log.i("BENIZ", pos.toString())
            Log.i("BENIZ", "KARRRAMBA")
            //todo
        }
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }

    override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
        holder?.titleView?.text = values[position].title
        holder?.descView?.text = values[position].description
        holder?.dueView?.text = form.format(values[position].dueDate.time)
        holder?.statusView?.setImageDrawable((if (values[position].done) resources.getDrawable(R.drawable.check) else resources.getDrawable(R.drawable.adv)))
        holder?.typeView?.setImageDrawable(resources.getDrawable(values[position].type.resourceId))
        holder?.id = values[position].id
    }
    class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var id: Int? = null
        var titleView: TextView? = null
        var descView: TextView? = null
        var dueView: TextView? = null
        var statusView: ImageView? = null
        var typeView: ImageView? = null
        init{
            titleView = itemView?.findViewById(R.id.textView_title)
            descView = itemView?.findViewById(R.id.textView2_desc)
            dueView = itemView?.findViewById(R.id.textView3_due)
            statusView = itemView?.findViewById(R.id.imageView2_status)
            typeView = itemView?.findViewById(R.id.imageView_type)
        }
    }
}