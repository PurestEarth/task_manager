package com.example.taskmanager

import android.os.Bundle
import android.view.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*
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
        recyclerView.adapter = Adapter(generateFakeValues())
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

    class Adapter(private val values: List<Task>): RecyclerView.Adapter<Adapter.ViewHolder>(){

        override fun getItemCount() = values.size;
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
            val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
            //holder?.textView?.text = values[position].title
            holder?.textView?.text = "//holder?.textView?.text "
        }

        class ViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
            var textView: TextView? = null;
            init{
                 //textView = itemView?.findViewById(R.id.textView_title)
                textView = itemView?.findViewById(R.id.textView_title)
            }
        }

    }
}
