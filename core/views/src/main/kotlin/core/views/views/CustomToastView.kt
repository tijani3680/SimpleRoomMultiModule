package core.views.views

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import core.views.R

class CustomToastView : RelativeLayout {
    private var mInflater: LayoutInflater
    lateinit var tvMessage: TextView
    private lateinit var customView: RelativeLayout
    lateinit var imageView: ImageView

    constructor(context: Context?) : super(context) {
        mInflater = LayoutInflater.from(context)
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        mInflater = LayoutInflater.from(context)
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mInflater = LayoutInflater.from(context)
        init()
    }

    fun init() {
        mInflater.inflate(R.layout.custom_view, this, true)
        imageView = findViewById(R.id.icon_group)
        tvMessage = findViewById(R.id.tv_customeToast)
        customView = findViewById(R.id.custome_view)
    }

    fun initData(title: String, icon: IconChooser) {

        when (icon) {

            IconChooser.ERROR -> {
                imageView.setBackgroundResource(R.drawable.ic_error)
                tvMessage.setTextColor(ContextCompat.getColor(context, R.color.tv_error))
                customView.background.setColor(R.color.back_error)
            }
            IconChooser.WARNING -> {
                imageView.setBackgroundResource(R.drawable.ic_exclemationmark)
                tvMessage.setTextColor(ContextCompat.getColor(context, R.color.tv_warning))

                customView.background.setColor(R.color.back_warning)
            }
            IconChooser.INFORMATIONAL -> {
                imageView.setBackgroundResource(R.drawable.ic_informational)

                customView.background.setColor(R.color.back_informationla)
            }
            IconChooser.SUCCESS -> {
                imageView.setBackgroundResource(R.drawable.ic_success)

                customView.background.setColor(R.color.back_success)
            }
            IconChooser.NoInternet -> {
                imageView.setBackgroundResource(R.drawable.ic_dc)

                customView.background.setColor(R.color.back_noInternet)
            }
        }
        tvMessage.text = title
    }

    private fun Drawable.setColor(colorInt: Int) {

        when (val muted = this.mutate()) {
            is GradientDrawable -> muted.setColor(ContextCompat.getColor(context, colorInt))
            is ShapeDrawable -> muted.paint.color = ContextCompat.getColor(context, colorInt)
            is ColorDrawable -> muted.color = ContextCompat.getColor(context, colorInt)
        }
    }
}
