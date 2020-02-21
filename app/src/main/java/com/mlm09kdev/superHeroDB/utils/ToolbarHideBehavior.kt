package com.mlm09kdev.superHeroDB.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
import androidx.core.view.ViewCompat
import com.google.android.material.animation.AnimationUtils


/**
 * Created by Manuel Montes de Oca on 2/8/2020.
 */
class ToolbarHideBehavior<V : View?>(context: Context?, attrs: AttributeSet?) :
    Behavior<V>(context, attrs) {

    companion object {
        private const val ENTER_ANIMATION_DURATION = 225
        private const val EXIT_ANIMATION_DURATION = 175
        private const val STATE_SCROLLED_DOWN = 1
        private const val STATE_SCROLLED_UP = 2
    }

    private var height = 0
    private var currentState = STATE_SCROLLED_DOWN
    private var additionalHiddenOffsetY = 0
    private var currentAnimator: ViewPropertyAnimator? = null

    override fun onLayoutChild(
        parent: CoordinatorLayout, child: V, layoutDirection: Int
    ): Boolean {
        val paramsCompat = child!!.layoutParams as MarginLayoutParams
        height = child.measuredHeight + paramsCompat.bottomMargin
        return super.onLayoutChild(parent, child, layoutDirection)
    }


    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int
    ): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        if (dyConsumed > 0) {
            slideDown(child)
        } else if (dyConsumed < 0) {
            slideUp(child)
        }
    }

    /**
     * Perform an animation that will slide the child from it's current position to be totally on the
     * screen.
     */
    private fun slideDown(child: V) {
        if (currentState == STATE_SCROLLED_UP) {
            return
        }
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
            child!!.clearAnimation()
        }
        currentState = STATE_SCROLLED_UP
        animateChildTo(
            child,
            -(height + additionalHiddenOffsetY),
            ENTER_ANIMATION_DURATION.toLong(),
            AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR
        )
    }

    /**
     * Perform an animation that will slide the child from it's current position to be totally off the
     * screen.
     */
    private fun slideUp(child: V) {
        if (currentState == STATE_SCROLLED_DOWN) {
            return
        }
        if (currentAnimator != null) {
            currentAnimator!!.cancel()
            child!!.clearAnimation()
        }
        currentState =
            STATE_SCROLLED_DOWN
        animateChildTo(
            child,
            0,
            EXIT_ANIMATION_DURATION.toLong(),
            AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR
        )
    }

    private fun animateChildTo(
        child: V, targetY: Int, duration: Long, interpolator: TimeInterpolator
    ) {
        currentAnimator = child?.animate()
            ?.translationY(targetY.toFloat())
            ?.setInterpolator(interpolator)
            ?.setDuration(duration)
            ?.setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        currentAnimator = null
                    }
                })
    }
}