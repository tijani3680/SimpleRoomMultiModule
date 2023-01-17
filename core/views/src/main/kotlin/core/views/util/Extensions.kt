package core.views.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow

fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        trySend(Unit)
    }
    awaitClose { setOnClickListener(null) }
}

fun ImageView.changeImageDrawable(value: Int) {
    ResourcesCompat.getDrawable(resources, value, null)
}

private const val IDLE_TIME_IN_MILLIS = 1500L

fun EditText.edtTextChangeListener() = channelFlow {
    val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {

            if (s.toString().isBlank()) {

                trySend(false)
            } else {
                trySend(true)
            }
        }
    }

    addTextChangedListener(textWatcher)
    awaitClose { removeTextChangedListener(textWatcher) }
}

fun EditText.isTypingFlow() = channelFlow {
    val textWatcher = object : TextWatcher {
        private var isTyping = false
        private val handler = Handler(Looper.getMainLooper())
        private val runnable = Runnable {
            if (!isTyping) {

                trySend(false)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // TODO("Not yet implemented")
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            isTyping = true
            handler.removeCallbacks(runnable)
            if (before < count) {
                trySend(true)
            }
        }

        override fun afterTextChanged(s: Editable?) {
            isTyping = false
            handler.postDelayed(runnable, IDLE_TIME_IN_MILLIS)
        }
    }

    addTextChangedListener(textWatcher)
    awaitClose { removeTextChangedListener(textWatcher) }
}

fun Fragment.internationalPhone(phone: String): String {
    val phoneUtil = PhoneNumberUtil.createInstance(context)
    try {
        val phoneWithSpace = phoneUtil.format(
            generatePhoneNumber(phone),
            PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL
        )
        return phoneWithSpace.replace(" ".toRegex(), "")
    } catch (exception: NullPointerException) {
    }
    return phone
}

fun Fragment.generatePhoneNumber(phoneNumber: String?): Phonenumber.PhoneNumber? {
    var mPhoneNumber: Phonenumber.PhoneNumber? = null
    val phoneUtil = PhoneNumberUtil.createInstance(context)
    val tm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as TelephonyManager
    val defaultCountryIsoCode = tm.networkCountryIso.uppercase()
    try {
        mPhoneNumber = phoneUtil.parse(phoneNumber, defaultCountryIsoCode)
    } catch (e: NumberParseException) {
    }
    return mPhoneNumber
}
