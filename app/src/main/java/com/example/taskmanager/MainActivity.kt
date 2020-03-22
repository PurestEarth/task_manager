package com.example.taskmanager

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
    private var tasks = generateFakeValues()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView =
            findViewById<RecyclerView>(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(tasks, resources)
        recyclerView.addOnItemTouchListener(OnSwipeTouchListener(this))
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

    class Adapter(private val values: List<Task>, private val resources: Resources): RecyclerView.Adapter<Adapter.ViewHolder>(){

        override fun getItemCount() = values.size;
        val form: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
            val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
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

    class OnSwipeTouchListener(ctx: Context?) : OnTouchListener, RecyclerView.OnItemTouchListener {
        private val gestureDetector: GestureDetector
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            return gestureDetector.onTouchEvent(event)
        }

        private inner class GestureListener : SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                var result = false
                try {
                    val diffY = e2.y - e1.y
                    val diffX = e2.x - e1.x
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(
                                velocityX
                            ) > SWIPE_VELOCITY_THRESHOLD
                        ) {
                            if (diffX > 0) {
                                onSwipeRight()
                            } else {
                                onSwipeLeft()
                            }
                            result = true
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(
                            velocityY
                        ) > SWIPE_VELOCITY_THRESHOLD
                    ) {
                        if (diffY > 0) {
                            onSwipeBottom()
                        } else {
                            onSwipeTop()
                        }
                        result = true
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
                return result
            }
        }

        fun onSwipeRight() {
            Log.i("BENIZ", "SWIIIPED RIGHT")
        }
        fun onSwipeLeft() {
            Log.i("BENIZ", "SWIIIPED LEFT HEHE")
        }
        fun onSwipeTop() {}
        fun onSwipeBottom() {}

        init {
            gestureDetector = GestureDetector(ctx, GestureListener())
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

            Log.i("BENIZ", "ON INTERCEPTE TOUC HEVENT")
            return false
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }
    }

}


// Icons by 8Icon