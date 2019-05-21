package com.narunas.n26test.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.narunas.n26test.R
import com.narunas.n26test.model.pojo.ChartResponse

class ChartGraph : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val backgroundPaint: Paint = Paint()
    val canvasRectF: RectF = RectF()
    val canvasValuePath: Path = Path()
    val canvasValuePaint: Paint = Paint()

    lateinit var chartData: ChartResponse


    init {

        backgroundPaint.color = ContextCompat.getColor(context, R.color.lightGrey)
        canvasValuePaint.color = ContextCompat.getColor(context, R.color.graphValue)
        canvasValuePaint.style = Paint.Style.STROKE
        canvasValuePaint.pathEffect = null
        canvasValuePaint.strokeWidth = 2f
        canvasValuePaint.isAntiAlias = true
    }

    fun update(data: ChartResponse) {

        chartData = data
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            canvasRectF.set(0f, 0f, width.toFloat(), height.toFloat())
            drawBackground(it)
            drawPath(it)
        }
    }

    private fun drawPath(canvas: Canvas) {

        if (!::chartData.isInitialized || chartData.values.size == 0) return

        var maxY = 0.0
        val yDivisor: Float
        val recordCount = chartData.values.size
        val xOffset = canvasRectF.width() / recordCount


        /** normalizing max Y value**/
        for (i in chartData.values.indices) {
            val mY = chartData.values[i].y
            if (mY > maxY) {
                maxY = mY
            }
        }


        yDivisor = canvasRectF.height() / maxY.toFloat()

        canvasValuePath.rewind()
        for (i in chartData.values.indices) {
            if (i == 0) {
                canvasValuePath.moveTo(
                    i * xOffset,
                    canvasRectF.height() - (chartData.values[i].y.toFloat() * yDivisor)
                )

                continue
            }

            canvasValuePath.lineTo(
                i * xOffset,
                canvasRectF.height() - (chartData.values[i].y.toFloat() * yDivisor)
            )
        }

        if (!canvasValuePath.isEmpty) {

            canvas.drawPath(canvasValuePath, canvasValuePaint)
        }
    }
    private fun drawBackground(canvas: Canvas) {
        canvas.drawRoundRect(canvasRectF, 10f, 10f, backgroundPaint)
    }


}