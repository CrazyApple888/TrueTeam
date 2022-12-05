package ru.nsu.alphacontest.design.dialogs.utils

import androidx.fragment.app.DialogFragment

fun DialogFragment.dismissIfVisible() {
    if(isVisible) dismiss()
}