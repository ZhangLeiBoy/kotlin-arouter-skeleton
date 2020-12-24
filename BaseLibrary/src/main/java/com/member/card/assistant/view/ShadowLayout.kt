package com.member.card.assistant.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.member.card.assistant.base.R

class ShadowLayout : RelativeLayout{
    companion object {
        const val NONE = 0x0
        const val ALL = 0xF

        /**
         * 阴影
         */
        const val LEFT = 0x1
        const val TOP = 0x2
        const val RIGHT = 0x4
        const val BOTTOM = 0x8
        const val SHAPE_RECTANGLE = 0x1
        const val SHAPE_OVAL = 0x2

        /**
         * 背景
         */
        const val LEFT_TOP = 0x1
        const val RIGHT_TOP = 0x2
        const val RIGHT_BOTTOM = 0x4
        const val LEFT_BOTTOM = 0x8
    }
    
    constructor(context: Context?):super(context){
        init(null)
    }
    constructor(context: Context?, attrs: AttributeSet? = null):super(context, attrs){
        init(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0):super(context, attrs, defStyleAttr){
        init(attrs)
    }
    
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRectF = RectF()

    /**
     * 阴影的颜色
     */
    private var mShadowColor = Color.TRANSPARENT

    /**
     * 阴影的大小范围
     */
    private var mShadowRadius = 0f

    /**
     * 阴影 x 轴的偏移量
     */
    private var mShadowDx = 0f

    /**
     * 阴影 y 轴的偏移量
     */
    private var mShadowDy = 0f

    /**
     * 阴影显示的边界
     */
    private var mShadowSide = ALL

    /**
     * 阴影的形状，圆形/矩形
     */
    private var mShadowShape = SHAPE_RECTANGLE

    /**
     * 背景颜色
     */
    private var mBgColor = Color.WHITE

    /**
     * 背景路径
     */
    private val mBgPath = Path()

    /**
     * 背景边界
     */
    private var mBgSide = ALL

    /**
     * 背景圆角
     */
    private var mBgRadius = 0f
    private val mBgRadiusArray = FloatArray(8)

    /**
     * 读取设置的阴影的属性
     *
     * @param attrs 从其中获取设置的值
     */
    private fun init(attrs: AttributeSet?) {
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        // 调用此方法后，才会执行 onDraw(Canvas) 方法
        setWillNotDraw(false)
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout)
        if (typedArray != null) {
            mShadowColor = typedArray.getColor(
                R.styleable.ShadowLayout_shadowColor,
                context.resources.getColor(android.R.color.black)
            )
            mShadowRadius = typedArray.getDimension(R.styleable.ShadowLayout_shadowRadius, 0f)
            mShadowDx = typedArray.getDimension(R.styleable.ShadowLayout_shadowDx, 0f)
            mShadowDy = typedArray.getDimension(R.styleable.ShadowLayout_shadowDy, 0f)
            mShadowSide =
                typedArray.getInt(R.styleable.ShadowLayout_shadowSide, ALL)
            mShadowShape = typedArray.getInt(
                R.styleable.ShadowLayout_shadowShape,
                SHAPE_RECTANGLE
            )
            mBgColor = typedArray.getColor(
                R.styleable.ShadowLayout_bgColor,
                context.resources.getColor(android.R.color.white)
            )
            mBgRadius = typedArray.getDimension(R.styleable.ShadowLayout_bgRadius, 0f)
            mBgSide = typedArray.getInt(R.styleable.ShadowLayout_bgSide, ALL)
            typedArray.recycle()
        }
        setShadowPadding()
        setBgRadiusArray()
    }

    private fun setShadowPaint() {
        mPaint.reset()
        mPaint.isAntiAlias = true
        mPaint.color = Color.TRANSPARENT
        mPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor)
    }

    private fun setBgPaint() {
        mPaint.reset()
        mPaint.isAntiAlias = true
        mPaint.color = mBgColor
    }

    private fun setShadowPadding() {
        val effect = mShadowRadius
        var paddingLeft = 0
        var paddingTop = 0
        var paddingRight = 0
        var paddingBottom = 0
        if (mShadowSide and LEFT == LEFT) {
            paddingLeft = effect.toInt()
        }
        if (mShadowSide and TOP == TOP) {
            paddingTop = effect.toInt()
        }
        if (mShadowSide and RIGHT == RIGHT) {
            paddingRight = effect.toInt()
        }
        if (mShadowSide and BOTTOM == BOTTOM) {
            paddingBottom = effect.toInt()
        }
        if (mShadowDy != 0.0f) {
            paddingTop -= mShadowDy.toInt()
            paddingBottom += mShadowDy.toInt()
        }
        if (mShadowDx != 0.0f) {
            paddingLeft -= mShadowDx.toInt()
            paddingRight += mShadowDx.toInt()
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    private fun setBgRadiusArray() {
        for (i in mBgRadiusArray.indices) {
            mBgRadiusArray[i] = 0F
        }
        if (mBgSide and LEFT_TOP == LEFT_TOP) {
            mBgRadiusArray[1] = mBgRadius
            mBgRadiusArray[0] = mBgRadiusArray[1]
        }
        if (mBgSide and RIGHT_TOP == RIGHT_TOP) {
            mBgRadiusArray[3] = mBgRadius
            mBgRadiusArray[2] = mBgRadiusArray[3]
        }
        if (mBgSide and RIGHT_BOTTOM == RIGHT_BOTTOM) {
            mBgRadiusArray[5] = mBgRadius
            mBgRadiusArray[4] = mBgRadiusArray[5]
        }
        if (mBgSide and LEFT_BOTTOM == LEFT_BOTTOM) {
            mBgRadiusArray[7] = mBgRadius
            mBgRadiusArray[6] = mBgRadiusArray[7]
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val effect = mShadowRadius
        var rectLeft = 0f
        var rectTop = 0f
        var rectRight = this.measuredWidth.toFloat()
        var rectBottom = this.measuredHeight.toFloat()
        if (mShadowSide and LEFT == LEFT) {
            rectLeft = effect
        }
        if (mShadowSide and TOP == TOP) {
            rectTop = effect
        }
        if (mShadowSide and RIGHT == RIGHT) {
            rectRight = this.measuredWidth - effect
        }
        if (mShadowSide and BOTTOM == BOTTOM) {
            rectBottom = this.measuredHeight - effect
        }
        if (mShadowDy != 0.0f) {
            rectTop -= mShadowDy
            rectBottom -= mShadowDy
        }
        if (mShadowDx != 0.0f) {
            rectLeft -= mShadowDx
            rectRight -= mShadowDx
        }
        mRectF.left = rectLeft
        mRectF.top = rectTop
        mRectF.right = rectRight
        mRectF.bottom = rectBottom
    }

    /**
     * 真正绘制阴影的方法
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setShadowPaint()
        if (mShadowShape == SHAPE_RECTANGLE) {
            mBgPath.rewind()
            mBgPath.addRoundRect(mRectF, mBgRadiusArray, Path.Direction.CW)
            canvas.drawPath(mBgPath, mPaint)
            setBgPaint()
            canvas.drawPath(mBgPath, mPaint)
        } else if (mShadowShape == SHAPE_OVAL) {
            canvas.drawCircle(
                mRectF.centerX(),
                mRectF.centerY(),
                Math.min(mRectF.width(), mRectF.height()) / 2,
                mPaint
            )
            setBgPaint()
            canvas.drawCircle(
                mRectF.centerX(),
                mRectF.centerY(),
                Math.min(mRectF.width(), mRectF.height()) / 2,
                mPaint
            )
        }
    }

    fun setShadowColor(shadowColor: Int): ShadowLayout {
        mShadowColor = shadowColor
        return this
    }

    fun setShadowRadius(shadowRadius: Float): ShadowLayout {
        mShadowRadius = shadowRadius
        return this
    }

    fun setBgColor(bgColor: Int): ShadowLayout {
        mShadowColor = bgColor
        return this
    }

    fun setBgRadius(bgRadius: Float): ShadowLayout {
        mShadowRadius = bgRadius
        setBgRadiusArray()
        return this
    }

    fun setShadowDx(shadowDx: Float): ShadowLayout {
        mShadowDx = shadowDx
        return this
    }

    fun setShadowDy(shadowDy: Float): ShadowLayout {
        mShadowDy = shadowDy
        return this
    }

    fun setShadowSide(shadowSide: Int): ShadowLayout {
        mShadowSide = shadowSide
        setShadowPadding()
        return this
    }

    fun setBgSide(bgSide: Int): ShadowLayout {
        mBgSide = bgSide
        setBgRadiusArray()
        return this
    }

    fun commit() {
        requestLayout()
        invalidate()
    }
}