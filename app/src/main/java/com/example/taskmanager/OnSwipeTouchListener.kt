package com.example.taskmanager

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class OnSwipeTouchListener(
    ctx: Context?,
    private val adapter: Adapter,
    private val recycle: RecyclerView
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
            Log.i("BENIZ", "Totally clciked")
            if (e != null) {
                Log.i("BENIZ", getItemPosition(e.rawX, e.rawY).toString())
            }
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.i("BENIZ", "WURST")
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
        Log.i("BENIZ", "SWIPPED RIGHT")
        Log.i("BENIZ", getItemPosition(e.rawX, e.rawY).toString())
        adapter.values.removeAt(getItemPosition(e.rawX, e.rawY))
        adapter.notifyDataSetChanged()
        // todo remove that piece of shit
    }
    fun onSwipeLeft(e: MotionEvent) {
        Log.i("BENIZ", "SWIIIPED LEFT HEHE")
        adapter.values.get(getItemPosition(e.rawX, e.rawY)).done = true
        adapter.notifyDataSetChanged()
        // todo mark it as done
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

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }
}