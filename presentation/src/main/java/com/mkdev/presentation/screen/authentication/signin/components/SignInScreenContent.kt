package com.mkdev.presentation.screen.authentication.signin.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mkdev.presentation.R
import com.mkdev.presentation.theme.CommonColors
import com.mkdev.presentation.theme.Dimens

@Composable
internal fun SignInScreenContent(
    modifier: Modifier,
    onForgotPasswordClick: () -> Unit = {},
    onKeyboardDoneActionInvoked: () -> Unit,
) {
    var emailValue = remember { mutableStateOf("") }
    var passwordValue = remember { mutableStateOf("") }

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
            textState = emailValue,
            hint = stringResource(R.string.email_text),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
        )

        Spacer(modifier = Modifier.height(Dimens.PaddingStandard))

        TextFieldView(
            modifier = Modifier.fillMaxWidth(),
            textState = passwordValue,
            visualTransformation = PasswordVisualTransformation(),
            hint = stringResource(R.string.password_text),
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
            onKeyboardDoneActionInvoked = onKeyboardDoneActionInvoked
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = { }
        ) {
            Text("Log in")
        }
    }
}

@Composable
private fun TextFieldView(
    modifier: Modifier,
    textState: MutableState<String>,
    hint: String,
    maxLength: Int = 25,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    onKeyboardDoneActionInvoked: (() -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = modifier,
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
        ),
    )
}