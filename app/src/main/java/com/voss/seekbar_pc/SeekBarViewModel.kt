package com.voss.seekbar_pc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeekBarViewModel : ViewModel() {
    val value = MutableLiveData<Float>()
    val percent = MutableLiveData<Int>()
    val price = MutableLiveData<Int>()
    val discount = MutableLiveData<Float>()
    val total = MutableLiveData<Float>()
    init{
        value.value = 0f
        percent.value = 0
        price.value = 0
        discount.value = 0f
        total.value = 0f
    }
}