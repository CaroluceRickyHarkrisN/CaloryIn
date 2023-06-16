package com.relevanx.capstone_v1.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.relevanx.capstone_v1.R

class CustomEmail(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    init {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val emailPattern = Patterns.EMAIL_ADDRESS
                error = if (!emailPattern.matcher(text.toString()).matches()) {
                    context.getString(R.string.format_email_salah)
                } else {
                    null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}