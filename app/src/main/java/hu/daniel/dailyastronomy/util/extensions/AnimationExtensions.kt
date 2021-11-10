package hu.daniel.dailyastronomy.util.extensions

import android.animation.Animator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import hu.daniel.dailyastronomy.util.noop

fun View.startSizePulseAnimation(maxSize: Float, minSize: Float, animationDuration: Long) {
    val scaleDownAnimation = createScaleAnimation(maxSize, minSize, animationDuration)
    val scaleUpAnimation = createScaleAnimation(minSize, maxSize, animationDuration)

    scaleDownAnimation.onAnimationEnd { startAnimation(scaleUpAnimation) }
    scaleUpAnimation.onAnimationEnd { startAnimation(scaleDownAnimation) }

    startAnimation(scaleDownAnimation)
}

fun View.startSizeAndPositionAnimation(toSize: Float, yPosition: Float? = null, xPosition: Float? = null, duration: Long, callback: (() -> Unit)? = null) {
    val animator = animate()
            .scaleX(toSize)
            .scaleY(toSize)
            .setOnAnimationEnd { callback?.invoke() }
            .setDuration(duration)
    if (yPosition != null) animator.y(yPosition)
    if (xPosition != null) animator.x(xPosition)
    animator.start()
}

fun View.startAlphaAnimation(alpha: Float, duration: Long) {
    animate()
            .alpha(alpha)
            .setDuration(duration)
            .start()
}

private fun createScaleAnimation(fromSize: Float, toSize: Float, animationDuration: Long): ScaleAnimation {
    return ScaleAnimation(fromSize, toSize,
            fromSize, toSize,
            Animation.RELATIVE_TO_SELF, 0.5F,
            Animation.RELATIVE_TO_SELF, 0.5F
    ).apply {
        duration = animationDuration
    }
}

fun Animation.onAnimationEnd(listener: () -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) = noop()
        override fun onAnimationRepeat(animation: Animation?) = noop()
        override fun onAnimationEnd(animation: Animation?) {
            listener.invoke()
        }
    })
}

fun ViewPropertyAnimator.setOnAnimationEnd(listener: () -> Unit): ViewPropertyAnimator {
    setListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) = noop()
        override fun onAnimationCancel(animation: Animator?) = noop()
        override fun onAnimationRepeat(animation: Animator?) = noop()
        override fun onAnimationEnd(animation: Animator?) {
            listener.invoke()
        }
    })
    return this
}