package com.mkdev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mkdev.domain.entity.survey.SurveyModel
import com.mkdev.domain.usecase.GetSurveysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    getSurveysUseCase: GetSurveysUseCase,
) : ViewModel() {

    @OptIn(FlowPreview::class)
    val surveysPaging: Flow<PagingData<SurveyModel>> =
        getSurveysUseCase().cachedIn(viewModelScope)
}