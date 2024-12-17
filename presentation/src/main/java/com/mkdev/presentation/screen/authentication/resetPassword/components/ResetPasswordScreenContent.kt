package com.mkdev.presentation.screen.authentication.resetPassword.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.mkdev.presentation.R
import com.mkdev.presentation.common.component.BackgroundView
import com.mkdev.presentation.common.component.ButtonView
import com.mkdev.presentation.common.component.TextFieldView
import com.mkdev.presentation.common.component.TopAppBarBackButton
import com.mkdev.presentation.common.utils.isValidEmail
import com.mkdev.presentation.model.local.TextFieldErrorModel
import com.mkdev.presentation.theme.Dimens

@Composable
internal fun ResetPasswordScreenContent(
    modifier: Modifier,
    onResetClick: (email: String) -> Unit = {},
    onBackClick: () -> Unit,
) {
    val emailState = remember { mutableStateOf("") }

    val emailError = remember { mutableStateOf(TextFieldErrorModel()) }

    val keyboardController = LocalSoftwareKeyboardController.current
    keyboardController?.hide()

    BackgroundView(modifier = modifier)

    Column(modifier = modifier) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .statusBarsPadding()
                .padding(horizontal = Dimens.PaddingSmall)
        ) {
            TopAppBarBackButton(onClick = onBackClick)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.PaddingStandard)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .wrapContentSize(),
                painter = painterResource(R.drawable.img_nimble_logo),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.enter_your_email),
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.2f)
        )
    }

    Column(
        modifier = modifier
            .padding(Dimens.PaddingStandard),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldView(
            modifier = Modifier.fillMaxWidth(),
            textState = emailState,
            hint = stringResource(R.string.email_text),
            isError = emailError.value.isError,
            errorResId = emailError.value.errorText,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
        )

        Spacer(modifier = Modifier.height(Dimens.PaddingStandard))

        ButtonView(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeightMedium),
            text = stringResource(R.string.reset_text),
            onClick = {
                keyboardController?.hide()

                validateEmailAndResetPassword(
                    email = emailState.value,
                    onError = { emailModel ->
                        emailError.value = emailError.value.copy(
                            isError = emailModel.isError,
                            errorText = emailModel.errorText,
                        )
                    },
                    onResetClick = onResetClick,
                )
            },
        )
    }
}

private fun validateEmailAndResetPassword(
    email: String,
    onError: (TextFieldErrorModel) -> Unit,
    onResetClick: (email: String) -> Unit,
) {
    val isEmailValid = email.isNotBlank() && email.isValidEmail()

    onError(
        TextFieldErrorModel(
            isError = !isEmailValid,
            errorText = if (!isEmailValid) R.string.email_empty_error else null
        ),
    )

    if (isEmailValid) {
        onResetClick(email)
    }
}