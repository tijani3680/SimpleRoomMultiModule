package app.lahzebar.features.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import app.lahzebar.commons.util.Loader
import app.lahzebar.domain.model.Bubble
import app.lahzebar.features.menu.databinding.FragmentEditBinding
import core.views.base.BaseFragmentWithVM
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditFragment :
    BaseFragmentWithVM<FragmentEditBinding, EditState, EditEffect, EditViewModel>(EditViewModel::class.java) {
    companion object {
        const val CONVERT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val BUBBLE_UPDATED = "Bubble Updated"
    }

    private val initialState
        get() = viewModel.state.value as? EditState.Init

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEditBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)
        binding.doneTvFep.setOnClickListener {
            initialState?.data?.let {
                if (it.nature == true) {
                    val dayOfMonth = binding.birthDatePickerFep.dayOfMonth
                    val month = binding.birthDatePickerFep.month + 1
                    val year = binding.birthDatePickerFep.year
                    val birthDate = "$year-$month-${dayOfMonth}T00:00:00.000Z"
                    viewModel.emitAction(
                        EditAction.UpdateProfile(
                            Bubble(
                                firstName = binding.firstNameEtFep.text.toString(),
                                lastName = binding.lastNameEtFep.text.toString(),
                                description = binding.decsEtFep.text.toString(),
                                dateOfBirth = birthDate
                            )
                        )
                    )
                } else {
                    viewModel.emitAction(
                        EditAction.UpdateProfile(
                            Bubble(
                                companyName = binding.companyNameEtFep.text.toString(),
                                slogan = binding.sloganNumEtFep.text.toString()
                            )
                        )
                    )
                }
            }
        }
    }

    override fun renderEffect(effect: EditEffect) {
        when (effect) {
            is EditEffect.Error -> {
                Toast.makeText(requireContext(), "${effect.error}", Toast.LENGTH_SHORT).show()
            }
            EditEffect.Navigate -> {
                findNavController().navigateUp()
            }
        }
    }

    override fun renderState(state: EditState) {
        when (state) {
            is EditState.Init -> renderInitState(state)
        }
    }

    private fun renderInitState(state: EditState.Init) {
        state.data?.let {
            (requireActivity() as? Loader)?.loader(false)
            binding.apply {
                if (it.nature == true) {
                    firstNameEtFep.setText(it.firstName)
                    lastNameEtFep.setText(it.lastName)
                    decsEtFep.setText(it.description)
                    it.dateOfBirth?.let {
                        val date = convertDate(it)
                        date.apply {
                            birthDatePickerFep.init(first, second, third, null)
                        }
                    }
                    companyNameTvFep.visibility = View.GONE
                    companyNameTextfieldLayoutFep.visibility = View.GONE
                    sloganTvFep.visibility = View.GONE
                    sloganTextfieldLayoutFep.visibility = View.GONE
                } else {
                    companyNameEtFep.setText(it.companyName)
                    sloganNumEtFep.setText(it.slogan)

                    firstNameEtFep.visibility = View.GONE
                    firstNameTextfieldLayoutFep.visibility = View.GONE
                    lastNameTvFep.visibility = View.GONE
                    lastNameTextfieldLayoutFep.visibility = View.GONE
                    descTvFep.visibility = View.GONE
                    descTextfieldLayoutFep.visibility = View.GONE
                }
            }
        } ?: run {
            viewModel.emitAction(EditAction.LoadData)
            (requireActivity() as? Loader)?.loader(true)
        }
    }

    private fun convertDate(stringDate: String): Triple<Int, Int, Int> {
        val date = SimpleDateFormat(CONVERT_DATE_FORMAT, Locale.US).parse(stringDate)
        val calendar = Calendar.getInstance()
        date?.let { calendar.time = it }
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        return Triple(year, month, day)
    }
}
