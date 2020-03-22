package com.example.taskmanager

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class OnSwipeTouchListener(ctx: Context?) : View.OnTouchListener {
    private val gestureDetector: GestureDetector
    private val SWIPE_THRESHOLD = 100
    private val SWIPE_VELOCITY_THRESHOLD = 100

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
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
        // todo remove that piece of shit
    }
    fun onSwipeLeft() {
        Log.i("BENIZ", "SWIIIPED LEFT HEHE")
        // todo mark it as done
    }
    fun onSwipeTop() {}
    fun onSwipeBottom() {}

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }
}