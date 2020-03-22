package com.example.taskmanager

import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager:
            RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView =
            findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(generateFakeValues(), getResources())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun generateFakeValues(): MutableList<Task> {
        val values = mutableListOf<Task>()
        for(i in 0..20){
            values.add(Task("Title $i",
                "RecyclerView.ItemAnimator will animate ViewGroup\n" +
                        "modifications such as add/delete/select \n" +
                        "that are notified to the adapter. DefaultItemAnimator can be used for basic default animations \n" +
                        "and works quite well. See the section of this guide for more information." ,
                Calendar.getInstance(),
                listOf(true,false).random(),
                listOf(Type.EMAIL, Type.MEETING, Type.PHONE, Type.QUARANTINE).random()))
        }
        return values
    }

    class Adapter(private val values: List<Task>, private val resources: Resources): RecyclerView.Adapter<Adapter.ViewHolder>(){

        override fun getItemCount() = values.size;
        val form: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
            val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
            holder?.titleView?.text = values[position].title
            holder?.descView?.text = values[position].description
            holder?.dueView?.text = form.format(values[position].dueDate.time)
            holder?.statusView?.setImageDrawable((if (values[position].done) resources.getDrawable(R.drawable.check) else resources.getDrawable(R.drawable.adv)))
            holder?.typeView?.setImageDrawable(resources.getDrawable(values[position].type.resourceId))
        }
        class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
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
}


// Icons by 8Icon