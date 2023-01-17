package core.views.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.viewbinding.BuildConfig

class AnimationHelper {
    @SuppressLint("ClickableViewAccessibility")
    fun scaleAnimation(view: View, onUpAction: () -> Unit) {
        view.setOnTouchListener { v, event ->
            if (BuildConfig.DEBUG)
                Log.d("TAG", "scaleAnimation: event.action" + event.action)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    scaleDown(v)
                }
                MotionEvent.ACTION_UP -> {
                    scaleUp(v)
                    onUpAction.invoke()
                }
                MotionEvent.ACTION_CANCEL -> {
                    scaleUp(v)
                }
                MotionEvent.ACTION_MOVE -> {
                    // scaleUp(v)
                }
            }
            true
        }
    }

    fun scaleDown(view: View) {
        val scaleDownX = ObjectAnimator.ofFloat(
            view,
            "scaleX", 0.9f
        )
        val scaleDownY = ObjectAnimator.ofFloat(
            view,
            "scaleY", 0.9f
        )
        val translationTogetherImage = AnimatorSet()
        translationTogetherImage.duration = 500
        translationTogetherImage.playTogether(scaleDownX, scaleDownY)
        translationTogetherImage.start()
    }

    fun scaleUp(view: View) {
        val scaleDownX2 = ObjectAnimator.ofFloat(
            view, "scaleX", 1f
        )
        val scaleDownY2 = ObjectAnimator.ofFloat(
            view, "scaleY", 1f
        )
        val scaleDown2 = AnimatorSet()
        scaleDown2.duration = 500
        scaleDown2.playTogether(scaleDownX2, scaleDownY2)
        scaleDown2.start()
    }
}
