package com.example.taskmanager


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager:
            RecyclerView.LayoutManager
    private var tasks = generateFakeValues()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView =
            findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(tasks, resources, this)
        toolbar.overflowIcon = ContextCompat.getDrawable(applicationContext,android.R.drawable.ic_input_add)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_task -> {
                // TODO Add new task
                return true
            }
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
                listOf(Type.EMAIL, Type.MEETING, Type.PHONE, Type.QUARANTINE).random(), i))
        }
        return values
    }
}

// todo limit description and show full in closeup
// Icons by 8Icon