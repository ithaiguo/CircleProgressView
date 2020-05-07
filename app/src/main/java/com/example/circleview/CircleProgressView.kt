package com.example.circleview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.circleview.utils.DensityUtil
import com.example.circleview.utils.ResourceUtils

class CircleProgressView:View {
    private lateinit var circlePaint:Paint
    private lateinit var arcPaint:Paint
    private lateinit var textPaint:Paint
    private var strokeWidth = 0f
    private var gradient: LinearGradient?=null
    private var rectF: RectF?=null
    private var progress:Float=60f
    private var text:String?=null
    private var startColor = 0
    private var endColor = 0

    constructor(context: Context) : super(context){
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        val typeArray = context.obtainStyledAttributes(attrs,R.styleable.CirclePercentView)
        strokeWidth = typeArray.getFloat(R.styleable.CirclePercentView_circleStrokeWidth,10f)
        startColor = typeArray.getColor(R.styleable.CirclePercentView_circleStartColor,ResourceUtils.getColor(R.color.color_light_pink))
        endColor = typeArray.getColor(R.styleable.CirclePercentView_circleEndColor,ResourceUtils.getColor(R.color.color_theme))
        typeArray.recycle()
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init(context)
    }

    private fun init(context: Context) {
        initCirclePaint()
        initArcPaint()
        initTextPaint(context)
    }

    private fun initTextPaint(context: Context) {
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.textSize = DensityUtil.sp2px(context,34f).toFloat()
        textPaint.strokeWidth = 1f
        textPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = ResourceUtils.getColor(R.color.text_color_4)
//        textPaint.typeface = FontUtils.getTypeExoMedia(context)
    }

    private fun initArcPaint() {
        arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeWidth = strokeWidth
        arcPaint.strokeCap = Paint.Cap.ROUND
    }


    private fun initCirclePaint() {
        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        circlePaint.strokeWidth = strokeWidth
        circlePaint.color = Color.rgb(244,244,244)
        circlePaint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (gradient == null){
            gradient = LinearGradient(width.toFloat(), 0f, width.toFloat(), height.toFloat(),
                    startColor, endColor, Shader.TileMode.MIRROR)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //外部圆
        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()
        canvas.drawCircle(centerX,centerY,centerX - strokeWidth/2,circlePaint)

        //弧形外部外切正方形
        if (rectF == null){
            rectF = RectF(strokeWidth/2,strokeWidth/2,width - strokeWidth/2,height - strokeWidth/2)
        }

        //进度弧形
        arcPaint.shader = gradient
        canvas.drawArc(rectF!!,-90f,progress.times(3.6).toFloat(),false,arcPaint)

        //中心文字
        val fontMetrics = textPaint.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom - strokeWidth/2
        canvas.drawText(text.toString(),centerX,centerY+distance,textPaint)
    }

    fun setProgress(progress:Float){
        this.progress = progress
        invalidate()
    }

    fun setCenterText(text:String){
        this.text = text
    }

    fun setCenterTextSize(textsize:Float){
        textPaint.textSize = DensityUtil.sp2px(context,textsize).toFloat()
    }


}