package core.views.views

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.SparseArray
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputConnection
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import core.views.R

class CustomKeypad : ConstraintLayout, View.OnClickListener, GestureDetector.OnGestureListener {
    private var mInflater: LayoutInflater
    private lateinit var listener: IonKeyPadListener
    private lateinit var toneGenerator: ToneGenerator
    private lateinit var oneTV: TextView
    private lateinit var twoTV: TextView
    private lateinit var threeTV: TextView
    private lateinit var fourTV: TextView
    private lateinit var fiveTV: TextView
    private lateinit var sixTV: TextView
    private lateinit var sevenTV: TextView
    private lateinit var eightTV: TextView
    private lateinit var nineTV: TextView
    private lateinit var starTV: TextView
    private lateinit var zeroTV: TextView
    private lateinit var hashtagTV: TextView
    private lateinit var oneLayout: ConstraintLayout
    private lateinit var twoLayout: ConstraintLayout
    private lateinit var threeLayout: ConstraintLayout
    private lateinit var fourLayout: ConstraintLayout
    private lateinit var fiveLayout: ConstraintLayout
    private lateinit var sixLayout: ConstraintLayout
    private lateinit var sevenLayout: ConstraintLayout
    private lateinit var eightLayout: ConstraintLayout
    private lateinit var nineLayout: ConstraintLayout
    private lateinit var starLayout: ConstraintLayout
    private lateinit var zeroLayout: ConstraintLayout
    private lateinit var hashtagLayout: ConstraintLayout
    private var keyValues = SparseArray<String>()
    companion object {
        private val TIME_ANIMATION_PAD = 90
    }
    private lateinit var inputConnection: InputConnection
    constructor(context: Context) : super(context) {
        mInflater = LayoutInflater.from(context)
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mInflater = LayoutInflater.from(context)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        mInflater = LayoutInflater.from(context)
        init()
    }

    fun init() {
        mInflater.inflate(R.layout.custom_keypad, this, true)
        oneTV = findViewById(R.id.one_tv)
        twoTV = findViewById(R.id.two_tv)
        threeTV = findViewById(R.id.three_tv)
        fourTV = findViewById(R.id.four_tv)
        fiveTV = findViewById(R.id.five_tv)
        sixTV = findViewById(R.id.six_tv)
        sevenTV = findViewById(R.id.seven_tv)
        eightTV = findViewById(R.id.eight_tv)
        nineTV = findViewById(R.id.nine_tv)
        starTV = findViewById(R.id.star_tv)
        zeroTV = findViewById(R.id.zero_tv)
        hashtagTV = findViewById(R.id.hashtag_tv)

        oneLayout = findViewById(R.id.one_layout)
        twoLayout = findViewById(R.id.two_layout)
        threeLayout = findViewById(R.id.three_layout)
        fourLayout = findViewById(R.id.four_layout)
        fiveLayout = findViewById(R.id.five_layout)
        sixLayout = findViewById(R.id.six_layout)
        sevenLayout = findViewById(R.id.seven_layout)
        eightLayout = findViewById(R.id.eight_layout)
        nineLayout = findViewById(R.id.nine_layout)
        starLayout = findViewById(R.id.star_layout)
        zeroLayout = findViewById(R.id.zero_layout)
        hashtagLayout = findViewById(R.id.hashtag_layout)

        keyValues.put(R.id.one_layout, context.getString(R.string.pad_1))
        keyValues.put(R.id.two_layout, context.getString(R.string.pad_2))
        keyValues.put(R.id.three_layout, context.getString(R.string.pad_3))
        keyValues.put(R.id.four_layout, context.getString(R.string.pad_4))
        keyValues.put(R.id.five_layout, context.getString(R.string.pad_5))
        keyValues.put(R.id.six_layout, context.getString(R.string.pad_6))
        keyValues.put(R.id.seven_layout, context.getString(R.string.pad_7))
        keyValues.put(R.id.eight_layout, context.getString(R.string.pad_8))
        keyValues.put(R.id.nine_layout, context.getString(R.string.pad_9))
        keyValues.put(R.id.star_layout, context.getString(R.string.pad_star))
        keyValues.put(R.id.zero_layout, context.getString(R.string.pad_0))
        keyValues.put(R.id.hashtag_layout, context.getString(R.string.pad_sharp))

        zeroLayout.setOnLongClickListener(
            OnLongClickListener {
                val value = context.getString(R.string.pad_plus)
                inputConnection.commitText(value, 1)
                true
            }
        )

        // set button click listeners
        oneLayout.setOnClickListener(this)
        twoLayout.setOnClickListener(this)
        threeLayout.setOnClickListener(this)
        fourLayout.setOnClickListener(this)
        fiveLayout.setOnClickListener(this)
        sixLayout.setOnClickListener(this)
        sevenLayout.setOnClickListener(this)
        eightLayout.setOnClickListener(this)
        nineLayout.setOnClickListener(this)
        starLayout.setOnClickListener(this)
        zeroLayout.setOnClickListener(this)
        hashtagLayout.setOnClickListener(this)
    }

    interface IonKeyPadListener {
        fun onSwipeLeft()
        fun onSwipeRight()
        fun OnShowKeyBoardAndFocus()
        fun onDail()
    }

    override fun onClick(v: View?) {

        //  ToneGenerator dtmfGenerator = new ToneGenerator(0,ToneGenerator.MAX_VOLUME);
        // do nothing if the InputConnection has not been set yet
        if (!::inputConnection.isInitialized) return
        val value = keyValues[v!!.id]

        inputConnection.commitText(value, 1)
        // TODO this commented
        /* if (SharedPreference.getInstance().getPlayToneOnKeyTouch()) {*/
        when (v.id) {
            R.id.zero_layout -> playTone(zeroLayout, ToneGenerator.TONE_DTMF_0)
            R.id.one_layout -> playTone(oneLayout, ToneGenerator.TONE_DTMF_1)
            R.id.two_layout -> playTone(twoLayout, ToneGenerator.TONE_DTMF_2)
            R.id.three_layout -> playTone(threeLayout, ToneGenerator.TONE_DTMF_3)
            R.id.four_layout -> playTone(fourLayout, ToneGenerator.TONE_DTMF_4)
            R.id.five_layout -> playTone(fiveLayout, ToneGenerator.TONE_DTMF_5)
            R.id.six_layout -> playTone(sixLayout, ToneGenerator.TONE_DTMF_6)
            R.id.seven_layout -> playTone(sevenLayout, ToneGenerator.TONE_DTMF_7)
            R.id.eight_layout -> playTone(eightLayout, ToneGenerator.TONE_DTMF_8)
            R.id.nine_layout -> playTone(nineLayout, ToneGenerator.TONE_DTMF_9)
            R.id.hashtag_layout -> playTone(hashtagLayout, ToneGenerator.TONE_DTMF_S)
            R.id.star_layout -> playTone(starLayout, ToneGenerator.TONE_DTMF_P)
        }
        /*  }*/
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onFling(
        downEvent: MotionEvent,
        moveEvent: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val result = false
        val diffy = moveEvent.y - downEvent.y
        val diffx = moveEvent.x - downEvent.x
        if (Math.abs(diffx) > Math.abs(diffy)) { // right of left swipe
            if (Math.abs(diffx) > 200 && Math.abs(velocityX) > 200) { // check if it is click or swipe
                if (diffx > 0) {
                    listener?.onSwipeRight()
                } else {
                    listener?.onSwipeLeft()
                }
            }
        }
        if (Math.abs(diffy) > Math.abs(diffx)) { // swipe down or top
            if (Math.abs(diffy) > 200 && Math.abs(velocityY) > 200) { // check if it is click or swipe
                if (diffy > 0) {
                    listener?.onDail()
                } else {
                    listener?.OnShowKeyBoardAndFocus()
                }
            }
        }
        return result
    }

    fun setInputConnection(ic: InputConnection) {
        inputConnection = ic
    }
    private fun playTone(btn: ConstraintLayout, mediaFileRawId: Int) {
        val brand = Build.BRAND
        // for lg models during call  dim mode not work as well
        // when the phone is lg do not play dim mode
        // TODO this commented
        // val callInfoRepository: CallInfoRepository = CallInfoRepository.getInstance(context)
        if (!brand.contains("lge")) {
            playToneProcess(btn, mediaFileRawId)
        } else {
            // TODO this commented
           /* if (callInfoRepository.callList.size() <= 0) {
                playToneProcess(btn, mediaFileRawId)
            }*/
        }
    }

    private fun playToneProcess(btn: ConstraintLayout, mediaFileRawId: Int) {
        val animation = ScaleAnimation(
            1.0f,
            0.9f,
            1.0f,
            0.9f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = TIME_ANIMATION_PAD.toLong()
        btn.startAnimation(animation)
        try {
            toneGenerator =
                ToneGenerator(AudioManager.STREAM_SYSTEM, ToneGenerator.TONE_SUP_CONFIRM)
            toneGenerator.startTone(mediaFileRawId, 120)
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                if (toneGenerator != null) {
                    toneGenerator.release()
                }
            }, 200)
        } catch (e: Exception) {
            // TODO this commented
            // CrashlyticsHelper.getInstance().reportCustomCrash(e)
        }
    }

    fun setCustomKeypadListener(listener: IonKeyPadListener) {
        this.listener = listener
    }
}
