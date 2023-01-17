package app.lahzebar.commons.util.swipe

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import app.lahzebar.commons.R
import java.util.LinkedList
import java.util.Queue

abstract class SwipeHelper @SuppressLint("ClickableViewAccessibility") constructor(
    context: Context?,
    private val recyclerView: RecyclerView,
    buttonWidth: Int
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    var buttonWidth: Int
    private var buttonListLeft: MutableList<MyButton>? = null
    private var buttonListRight: MutableList<MyButton>? = null
    private val gestureDetector: GestureDetector
    private var swipePosition = -1
    private var swipeThreshold = 0.5f
    private val buttonBufferLeft: MutableMap<Int, MutableList<MyButton>>
    private val buttonBufferRight: MutableMap<Int, MutableList<MyButton>>
    private var removerQueue: Queue<Int>? = null

    init {
        buttonListLeft = ArrayList()
        buttonListRight = ArrayList()
        val gestureListener: SimpleOnGestureListener = object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                for (button in buttonListLeft as ArrayList<MyButton>) {
                    if (button.onClick(e.x, e.y)) {
                        break
                    }
                }
                for (button in buttonListRight!!) {
                    if (button.onClick(e.x, e.y)) {
                        break
                    }
                }
                return true
            }
        }
        gestureDetector = GestureDetector(context, gestureListener)
        val onTouchListener = OnTouchListener { v, event ->
            if (swipePosition < 0) return@OnTouchListener false
            val point = Point(event.rawX.toInt(), event.rawY.toInt())
            val swipeViewHolder = recyclerView.findViewHolderForAdapterPosition(swipePosition)
            val swipedItem = swipeViewHolder!!.itemView
            val rect = Rect()
            swipedItem.getGlobalVisibleRect(rect)
            if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_MOVE) {
                if (rect.top < point.y && rect.bottom > point.y) {
                    gestureDetector.onTouchEvent(event)
                } else {
                    removerQueue!!.add(swipePosition)
                    swipePosition = -1
                    recoverSwipeItem()
                }
            }
            false
        }
        recyclerView.setOnTouchListener(onTouchListener)
        buttonBufferLeft = HashMap()
        buttonBufferRight = HashMap()
        this.buttonWidth = buttonWidth

        removerQueue = object : LinkedList<Int>() {
            fun add(integer: Int?): Boolean {
                return if (contains(integer)) false else super.add(integer!!)
            }
        }

        attachSwipe()
    }

    private fun attachSwipe() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    @Synchronized
    private fun recoverSwipeItem() {
        while (!removerQueue!!.isEmpty()) {
            val pos = removerQueue!!.poll()
            if (pos != null) {
                if (pos > -1) recyclerView.adapter!!.notifyItemChanged(pos)
            }
        }
    }

    inner class MyButton {
        private val text: String
        private val imageResId: Int
        private val textSize: Int
        private var color = 0
        private var pos = 0
        private var clickRegion: RectF? = null
        private val listener: SwipeButtonClickListener
        private val context: Context
        private val resources: Resources
        private var swipeBackground = false
        private var swipeTransparentBackground = false

        constructor(
            context: Context,
            text: String,
            textSize: Int,
            imageResId: Int,
            listener: SwipeButtonClickListener
        ) {
            this.text = text
            this.imageResId = imageResId
            this.textSize = textSize
            this.listener = listener
            this.context = context
            resources = context.resources
        }

        constructor(
            context: Context,
            text: String,
            textSize: Int,
            imageResId: Int,
            swipeBackground: Boolean,
            listener: SwipeButtonClickListener
        ) {
            this.text = text
            this.imageResId = imageResId
            this.textSize = textSize
            this.listener = listener
            this.context = context
            resources = context.resources
            this.swipeBackground = swipeBackground
        }

        constructor(
            context: Context,
            text: String,
            textSize: Int,
            imageResId: Int,
            swipeBackground: Boolean,
            swipeTransparentBackground: Boolean,
            listener: SwipeButtonClickListener
        ) {
            this.text = text
            this.imageResId = imageResId
            this.textSize = textSize
            this.listener = listener
            this.context = context
            resources = context.resources
            this.swipeBackground = swipeBackground
            this.swipeTransparentBackground = swipeTransparentBackground
        }

        fun onClick(x: Float, y: Float): Boolean {
            if (clickRegion != null && clickRegion!!.contains(x, y)) { //
                listener.onClick(pos)
                return true
            }
            return false
        }

        fun onDraw(c: Canvas, rectF: RectF, pos: Int) {
            val p = Paint()
            color = ResourcesCompat.getColor(resources, R.color.palphone_madison, null)

//            if (swipeBackground) {
//                color = ResourcesCompat.getColor(resources, R.color.palphone_madison, null);
//            }
//
//            if (swipeTransparentBackground) {
//                color = ResourcesCompat.getColor(resources, R.color.palphone_transparent, null);
//            }
            p.color = color
            c.drawRect(rectF, p)
            val typeface = ResourcesCompat.getFont(context, R.font.poppins_light)

            // text
            p.color = Color.WHITE
            p.textSize = textSize.toFloat()
            p.typeface = typeface
            val r = Rect()
            val cHeight = rectF.height()
            val cWidth = rectF.width()
            p.textAlign = Paint.Align.LEFT
            p.getTextBounds(text, 0, text.length, r)
            var x = 0f
            var y = 0f
            // if just show text
            if (imageResId == 0) {
                x = cWidth / 2f - r.width() / 2f - r.left
                y = cHeight / 2f + r.height() / 2f - r.bottom
                c.drawText(text, rectF.left + x, rectF.top + y, p)
            } else {
                val d = ContextCompat.getDrawable(context, imageResId)
                val bmp = drawableToBitmap(d)
                val textWidth = p.measureText(text)
                val spaceHeight = 10f
                c.drawBitmap(
                    bmp,
                    rectF.centerX() - bmp.width / 2,
                    rectF.centerY() - (bmp.height / 2 + 18),
                    null
                )
                c.drawText(
                    text,
                    rectF.centerX() - textWidth / 2,
                    rectF.centerY() + bmp.height - 5,
                    p
                )
            }
            clickRegion = rectF
            this.pos = pos
        }
    }

    fun drawableToBitmap(d: Drawable?): Bitmap {
        if (d is BitmapDrawable) return d.bitmap
        val bitmap =
            Bitmap.createBitmap(d!!.intrinsicWidth, d.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        d.setBounds(0, 0, canvas.width, canvas.height)
        d.draw(canvas)
        return bitmap
    }

    override fun onMoved(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        fromPos: Int,
        target: RecyclerView.ViewHolder,
        toPos: Int,
        x: Int,
        y: Int
    ) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        when (direction) {
            ItemTouchHelper.LEFT -> {
                if (swipePosition != pos) removerQueue!!.add(swipePosition)
                swipePosition = pos
                if (buttonBufferLeft.containsKey(swipePosition)) buttonListLeft =
                    buttonBufferLeft[swipePosition] else buttonListLeft!!.clear()
                buttonBufferLeft.clear()
                swipeThreshold = 0.5f * buttonListLeft!!.size * buttonWidth
            }
            ItemTouchHelper.RIGHT -> {
                if (swipePosition != pos) removerQueue!!.add(swipePosition)
                swipePosition = pos
                if (buttonBufferRight.containsKey(swipePosition)) buttonListRight =
                    buttonBufferRight[swipePosition]!! else buttonListRight!!.clear()
                buttonBufferRight.clear()
                swipeThreshold = 0.5f * buttonListRight!!.size * buttonWidth
            }
        }
        recoverSwipeItem()
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return swipeThreshold
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return 0.1f * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return 5.0f * defaultValue
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        val itemView = viewHolder.itemView
        if (pos < 0) {
            swipePosition = pos
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < 0) {
                var buffer: MutableList<MyButton> = ArrayList()
                if (!buttonBufferLeft.containsKey(pos)) {
                    instantiateMyButton(viewHolder, buffer)
                    buttonBufferLeft[pos] = buffer
                } else {
                    buffer = buttonBufferLeft[pos]!!
                }
                translationX = dX * buffer.size * buttonWidth / itemView.width
                drawButton(c, itemView, buffer, pos, translationX)
            } else {
                var buffer: MutableList<MyButton> = ArrayList()
                if (!buttonBufferRight.containsKey(pos)) {
                    instantiateMyButtonRight(viewHolder, buffer)
                    buttonBufferRight[pos] = buffer
                } else {
                    buffer = buttonBufferRight[pos]!!
                }
                translationX = dX * buffer.size * buttonWidth / itemView.width
                drawButton(c, itemView, buffer, pos, translationX)
            }
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            translationX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    private fun drawButton(
        c: Canvas,
        itemView: View,
        buffer: MutableList<MyButton>?,
        pos: Int,
        translationX: Float
    ) {
        var right = itemView.right.toFloat()
        var left = itemView.left.toFloat()
        val dButtonWidth = -1 * translationX / buffer!!.size
        for (button in buffer) {
            if (translationX < 0) {
                left = right - dButtonWidth
                button.onDraw(
                    c,
                    RectF(
                        left,
                        itemView.top.toFloat(),
                        right,
                        itemView.bottom.toFloat()
                    ),
                    pos // (to draw button on right)
                )

                // right = left;
            } else if (translationX > 0) {
                right = left - dButtonWidth
                button.onDraw(
                    c,
                    RectF(
                        left,
                        itemView.top.toFloat(),
                        right,
                        itemView.bottom.toFloat()
                    ),
                    pos // (to draw button on left)
                )
            }
        }
    }

    abstract fun instantiateMyButton(viewHolder: RecyclerView.ViewHolder, buffer: MutableList<MyButton>)
    abstract fun instantiateMyButtonRight(viewHolder: RecyclerView.ViewHolder, buffer: MutableList<MyButton>)
}
