package com.mkdev.presentation.screen.authentication.signin.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.mkdev.presentation.R
import com.mkdev.presentation.common.component.BackgroundView
import com.mkdev.presentation.common.component.ButtonView
import com.mkdev.presentation.common.component.TextFieldView
import com.mkdev.presentation.common.utils.isValidEmail
import com.mkdev.presentation.model.local.TextFieldErrorModel
import com.mkdev.presentation.theme.Dimens

@Composable
internal fun SignInScreenContent(
    modifier: Modifier,
    onForgotPasswordClick: () -> Unit = {},
    onLoginClick: (email: String, password: String) -> Unit,
) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    val emailError = remember { mutableStateOf(TextFieldErrorModel()) }
    val passwordError = remember { mutableStateOf(TextFieldErrorModel()) }

    val keyboardController = LocalSoftwareKeyboardController.current
    keyboardController?.hide()

    BackgroundView(modifier = modifier)

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                painter = painterResource(R.drawable.img_nimble_logo),
                contentDescription = null,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
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

        TextFieldView(
            modifier = Modifier.fillMaxWidth(),
            textState = passwordState,
            visualTransformation = PasswordVisualTransformation(mask = '●'),
            hint = stringResource(R.string.password_text),
            isError = passwordError.value.isError,
            errorResId = passwordError.value.errorText,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                Text(
                    text = stringResource(R.string.forgot_text),
                    modifier = Modifier
                        .padding(horizontal = Dimens.PaddingStandard)
                        .clickable {
                            onForgotPasswordClick()
                        },
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            onKeyboardDoneActionInvoked = {
                keyboardController?.hide()

                validateAndLogin(
                    email = emailState.value,
                    password = passwordState.value,
                    onError = { emailModel, passwordModel ->
                        emailError.value = emailError.value.copy(
                            isError = emailModel.isError,
                            errorText = emailModel.errorText,
                        )
                        passwordError.value = passwordError.value.copy(
                            isError = passwordModel.isError,
                            errorText = passwordModel.errorText,
                        )
                    },
                    onLoginClick = onLoginClick,
                )
            }
        )

        Spacer(modifier = Modifier.height(Dimens.PaddingStandard))


        ButtonView(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeightMedium),
            text = stringResource(R.string.log_in_text),
            onClick = {
                keyboardController?.hide()

                validateAndLogin(
                    email = emailState.value,
                    password = passwordState.value,
                    onError = { emailModel, passwordModel ->
                        emailError.value = emailError.value.copy(
                            isError = emailModel.isError,
                            errorText = emailModel.errorText,
                        )
                        passwordError.value = passwordError.value.copy(
                            isError = passwordModel.isError,
                            errorText = passwordModel.errorText,
                        )
                    },
                    onLoginClick = onLoginClick,
                )
            },
        )
    }
}

private fun validateAndLogin(
    email: String,
    password: String,
    onError: (TextFieldErrorModel, TextFieldErrorModel) -> Unit,
    onLoginClick: (String, String) -> Unit,
) {
    val isEmailValid = email.isNotBlank() && email.isValidEmail()
    val isPasswordValid = password.isNotBlank()

    onError(
        TextFieldErrorModel(
            isError = !isEmailValid,
            errorText = if (!isEmailValid) R.string.email_empty_error else null
        ),

        TextFieldErrorModel(
            isError = !isPasswordValid,
            errorText = if (!isPasswordValid) R.string.password_empty_error else null
        ),
    )

    if (isEmailValid && isPasswordValid) {
        onLoginClick(email, password)
    }
}