package com.mkdev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkdev.domain.usecase.GetSurveysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getSurveysUseCase: GetSurveysUseCase,
) : ViewModel() {

    /*val surveys: Flow<PagingData<SurveyModel>> = getSurveysUseCase()
        .cachedIn(viewModelScope)*/

    fun getSurveys() {
        viewModelScope.launch {
            getSurveysUseCase().onEach { result ->

            }.launchIn(this)
        }
    }
}