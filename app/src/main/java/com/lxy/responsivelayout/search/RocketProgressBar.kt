package com.lxy.responsivelayout.search

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.lxy.responsivelayout.R

/**
 *
 * @Author：liuxy
 * @Date：2024/6/20 14:31
 * @Desc：
 *
 */
class RocketProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress: Int = 0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rocketPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rocketBitmap: Bitmap
    private val cornerRadius = 20f // 圆角半径
    private val progressHeight = 8f // 进度条高度
    private var progressColor = 0 // 进度条颜色

    init {
        // 加载火箭图标并缩小到原图的一半大小
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_rocket)
        rocketBitmap = Bitmap.createScaledBitmap(originalBitmap, originalBitmap.width / 2, originalBitmap.height / 2, false)

        // 获取当前主题颜色
        val typedValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.colorAccent, typedValue, true)
        progressColor = typedValue.data

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制背景条
        paint.color = Color.LTGRAY
        canvas.drawRoundRect(0f, height / 2f - progressHeight, width.toFloat(), height / 2f + progressHeight, cornerRadius, cornerRadius, paint)

        // 绘制进度条
        paint.color = progressColor
        val progressWidth = width * (progress / 100f)
        canvas.drawRoundRect(0f, height / 2f - progressHeight, progressWidth, height / 2f + progressHeight, cornerRadius, cornerRadius, paint)

        // 绘制火箭
        val rocketLeft = progressWidth - rocketBitmap.width / 2
        val rocketTop = height / 2f - rocketBitmap.height / 2
        canvas.drawBitmap(rocketBitmap, rocketLeft, rocketTop, rocketPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredHeight = rocketBitmap.height + paddingTop + paddingBottom

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                widthSize
            }
            MeasureSpec.AT_MOST -> {
                widthSize.coerceAtMost(widthMeasureSpec)
            }
            else -> {
                widthMeasureSpec
            }
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                heightSize
            }
            MeasureSpec.AT_MOST -> {
                desiredHeight.coerceAtMost(heightSize)
            }
            else -> {
                desiredHeight
            }
        }

        setMeasuredDimension(width, height)
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    fun setProgressColor(color : Int){
        this.progressColor = color
    }
}

