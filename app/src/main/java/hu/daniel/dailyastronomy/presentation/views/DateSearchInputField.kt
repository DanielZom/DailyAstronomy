package hu.daniel.dailyastronomy.presentation.views

import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.util.extensions.formatToYearMonthDate
import kotlinx.android.synthetic.main.component_search_field.view.*
import java.util.*


class DateSearchInputField(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    var minDate: Calendar? = null
    var maxDate: Calendar? = null
    var lastSearchedFormattedDate: String? = null

    private var searchListener: ((Calendar) -> Unit)? = null
    private var deleteListener: (() -> Unit)? = null
    private var initialCalendar = Calendar.getInstance()

    init {
        inflate(context, R.layout.component_search_field, this)

        searchClickListenerView.setOnClickListener { showDatePicker() }
        deleteClickListenerView.setOnClickListener {
            lastSearchedFormattedDate = null
            if (dateInputField.text.toString().isNotBlank()) {
                dateInputField.text = null
                initialCalendar = Calendar.getInstance()
                deleteListener?.invoke()
            }
        }
    }

    fun setOnSearchListener(listener: (Calendar) -> Unit) {
        searchListener = listener
    }

    fun setOnTextDeleteListener(listener: () -> Unit) {
        deleteListener = listener
    }

    private fun showDatePicker() {
        val dialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
            val searchedDate = Calendar.getInstance().also { calendar ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            val formattedDate = searchedDate.time.formatToYearMonthDate()

            lastSearchedFormattedDate = formattedDate
            searchListener?.invoke(searchedDate)
            dateInputField.text = formattedDate

            initialCalendar = searchedDate
        }, initialCalendar.get(Calendar.YEAR), initialCalendar.get(Calendar.MONTH), initialCalendar.get(Calendar.DATE))
        minDate?.let { date -> dialog.datePicker.minDate = date.timeInMillis }
        maxDate?.let { date -> dialog.datePicker.maxDate = date.timeInMillis }
        dialog.show()
    }
}