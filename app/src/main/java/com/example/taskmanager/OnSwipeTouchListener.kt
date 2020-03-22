package com.example.taskmanager

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.math.abs

class OnSwipeTouchListener(
    ctx: Context?,
    private val adapter: Adapter,
    private val recycle: RecyclerView,
    private val context: Context,
    private val view: View,
    private val inflater: LayoutInflater
) : View.OnTouchListener {
    private val gestureDetector: GestureDetector
    private val SWIPE_THRESHOLD = 10
    private val SWIPE_VELOCITY_THRESHOLD = 20

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override  fun onSingleTapUp(e: MotionEvent?): Boolean {
            if (e != null) {
                displayPopup(adapter.values[getItemPosition(e.rawX, e.rawY)])
            }
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
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > SWIPE_THRESHOLD && Math.abs(
                            velocityX
                        ) > SWIPE_VELOCITY_THRESHOLD
                    ) {
                        if (diffX > 0) {
                            onSwipeRight(e1)
                        } else {
                            onSwipeLeft(e2)
                        }
                        result = true
                    }
                } else if (abs(diffY) > SWIPE_THRESHOLD && Math.abs(
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
            return true
        }
    }

    fun onSwipeRight(e: MotionEvent) {
        adapter.values.removeAt(getItemPosition(e.rawX, e.rawY))
        adapter.notifyDataSetChanged()
    }
    fun onSwipeLeft(e: MotionEvent) {
        adapter.values.get(getItemPosition(e.rawX, e.rawY)).done = true
        adapter.notifyDataSetChanged()
    }
    fun onSwipeTop() {}
    fun onSwipeBottom() {}

    fun getItemPosition(x: Float, y:Float): Int{
        val child = recycle.findChildViewUnder(x, y)
        return if(child !== null){
            recycle.getChildAdapterPosition(child) - 1
        } else{
            adapter.values.size - 1
        }
    }

    private fun displayPopup(tsk: Task){
        val popupView = inflater.inflate(R.layout.popup, recycle, false)
        val dm = context?.resources?.displayMetrics
        val popupWindow = PopupWindow(popupView,
            dm?.widthPixels?.times(0.8)?.toInt()!!,
            dm?.heightPixels?.times(0.8)?.toInt()!!, true)
        val popupTitle: TextView = popupView.findViewById(R.id.textView_popup_title)
        val popupStatus: ImageView = popupView.findViewById(R.id.imageView_popup_status)
        val popupType: ImageView = popupView.findViewById(R.id.imageView2_popup_type)
        val popupDueDate: TextView = popupView.findViewById(R.id.textView3_popup_due)
        val popupDescription: TextView = popupView.findViewById(R.id.textView2_popup_desc)

        popupTitle.text = tsk.title
        popupDueDate.text = adapter.form.format(tsk.dueDate.time)
        popupDescription.text = tsk.description
        popupStatus.setImageDrawable((if (tsk.done) adapter.resources.getDrawable(R.drawable.check) else adapter.resources.getDrawable(R.drawable.adv)))
        popupType.setImageDrawable(adapter.resources.getDrawable(tsk.type.resourceId))

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupView.setOnTouchListener { _, _ ->
            popupWindow.dismiss()
            true
        }
    }
    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }
}