package app.lahzebar.features.keypad

import android.Manifest
import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Activity
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.provider.Settings
import android.telecom.TelecomManager
import android.text.Editable
import android.text.SpannableString
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import app.lahzebar.commons.models.SearchedPhoneNumberItem
import app.lahzebar.commons.navigation.DestinationEvent
import app.lahzebar.commons.navigation.Navigator
import app.lahzebar.domain.model.room.Contact
import app.lahzebar.domain.model.room.ContactAndPhone
import app.lahzebar.domain.model.room.History
import app.lahzebar.domain.model.room.PhoneNumber
import app.lahzebar.features.adapter.SearchPhoneNumberAdapter
import app.lahzebar.features.keypad.databinding.FragmentKeypadBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import core.utils.safeLet
import core.views.base.BaseFragmentWithVM
import core.views.util.internationalPhone
import core.views.views.CustomKeypad
import java.text.SimpleDateFormat
import java.util.Date

class KeypadFragment :
    BaseFragmentWithVM<FragmentKeypadBinding, KeypadState, KeypadEffect, KeypadViewModel>(
        KeypadViewModel::class.java
    ) {

    private lateinit var searchPhoneNumberAdapter: SearchPhoneNumberAdapter
    private lateinit var backPressed: Date
    private var fakeCall = false
    private var multiplePermissionsListener: MultiplePermissionsListener? = null

    companion object {
        const val TAG = "KeypadFragmentTag"
        private val PERMISSIONS = listOf(
            permission.READ_CONTACTS,
            permission.WRITE_CONTACTS,
            permission.READ_CALL_LOG
        )
    }

    private var defaultDialerResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val telecomManager = activity?.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        val defaultDialerPackage = telecomManager.defaultDialerPackage
        val palphonePackageName = requireActivity().packageName
        if (defaultDialerPackage == palphonePackageName) {
            requestPermissions()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentKeypadBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root; }

    @SuppressLint("ClickableViewAccessibility")
    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)
        handleBack()

        // offer for set palphone as default calling app
        offerReplacingDefaultDialer()

        binding.containerKeypad.setOnTouchListener { v, event ->
            val inputMethodManager =
                v.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            val focusView = (v.context as Activity).currentFocus
            if (focusView != null) {
                focusView.clearFocus()
                inputMethodManager.hideSoftInputFromWindow(
                    focusView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
            false
        }

        binding.backIconIvFk.setOnClickListener {
            backToFullShow()
        }

        val items = mutableListOf(
            SearchedPhoneNumberItem(SpannableString("a"), SpannableString("12"), "", ""),
            SearchedPhoneNumberItem(SpannableString("b"), SpannableString("435"), "", ""),
            SearchedPhoneNumberItem(SpannableString("c"), SpannableString("sedsr"), "", ""),
        )
        searchPhoneNumberAdapter = SearchPhoneNumberAdapter(
            data = items,
            onClickListener = {
                // viewModel.searchClicked
                // backtoFullShow()
            }

        )
        binding.foundPhoneNumbersRvFk.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(context)
            adapter = searchPhoneNumberAdapter
        }

        binding.btnDial.setOnClickListener {
            /* if (!fakeCall) {
                 Log.d(TAG, "!fakeCall")
                 if (viewModel.getPhoneNumber().getText() != null && viewModel.getPhoneNumber()
                         .getText()
                         .length() > 0
                 ) {
                     val bundle = Bundle()
                     bundle.putString(InternetCallActivity.CALL_TYPE, NORMAL_CALL)
                     bundle.putString(
                         InternetCallActivity.PHONE_NUMBER,
                         viewModel.getPhoneNumber().getText()
                     )
                     if (SharedPreference.getInstance().getOutgoingCalls()) {
                         navController.navigate(R.id.action_global_pinCodeSecurityFragment, bundle)
                     } else {
                         ApplicationPublic.doCalling(activity, viewModel.getPhoneNumber().getText())
                     }
                 }
             } else {
                 Log.d(TAG, "ELSE !fakeCall")
                 val bundle = Bundle()
                 bundle.putString("args_number", binding.phoneNumberEtFk.text.toString())
                 navController.navigate(R.id.action_keypadFragment_to_fakeCallFragment, bundle)
             }
             viewModel.getPhoneNumber().setText("")
             viewModel.getDisplayName().setText("")*/
        }
        binding.phoneNumberEtFk.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // search box in top of layout clear
                binding.searchEtFk.text.clear()
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                manageView(s)
                // cursor of search box in top of layout clear
                binding.searchEtFk.isCursorVisible = false
            }

            override fun afterTextChanged(s: Editable) {
                val phone = s.toString().replace(" ".toRegex(), "")
                // viewModel.getPhoneNumber().setText(phone)
                if (s.length == 3) {
                    if (s.toString() == "000") {
                        // binding.callLayoutFk.setVisibility(View.INVISIBLE);
                        // viewModel.getPhoneNumber().setText("")
                        fakeCall = !fakeCall
                    }
                }
                binding.phoneNumberEtFk.setSelection(s.length)
            }
        })
        binding.menuProfileImage.setOnClickListener {
            (requireActivity() as? Navigator)?.navigateTo(DestinationEvent.Profile())
        }
    }

    override fun renderEffect(effect: KeypadEffect) {
    }

    override fun renderState(state: KeypadState) {
        when (state) {
            // KeypadState.Init -> {
            //     offerReplacingDefaultDialer()
            // }
            is KeypadState.Init -> {
                state.lastUpdateContact?.let {
                    lifecycleScope.launchWhenStarted {
                        getContact(it)
                    }
                }
            }
        }
    }

    private fun setInPutKeypad(editTextNumber: EditText, keyboard: CustomKeypad) {

        val obj = object : CustomKeypad.IonKeyPadListener {
            override fun onSwipeLeft() {
                // / STOPSHIP:
            }

            override fun onSwipeRight() {
                // / STOPSHIP:
            }

            override fun OnShowKeyBoardAndFocus() {
                binding.searchEtFk.requestFocus()
                val imm =
                    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.searchEtFk, InputMethodManager.SHOW_IMPLICIT)
            }

            override fun onDail() {
                // / STOPSHIP:
            }
        }
        editTextNumber.isFocusable = false
        val ic = editTextNumber.onCreateInputConnection(EditorInfo())
        keyboard.setInputConnection(ic)
    }

    private fun checkPermissionReadPhoneState(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_PHONE_STATE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.READ_PHONE_STATE),
                    4578
                )
            }
        }
    }

    private fun handleBack() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.activeKeypadFk.visibility == View.GONE) {
                    backToFullShow()
                } else {
                    binding.searchEtFk.text.clear()
                    resetSearch()
                    val now = Date()
                    if (now.time - backPressed.time < 3000L) activity!!.finish()
                    // CustomToast(resources.getString(R.string.EXIT_NOTE))
                    backPressed = now
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun backToFullShow() {
        binding.activeKeypadFk.visibility = View.VISIBLE
        binding.menuProfileImage.visibility = View.VISIBLE
        binding.searchEtFk.visibility = View.VISIBLE
        binding.searchIconIvFk.visibility = View.VISIBLE
        // binding.notificationIconIvFk.visibility=View.VISIBLE
        /*binding.addContactIconIvFk.visibility=View.VISIBLE
        binding.addContactTvFk.visibility=View.VISIBLE*/
        binding.backIconIvFk.visibility = View.GONE
        setSlideVisibility(binding.btnDial, true)
        val scale = requireContext().resources.displayMetrics.density
        val pixels = (150 * scale + 0.5f).toInt()
        val relativeParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, pixels
        )
        relativeParams.addRule(RelativeLayout.BELOW, R.id.search_et_fk)
        relativeParams.addRule(RelativeLayout.ABOVE, R.id.phone_number_et_fk)
        binding.foundPhoneNumbersRvFk.layoutParams = relativeParams
        binding.foundPhoneNumbersRvFk.layoutManager!!.scrollToPosition(0)
    }

    private fun setSlideVisibility(view: View, state: Boolean) {
        var animate = TranslateAnimation(0F, 0F, 0F, 0F)
        animate.duration = 500
        if (state) animate =
            TranslateAnimation(0F, 0F, view.height.toFloat(), 0F) else if (!state) animate =
            TranslateAnimation(
                0F, 0F, 0F,
                view.height.toFloat()
            )
        animate.duration = 500
        //        animate.setFillAfter(true);
        view.startAnimation(animate)
        view.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    fun resetSearch() {
        binding.phoneNumberEtFk.text!!.clear()
        /* viewModel.setDisplayName("")
         viewModel.setPhoneNumber("")*/
    }

    fun manageView(s: CharSequence?) {
        if (binding.searchEtFk.visibility == View.VISIBLE && s?.length!! > 2) {
            if (binding.btnDial.visibility == View.INVISIBLE) {
                setSlideVisibility(binding.btnDial, true)
            }
            if (s.toString()[0] == '0') {
                /*viewModel.searchPhoneNumber(
                    s.toString().replaceFirst("0".toRegex(), "").replace(" ", "")
                )*/
            } else {
                // viewModel.searchPhoneNumber(s.toString().replace(" ", ""))
            }
        } else if (binding.btnDial.visibility == View.VISIBLE) {
            setSlideVisibility(binding.btnDial, false)
        }
    }

    private fun requestRole() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = activity?.getSystemService(Context.ROLE_SERVICE) as RoleManager
            val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
            defaultDialerResult.launch(intent)
        }
    }

    private fun offerReplacingDefaultDialer() {
        // ask user to set this app as default caller
        val telecomManager = activity?.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        if (telecomManager.defaultDialerPackage != null && activity?.packageName != telecomManager.defaultDialerPackage) {
            val roleManager: RoleManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                roleManager = activity!!.getSystemService(RoleManager::class.java)
                if (roleManager.isRoleAvailable(RoleManager.ROLE_DIALER)) {
                    if (roleManager.isRoleHeld(RoleManager.ROLE_DIALER)) {
                    } else {
                        requestRole()
                    }
                }
            } else {
                startActivity(
                    Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER).putExtra(
                        TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME,
                        activity!!.packageName
                    )
                )
            }
        }
    }

    private fun requestPermissions() {
        multiplePermissionsListener = null
        // val lastUpdateContact = localDataSource.getLastUpdateContact().first()
        multiplePermissionsListener = object : MultiplePermissionsListener {
            override fun onPermissionsChecked(permissionResponse: MultiplePermissionsReport?) {
                val permissionGranted = permissionResponse?.grantedPermissionResponses
                if (permissionGranted?.isNotEmpty() == true) {
                    permissionGranted.forEach {
                        if (it.permissionName.equals(permission.READ_CONTACTS) || it.permissionName.equals(
                                permission.WRITE_CONTACTS
                            )
                        ) {

                            viewModel.emitAction(KeypadAction.PrepareReadContact)

                            Log.d(TAG, "onPermissionsChecked: getContact(0) " + getContact(0))
                        }
                        if (it.permissionName.equals(permission.READ_CALL_LOG)) {
                            viewModel.emitAction(KeypadAction.ReadHistory)
                            // getHistory()
                        }
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }
        }

        Dexter.withContext(requireActivity()).withPermissions(PERMISSIONS)
            .withListener(multiplePermissionsListener).check()
    }

    @SuppressLint("HardwareIds")
    private fun getDeviceId(): String {
        return Settings.Secure.getString(
            requireContext().contentResolver, Settings.Secure.ANDROID_ID
        )
    }

    private fun getContact(lastUpdateContact: Long) {
        val localContactsForInsert: MutableList<Contact> = mutableListOf()
        var contactAndPhoneNumbers: MutableList<ContactAndPhone> = mutableListOf()
        val phoneNumbersToAddLBD: MutableList<PhoneNumber> = mutableListOf()

        val projection = arrayOf(
            ContactsContract.Data.MIMETYPE,
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_URI,
            ContactsContract.Contacts.STARRED,
            ContactsContract.CommonDataKinds.Contactables.DATA,
            ContactsContract.CommonDataKinds.Contactables.DATA4,
            ContactsContract.CommonDataKinds.Contactables.TYPE,
            ContactsContract.CommonDataKinds.Contactables.LABEL,
            ContactsContract.CommonDataKinds.Contactables.CONTACT_LAST_UPDATED_TIMESTAMP
        )

        val selection = if (lastUpdateContact != 0L)
            ContactsContract.Data.MIMETYPE + " in (?, ?)" + " AND " +
                ContactsContract.Data.HAS_PHONE_NUMBER + " = '" + 1 + "'" + " AND " +
                ContactsContract.CommonDataKinds.Contactables.CONTACT_LAST_UPDATED_TIMESTAMP + " > " +
                lastUpdateContact
        else
            ContactsContract.Data.MIMETYPE + " in (?, ?)" + " AND " +
                ContactsContract.Data.HAS_PHONE_NUMBER + " = '" + 1 + "'"

        val selectionArgs = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
        )

        val uri = ContactsContract.Data.CONTENT_URI
        val cursor: Cursor? =
            context?.contentResolver?.query(uri, projection, selection, selectionArgs, null)
        var phoneNumbers: MutableList<PhoneNumber> = mutableListOf()
        var hashSet = HashSet<String>()
        var phonesForServer = HashSet<String>()
        var emailItems: MutableList<Contact.Email> = mutableListOf()

        if (cursor!!.count > 0) {
            val mimeTypeIdx = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)
            val idIdx = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
            val nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val dataIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA)
            val dataIdx4 =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA4)
            val photoInx =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.PHOTO_URI)
            val typeIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.TYPE)
            val lblIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.LABEL)
            val startedInx = cursor.getColumnIndex(ContactsContract.Contacts.STARRED)
            val lastUpdateInx =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.CONTACT_LAST_UPDATED_TIMESTAMP)

            var lastContactId = ""
            var finalPhoneNumber: String
            while (cursor.moveToNext()) {
                val contactId = cursor.getString(idIdx)
                val photoUri = cursor.getString(photoInx)
                val contactName = cursor.getString(nameIdx)
                val contactType = cursor.getInt(typeIdx)
                val contactTypeLabel = cursor.getString(lblIndex)
                val contactData = cursor.getString(dataIdx)
                val contactData4 = cursor.getString(dataIdx4)
                val favorite = cursor.getString(startedInx)
                val contactMimetype = cursor.getString(mimeTypeIdx)
                val lastUpdate = cursor.getString(lastUpdateInx)
                finalPhoneNumber = if (!TextUtils.isEmpty(contactData4)) {
                    contactData4
                } else {
                    contactData
                }
                if (lastContactId != contactId) {
                    phoneNumbers = mutableListOf()
                    hashSet = HashSet()
                    emailItems = mutableListOf()
                }
                lastContactId = contactId

                val contact = Contact(
                    contactId = contactId,
                    fullName = contactName,
                    isFavorite = (favorite == "1"),
                    imageUri = photoUri,
                    lastUpdateTime = lastUpdate
                )

                // here  checking Contact_mimeType to get mobile number associated with particular contact and email address associated
                if (contactMimetype == ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE) {
                    val typeEmail =
                        when (contactType) {
                            ContactsContract.CommonDataKinds.Email.TYPE_HOME -> context?.getString(
                                app.lahzebar.commons.R.string.HOME
                            )
                            ContactsContract.CommonDataKinds.Email.TYPE_WORK -> context?.getString(
                                app.lahzebar.commons.R.string.WORK
                            )
                            ContactsContract.CommonDataKinds.Email.TYPE_OTHER -> context?.getString(
                                app.lahzebar.commons.R.string.OTHER
                            )
                            ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM ->
                                contactTypeLabel
                                    ?: context?.getString(app.lahzebar.commons.R.string.OTHER)
                            else -> context?.getString(app.lahzebar.commons.R.string.OTHER)
                        }
                    if (!TextUtils.isEmpty(finalPhoneNumber) && Patterns.EMAIL_ADDRESS.matcher(
                            finalPhoneNumber
                        ).matches()
                    ) {
                        val emailItem = Contact.Email(
                            emailAddress = finalPhoneNumber,
                            title = typeEmail,
                            localContactFullName = contactName
                        )
                        emailItems.add(emailItem)
                    }
                }
                if (contactMimetype == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE) {
                    var typePhone = ""

                    typePhone =
                        when (contactType) {
                            ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> context?.getString(
                                app.lahzebar.commons.R.string.HOME
                            ).toString()
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> context?.getString(
                                app.lahzebar.commons.R.string.MOBILE
                            ).toString()
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> context?.getString(
                                app.lahzebar.commons.R.string.WORK
                            ).toString()
                            ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME -> context?.getString(
                                app.lahzebar.commons.R.string.HOME_FAX
                            ).toString()
                            ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK -> context?.getString(
                                app.lahzebar.commons.R.string.WORK_FAX
                            ).toString()
                            ContactsContract.CommonDataKinds.Phone.TYPE_PAGER -> context?.getString(
                                app.lahzebar.commons.R.string.PAGER
                            ).toString()
                            ContactsContract.CommonDataKinds.Phone.TYPE_OTHER -> context?.getString(
                                app.lahzebar.commons.R.string.OTHER
                            ).toString()
                            ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM -> (
                                contactTypeLabel
                                    ?: context?.getString(app.lahzebar.commons.R.string.OTHER)
                                ).toString()
                            else -> context?.getString(app.lahzebar.commons.R.string.OTHER)
                                .toString()
                        }
                    val phoneNumber = PhoneNumber(
                        phoneNumber = finalPhoneNumber,
                        type = typePhone,
                    )
                    phonesForServer.add(finalPhoneNumber)
                    phoneNumbersToAddLBD.add(phoneNumber)

                    safeLet(contact.id, phoneNumber.id) { cId, pId ->
                        ContactAndPhone(
                            cId,
                            pId
                        )
                    }?.let {
                        contactAndPhoneNumbers.add(it)
                    }

                    if (phoneNumber.phoneNumber?.let { hashSet.add(it) } == true) {
                        phoneNumbers.add(phoneNumber)
                    }
                }

                // todo
                /* if (phoneNumbers.size > 0) {
                     localHistoryRepository.getLocalHistoryDAO().updateCallerNameAnImagePhoneNumbers(
                         java.util.ArrayList<E>(hashSet),
                         localContact.getFullName(),
                         localContact.getImageUri()
                     )
                     localContact.setPhoneNumbers(phoneNumbers)
                     localContact.setPrimaryNumber(phoneNumbers[0].getPhoneNumber())
                 }*/

                if (emailItems.size > 0) contact.emails = emailItems

                if (contact.fullName.isNotEmpty()) {
                    localContactsForInsert.add(contact)
                }
            }
        }

        viewModel.emitAction(
            KeypadAction.ContactUpdated(
                localContactsForInsert,
                phoneNumbersToAddLBD,
                contactAndPhoneNumbers
            )
        )
    }

    private fun getHistory(): List<History> {
        val localHistoryForInsert: MutableList<History> = mutableListOf()
        val simpleDateFormatDay = SimpleDateFormat("yyyy.MM.dd")
        val simpleDateFormatDayTime = SimpleDateFormat("h:mm a")
        val queryUri =
            CallLog.Calls.CONTENT_URI.buildUpon().appendQueryParameter("limit", 800.toString())
                .build()
        val cursor: Cursor?
        val projection = arrayOf(
            CallLog.Calls._ID,
            CallLog.Calls.CACHED_NORMALIZED_NUMBER,
            CallLog.Calls.NUMBER,
            CallLog.Calls.TYPE,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            cursor = context?.contentResolver?.query(
                queryUri, // Content Uri is specific to individual content providers.
                projection, // String[] describing which columns to return.
                null,
                null
            )
        } else {
            cursor = context?.contentResolver?.query(
                CallLog.Calls.CONTENT_URI,
                projection, null, null, CallLog.Calls.DATE
            )
        }

        if (cursor?.count!! > 0) {
            while (cursor.moveToNext()) {

                val historyId = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID))
                val normalizedPhone =
                    cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NORMALIZED_NUMBER))
                val numberPhone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER))
                var phNumber = normalizedPhone.ifEmpty {
                    numberPhone
                }
                phNumber = internationalPhone(phNumber)

                val callType = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE))
                val callDateTime =
                    Date(java.lang.Long.valueOf(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE))))
                val callDay = simpleDateFormatDay.format(callDateTime)
                val callDayTime = simpleDateFormatDayTime.format(callDateTime)
                val callDuration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION))
                var dir: History.CallType? = null
                val dirCode = callType.toInt()
                when (dirCode) {
                    CallLog.Calls.OUTGOING_TYPE -> dir = History.CallType.OUT
                    CallLog.Calls.INCOMING_TYPE -> dir = History.CallType.IN
                    CallLog.Calls.MISSED_TYPE -> dir = History.CallType.MISS
                    CallLog.Calls.REJECTED_TYPE -> dir = History.CallType.REJECT
                    CallLog.Calls.BLOCKED_TYPE -> dir = History.CallType.BLOCKED
                }
                val history = History(
                    phNumber = phNumber,
                    callType = dir,
                    id = historyId,
                    callDate = callDateTime,
                    callDay = callDay,
                    callTime = callDayTime,
                    callDuration = callDuration

                )
                // todo
                /*if (phonesForServer == null) phonesForServer = HashSet<String>()
                phonesForServer.add(phNumber)

                val targetContact: LocalContact =
                    localContactRepository.getLocalContactDAO().getContactByNumber(phNumber)
                if (targetContact != null) {
                    localHistory.setCallerName(targetContact.getFullName())
                    localHistory.setContactImageUri(targetContact.getImageUri())
                }*/
                /* localHistoryRepository.getLocalHistoryDAO().insertLocalHistory(localHistory)*/
                localHistoryForInsert.add(history)
                if (cursor.isLast) {
                    // todo
                    // sharedPreference.setHistorySync(true)
                }
            }
            cursor.close()
            /*if (phonesForServer != null && !phonesForServer.isEmpty()) getInfoFromServer(
                ArrayList<String>(
                    phonesForServer
                )
            )*/
            // getPalIdForEachPhoneNumber(phoneNumbersForSearchPalId,false);
        } else {
            // sharedPreference.setHistorySync(true)
        }
        // sharedPreference.setPalIdsFromServer(false)
        return localHistoryForInsert
    }
}
