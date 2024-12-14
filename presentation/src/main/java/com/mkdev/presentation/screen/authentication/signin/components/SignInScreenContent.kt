package com.mkdev.presentation.screen.authentication.signin.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.mkdev.presentation.R
import com.mkdev.presentation.model.local.TextFieldErrorModel
import com.mkdev.presentation.theme.CommonColors
import com.mkdev.presentation.theme.Dimens
import com.mkdev.presentation.common.utils.isValidEmail
import com.mkdev.presentation.common.utils.isValidPassword

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

    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = Dimens.BlurEffect50dp),
            painter = painterResource(R.drawable.img_splash_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(CommonColors.GradientBlack2))
        )
    }

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
            .padding(Dimens.PaddingStandard)
            .verticalScroll(rememberScrollState()),
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

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeightMedium),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
            ),
            onClick = {
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
        ) {
            Text(
                text = stringResource(R.string.log_in_text),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
private fun TextFieldView(
    modifier: Modifier,
    textState: MutableState<String>,
    hint: String,
    maxLength: Int = 25,
    isError: Boolean,
    @StringRes errorResId: Int?,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    onKeyboardDoneActionInvoked: (() -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = modifier.border(
            width = Dimens.Border1dp,
            color = Color.White.copy(alpha = 0.2f),
            shape = CircleShape
        ),
        shape = CircleShape,
        value = textState.value,
        onValueChange = {
            if (it.length <= maxLength) {
                textState.value = it
            }
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
        maxLines = 1,
        placeholder = {
            Text(text = hint, style = MaterialTheme.typography.bodyLarge)
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            onKeyboardDoneActionInvoked?.invoke()
        }),
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White.copy(alpha = 0.2f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
            cursorColor = Color.White.copy(alpha = 0.2f),
            errorIndicatorColor = Color.Transparent,
            errorTextColor = MaterialTheme.colorScheme.error,
            errorContainerColor = Color.White.copy(alpha = 0.2f),
            errorCursorColor = MaterialTheme.colorScheme.error,
            errorPlaceholderColor = Color.White.copy(alpha = 0.5f),
        ),
    )
    if (isError && errorResId != null) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.PaddingStandard, vertical = Dimens.Padding3xSmall),
            text = stringResource(errorResId),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
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
    val isPasswordValid = password.isNotBlank() && password.isValidPassword()

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