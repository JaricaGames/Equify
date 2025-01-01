package com.jarica.compartirgastos.presentation.addCostScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddCostScreenViewModel @Inject constructor():ViewModel(){

    private val _descriptionText = MutableLiveData<String>()
    val descriptionText: LiveData<String> = _descriptionText

    private val _amountText = MutableLiveData<String>()
    val amountText: LiveData<String> = _amountText




    fun onDescriptionChange(descriptionText: String) {
        _descriptionText.value = descriptionText
    }

    fun onAmountChange(amount: String) {
        _amountText.value = amount

    }



}