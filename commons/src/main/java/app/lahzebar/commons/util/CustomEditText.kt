package app.lahzebar.commons.util

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText : AppCompatEditText {
    fun setHandleDismissingKeyboard(
        handleDismissingKeyboard: HandleDismissingKeyboard?
    ) {
        this.handleDismissingKeyboard = handleDismissingKeyboard
    }

    private var handleDismissingKeyboard: HandleDismissingKeyboard? = null

    interface HandleDismissingKeyboard {
        fun dismissKeyboard()
    }

    constructor(context: Context?) : super(context!!) { // TODO Auto-generated constructor stub
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) { // TODO Auto-generated constructor stub
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) { // TODO Auto-generated constructor stub
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK &&
            event.action == KeyEvent.ACTION_UP
        ) {
            handleDismissingKeyboard?.dismissKeyboard()
            return true
        }
        return super.dispatchKeyEvent(event)
    }
}
