package com.voss.progresscontrol

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProgressViewModel : ViewModel() {
    val setProgressValue = MutableLiveData<Int>()
    private var currentProgress: Int = 0

    init {
        currentProgress = 0
        setProgressValue.value = currentProgress

    }
}