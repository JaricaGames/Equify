package com.jarica.compartirgastos.presentation.newGroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewGroupViewModel @Inject constructor() : ViewModel(){

    private val _groupName = MutableLiveData<String>()
    val groupName: LiveData<String> = _groupName

    private val _alphaTextNext = MutableLiveData<Float>()
    val alphaTextNext: LiveData<Float> = _alphaTextNext

    //metodo del textfield
    fun onValueTextFieldChange(text: String) {
         _groupName.value = text
        if(text.equals("")){
                _alphaTextNext.value = 0f
            }else{
            _alphaTextNext.value = 1f
        }
    }

}