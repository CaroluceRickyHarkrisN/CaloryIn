package com.relevanx.capstone_v1.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.relevanx.capstone_v1.R

class CustomPassword(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private val minimal = 8

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                error = if ((s?.length ?: 0) < minimal) {
                    context.getString(R.string.password_8_karakter)
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}