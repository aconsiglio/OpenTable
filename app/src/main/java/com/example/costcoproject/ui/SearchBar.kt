package com.example.costcoproject.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.costcoproject.R
import kotlinx.android.synthetic.main.search_bar_view.view.*

class SearchBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.search_bar_view, this, true)
        hintBtn?.setOnClickListener {
            val popup = PopupMenu(context, it)
            popup.menuInflater.inflate(R.menu.hint_pop_up_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                updateHint(item.title)
                true
            }
            popup.show()
        }
        textLayout.isHintEnabled = false
    }

    private fun updateHint(hint: CharSequence) {
        editText.text?.clear()
        editText.hint = hint.toString()

    }
}