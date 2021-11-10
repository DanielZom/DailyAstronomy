package hu.daniel.dailyastronomy.presentation.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import hu.daniel.dailyastronomy.R


class RoundedImageView : AppCompatImageView {
    private var topLeftRadius = 0F
    private var bottomLeftRadius = 0F
    private var topRightRadius = 0F
    private var bottomRightRadius = 0F
    private var radius = 0F
    private var path = Path()
    private val rect: RectF
        get() = RectF(0F, 0F, width.toFloat(), height.toFloat())

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        readAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        readAttrs(attrs)
    }

    private fun readAttrs(attrs: AttributeSet) = context.theme
            .obtainStyledAttributes(attrs, R.styleable.RoundedImageView, 0, 0)
            .apply {
                topLeftRadius = getRadiusFrom(this, R.styleable.RoundedImageView_topLeftRadius)
                bottomLeftRadius = getRadiusFrom(this, R.styleable.RoundedImageView_bottomLeftRadius)
                topRightRadius = getRadiusFrom(this, R.styleable.RoundedImageView_topRightRadius)
                bottomRightRadius = getRadiusFrom(this, R.styleable.RoundedImageView_bottomRightRadius)
                radius = getRadiusFrom(this, R.styleable.RoundedImageView_radius)
            }

    override fun onDraw(canvas: Canvas) {
        drawRoundedCorners(canvas)
        super.onDraw(canvas)
    }

    private fun drawRoundedCorners(canvas: Canvas) {
        val cornerXyValues = if (radius != 0F) {
            floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        } else {
            floatArrayOf(
                    topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius
            )
        }
        path.addRoundRect(rect, cornerXyValues, Path.Direction.CW)
        canvas.clipPath(path)
    }

    private fun getRadiusFrom(typedArray: TypedArray, radiusId: Int) = typedArray.getDimensionPixelSize(radiusId, 0).toFloat()
}