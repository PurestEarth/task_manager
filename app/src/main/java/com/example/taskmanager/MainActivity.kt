package com.example.taskmanager


import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isNotEmpty
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
        recyclerView.adapter = Adapter(tasks, resources, recyclerView, this, recyclerView.context, this.window.decorView.findViewById(android.R.id.content),
            layoutInflater
        )
        toolbar.overflowIcon = ContextCompat.getDrawable(applicationContext,android.R.drawable.ic_input_add)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_task -> {
                displayPopup()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun displayPopup(){
        val popupView = layoutInflater.inflate(R.layout.task_form, this.window.decorView.findViewById(android.R.id.content), false)
        val dm = recyclerView.context?.resources?.displayMetrics
        val popupWindow = PopupWindow(popupView,
            dm?.widthPixels?.times(0.8)?.toInt()!!,
            dm?.heightPixels?.times(0.8)?.toInt()!!, true)
        val radioGroup: RadioGroup = popupView.findViewById(R.id.radioGroup)
        setUpRadioButtonListeners(popupView)
        popupWindow.showAtLocation(this.window.decorView.findViewById(android.R.id.content), Gravity.CENTER, 0, 0)

        val addTaskButton: Button = popupView.findViewById(R.id.button_add_task)

        addTaskButton.setOnClickListener {
            if(validateTaskForm(popupView, radioGroup)){
                showToast("Task added successfully")
                recyclerView.adapter?.notifyDataSetChanged()
                popupWindow.dismiss()
            }
        }
        popupView.isFocusable = true;
    }

    private fun validateTaskForm(popupView: View, radioGroup: RadioGroup): Boolean{
        var title = ""
        var description = ""
        val type: Type
        val cal: Calendar
        val popupTitle: TextView = popupView.findViewById(R.id.editText4_form_title)
        if(popupTitle.text !== null && popupTitle.text.isNotEmpty()){
            title = popupTitle.text.toString()
        }else {showToast("Title is not filled");return false}
        val popupDesc: TextView = popupView.findViewById(R.id.editText5_form_desc)
        popupDesc.text.toString()
        if(popupDesc.text !== null && popupDesc.text.isNotEmpty()){
            description = popupDesc.text.toString()
        }else {showToast("Description is not filled");return false}
        if(radioGroup.isNotEmpty()){
            type = Type.valueOf(popupView.findViewById<RadioButton>(radioGroup.getCheckedRadioButtonId()).text.toString().toUpperCase())
        }else {showToast("Chose type of Task");return false}
        val popupDueDate: TextView = popupView.findViewById(R.id.editText6_form_due)
        if(popupDueDate.text !== null && popupDueDate.text.isNotEmpty()){
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            var date: Date
            try {
                date = sdf.parse(popupDueDate.text.toString())
            } catch (e: Exception) {
                showToast("Make sure your data input is in correct format - dd-MM-yyyy")
                return false
            }
            cal = Calendar.getInstance()
            cal.time = date
        } else {showToast("Set deadline");return false}
         tasks.add(Task(title , description, cal, false, type, tasks.size))
        return true
    }

    private fun showToast(text: String){
        val toast = Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun setUpRadioButtonListeners(popupView: View){
        val radioEmail: RadioButton = popupView.findViewById(R.id.radio_email)
        val radioPhone: RadioButton = popupView.findViewById(R.id.radio_phone)
        val radioMeeting: RadioButton = popupView.findViewById(R.id.radio_meeting)
        val radioquarantine: RadioButton = popupView.findViewById(R.id.radio_quarantine)

        radioEmail.setOnClickListener {
        }
        radioPhone.setOnClickListener {
        }
        radioMeeting.setOnClickListener {
        }
        radioquarantine.setOnClickListener {
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