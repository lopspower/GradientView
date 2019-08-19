package com.mikhaellopez.gradientview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt

/**
 * Copyright (C) 2019 Mikhael LOPEZ
 * Licensed under the Apache License Version 2.0
 */
class GradientView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    // Properties
    private val paint: Paint = Paint()
    private val rect = Rect()

    //region Attributes
    var start: Int = Color.WHITE
        set(value) {
            field = value
            update()
        }
    var alphaStart: Float = 1f
        set(value) {
            field = if (value > 1f) 1f else if (value < 0f) 0f else value
            update()
        }
    var end: Int = Color.WHITE
        set(value) {
            field = value
            update()
        }
    var alphaEnd: Float = 1f
        set(value) {
            field = if (value > 1f) 1f else if (value < 0f) 0f else value
            update()
        }
    var direction: GradientDirection = GradientDirection.LEFT_TO_RIGHT
        set(value) {
            field = value
            update()
        }
    //endregion

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        // Load the styled attributes and set their properties
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.GradientView, 0, 0)

        // Init Circle Color
        start = attributes.getColor(R.styleable.GradientView_gv_start, start)
        alphaStart = attributes.getFloat(R.styleable.GradientView_gv_alpha_start, alphaStart)
        end = attributes.getColor(R.styleable.GradientView_gv_end, end)
        alphaEnd = attributes.getFloat(R.styleable.GradientView_gv_alpha_end, alphaEnd)
        direction = attributes.getInteger(R.styleable.GradientView_gv_direction, direction.value).toGradientDirection()

        attributes.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        update()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(rect, paint)
    }

    private fun update() {
        // Update Size
        val usableWidth = width - (paddingLeft + paddingRight)
        val usableHeight = height - (paddingTop + paddingBottom)
        rect.right = usableWidth
        rect.bottom = usableHeight
        // Update Color
        paint.shader = linearGradient()
        // ReDraw
        invalidate()
    }

    private fun linearGradient(): LinearGradient {
        var x0 = 0f
        var y0 = 0f
        var x1 = 0f
        var y1 = 0f
        when (direction) {
            GradientDirection.LEFT_TO_RIGHT -> x1 = width.toFloat()
            GradientDirection.RIGHT_TO_LEFT -> x0 = width.toFloat()
            GradientDirection.TOP_TO_BOTTOM -> y1 = height.toFloat()
            GradientDirection.BOTTOM_TO_TOP -> y0 = height.toFloat()
        }
        return LinearGradient(x0, y0, x1, y1,
                start.adjustAlpha(alphaStart), end.adjustAlpha(alphaEnd),
                Shader.TileMode.CLAMP)
    }

    /**
     * Transparent the given color by the factor
     * The more the factor closer to zero the more the color gets transparent
     *
     * @param factor 1.0f to 0.0f
     * @return int - A transplanted color
     */
    private fun Int.adjustAlpha(factor: Float): Int {
        val alpha = (Color.alpha(this) * factor).roundToInt()
        val red = Color.red(this)
        val green = Color.green(this)
        val blue = Color.blue(this)
        return Color.argb(alpha, red, green, blue)
    }

    private fun Int.toGradientDirection(): GradientDirection =
            when (this) {
                1 -> GradientDirection.LEFT_TO_RIGHT
                2 -> GradientDirection.RIGHT_TO_LEFT
                3 -> GradientDirection.TOP_TO_BOTTOM
                4 -> GradientDirection.BOTTOM_TO_TOP
                else -> throw IllegalArgumentException("This value is not supported for GradientDirection: $this")
            }

    /**
     * GradientDirection enum class to set the direction of the gradient color
     */
    enum class GradientDirection(val value: Int) {
        LEFT_TO_RIGHT(1),
        RIGHT_TO_LEFT(2),
        TOP_TO_BOTTOM(3),
        BOTTOM_TO_TOP(4)
    }

}