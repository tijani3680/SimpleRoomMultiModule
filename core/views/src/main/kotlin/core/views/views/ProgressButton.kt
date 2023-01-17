package core.views.views

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import core.views.R

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val progressBar: DotProgressBar
    private val buttonTextView: TextView

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.progress_button, this, true)
        buttonTextView = root.findViewById(R.id.button_text)
        progressBar = root.findViewById(R.id.progress_indicator)
        loadAttr(attrs, defStyleAttr)
        setButtonTextViewColor()
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressButton,
            defStyleAttr,
            0
        )

        val buttonText = arr.getString(R.styleable.ProgressButton_text)
        val loading = arr.getBoolean(R.styleable.ProgressButton_loading, false)
        val enabled = arr.getBoolean(R.styleable.ProgressButton_enabled, true)
        arr.recycle()
        isEnabled = enabled
        buttonTextView.isEnabled = enabled
        setText(buttonText)
        setLoading(loading)
    }

    fun setLoading(loading: Boolean) {
        isClickable = !loading // Disable clickable when loading
        if (loading) {
            buttonTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            buttonTextView.isEnabled = false
        } else {
            buttonTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            buttonTextView.isEnabled = true
        }
    }

    private fun setText(text: String?) {
        buttonTextView.text = text
    }

    private fun setButtonTextViewColor() {
        when (context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                buttonTextView.setTextColor(ContextCompat.getColor(context, R.color.cloud_burst))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                buttonTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }
    }
}
