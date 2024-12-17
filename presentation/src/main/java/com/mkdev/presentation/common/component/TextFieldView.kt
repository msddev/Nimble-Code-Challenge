package com.mkdev.presentation.common.component

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import com.mkdev.presentation.theme.Dimens

@Composable
internal fun TextFieldView(
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
                .padding(horizontal = Dimens.PaddingStandard, vertical = Dimens.Padding2xSmall),
            text = stringResource(errorResId),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}