package com.charlie.sawl.ruler

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.charlie.sawl.R
import java.util.*

/**
 * Created by Joel Anderson on 12/12/16.
 *
 *
 * Custom view class that gets height and width of viewport and
 * displays accurate ruler markings every 1/16th inch.
 */

class RulerView : View {

    private val defaultStrokeWidth = resources.getDimension(R.dimen.stroke_width)
    private val labelTextSize = resources.getDimension(R.dimen.text_size_sub_header)
    private val marginOffset = resources.getDimension(R.dimen.text_size_sub_header)

    private var mHeightInches: Float = 0f
    private var mYDPI: Float = 0f

    private var mPointerLocation = 100f

    private var mAccentColor = ContextCompat.getColor(context, R.color.colorAccent)
    private var mPointerAlpha = 255

    private val mPaint = Paint()
    private val mTextPaint = Paint()
    private val mTemp = Paint()

    constructor(context: Context) : super(context) {
        initPaints()
        val theme = context.getSharedPreferences("settings", Activity.MODE_PRIVATE)
        val dark = theme.getBoolean("dark", true)
        if (dark) {
            initWhitePaints()
        } else {
            initDarkPaints()
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initPaints()
        val theme = context.getSharedPreferences("settings", Activity.MODE_PRIVATE)
        val dark = theme.getBoolean("dark", true)
        if (dark) {
            initWhitePaints()
        } else {
            initDarkPaints()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        measureViewport()

        drawMetricStrokes(canvas)

        if (mPointerAlpha > 0) {
            drawPointer(canvas)
            drawPointerLabel(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mPointerAlpha > 0) {
            //update pointer location
            mPointerLocation = event.y
            //refresh view
            this.invalidate()
            true
        } else super.onTouchEvent(event)
    }

    /**
     * Initialize paint for ruler strokes
     */
    private fun initPaints() {
        //Initialize paint properties for ruler strokes
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = defaultStrokeWidth
        mPaint.isAntiAlias = false

        //Initialize paint properties for label text
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.textSize = labelTextSize
        mTextPaint.isFakeBoldText = true
    }

    private fun initWhitePaints() {
        mPaint.color = ContextCompat.getColor(context, android.R.color.black)
        mTextPaint.color = ContextCompat.getColor(context, android.R.color.black)
        mTemp.color =  mPaint.color
    }

    private fun initDarkPaints() {
        mPaint.color = ContextCompat.getColor(context, android.R.color.white)
        mTextPaint.color = ContextCompat.getColor(context, android.R.color.white)
        mTemp.color = mPaint.color
    }

    /**
     * Measures the viewport, calculates height and width in inches
     */
    private fun measureViewport() {
        val dm = DisplayMetrics()
        //Ensure viewport can be measured (rare case, but better to check before casting)
        if (context is Activity) {
            (context as Activity).windowManager.defaultDisplay.getMetrics(dm)
            mYDPI = dm.ydpi
            mHeightInches = height / mYDPI
        } else Log.d("Error", "View not in activity, skipping measurements")
    }

    private fun drawMetricStrokes(canvas: Canvas) {
        var i = 0f
        val heightMetric = (mHeightInches * 2.54).toFloat() * 10
        while (i < heightMetric) {
            updatePaintColor(i / 10)

            val lineWidth = getLineWidth(i / 10)
            val strokeLocation = (i.toDouble() / 10.0 / 2.54).toFloat() * mYDPI
            canvas.drawLine(0f, strokeLocation, lineWidth.toFloat(), strokeLocation, mPaint)

            drawLabel(canvas, i / 10, lineWidth - labelTextSize, strokeLocation + labelTextSize / 2)

            i += 1f
        }
    }

    /**
     * Change stroke color for whole inch markers, keep it black for others
     *
     * @param i the current location onscreen in inches
     */
    private fun updatePaintColor(i: Float) {
        val floor = i.toInt()
        if (i == floor.toFloat()) {
            mPaint.color = mAccentColor
        } else {
            mPaint.color = mTemp.color
        }
    }

    /**
     * Returns the strokes desired width
     *
     * @param inches the current location onscreen in inches
     * @return line width for stroke at current location
     */
    private fun getLineWidth(inches: Float): Int {
        val ceiling = Math.ceil(inches.toDouble())
        val floor = Math.floor(inches.toDouble())

        val maxWidth = width / 2

        if (inches.toDouble() == floor) {
            return maxWidth
        } else if (inches - 0.5 == floor) {
            return maxWidth / 2
        } else if (inches - 0.25 == floor || inches + 0.25 == ceiling) {
            return maxWidth / 4
        }
        return maxWidth / 8
    }

    /**
     * Draws the label for the current stroke if location is whole inch
     *
     * @param canvas to draw label on
     * @param inches the current location onscreen in inches
     * @param x      location in width to start text
     * @param y      location in height to start text
     */
    private fun drawLabel(canvas: Canvas, inches: Float, x: Float, y: Float) {
        val floor = inches.toInt()
        if (inches == floor.toFloat()) {
            val label = floor.toString()

            canvas.save()
            canvas.rotate(90f, x, y)
            canvas.drawText(label, x, y, mTextPaint)
            canvas.restore()
        }
    }

    /**
     * Draws line and circle that user can manipulate to measure objects.
     *
     * @param canvas to draw pointer on
     */
    private fun drawPointer(canvas: Canvas) {
        //Set new paint attributes
        mPaint.color = mAccentColor
        mPaint.style = Paint.Style.FILL
        mPaint.alpha = mPointerAlpha

        //Draw line and circle
        val circleRadius = width / 12
        val lineX = width.toFloat() - (circleRadius * 4).toFloat() - marginOffset
        val circleX = width.toFloat() - (circleRadius * 4).toFloat() - marginOffset
        canvas.drawLine(0f, mPointerLocation, lineX, mPointerLocation, mPaint)
        canvas.drawCircle(circleX, mPointerLocation, circleRadius.toFloat(), mPaint)

        //Revert paint attributes
        mPaint.style = Paint.Style.STROKE
        mPaint.color = ContextCompat.getColor(context, android.R.color.white)
        mPaint.alpha = 255
    }

    /**
     * Draws label on pointer, marking number of inches to one significant figure
     *
     * @param canvas to draw label on
     */
    private fun drawPointerLabel(canvas: Canvas) {
        //Set new paint attributes
        mTextPaint.color = ContextCompat.getColor(context, android.R.color.white)
        mTextPaint.alpha = mPointerAlpha

        val labelValue = mPointerLocation * 2.54f
        val pointerLabel = String.format(Locale.getDefault(), "%.2f", labelValue / mYDPI) //Round to tenth place

        //Draw Label in circle
        val circleRadius = width / 12
        val x = width.toFloat() - (circleRadius * 4).toFloat() - marginOffset - labelTextSize / 3
        val y = if (pointerLabel.length > 4) mPointerLocation - labelTextSize / 4 else mPointerLocation
        canvas.save()
        canvas.rotate(90f, x, y)
        canvas.drawText(pointerLabel, x - labelTextSize, y, mTextPaint)//offset text to center in circle
        canvas.restore()

        //Revert paint attributes
        mTextPaint.color = mTemp.color
        mTextPaint.alpha = 255
    }
}
