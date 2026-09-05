package com.github.edwingsanchez.lgtvcontroller.ui.controller

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.github.edwingsanchez.lgtvcontroller.ui.theme.LGTVControllerTheme

val ButtonShape = ShapeDefaults.ExtraLarge
val ControlsSpacing = 6.dp

@Composable
fun CTextButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonShape,
    fontSize: TextUnit = 3.7.em,
    colors: ButtonColors? = null,
    onClick: () -> Unit = {},
) {
    CButton(
        enabled = enabled,
        shape = shape,
        modifier = modifier,
        colors = colors,
        onClick = onClick
    ) {
        Text(text, fontSize = fontSize)
    }
}

@Composable
fun CIconButton(
    iconId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonShape,
    useDefaultTint: Boolean = false,
    colors: ButtonColors? = null,
    iconSize: Dp = 32.dp,
    onClick: () -> Unit = {},
) {
    val color = if (useDefaultTint) Color.Unspecified else null
    CButton(
        enabled = enabled,
        shape = shape,
        modifier = modifier,
        colors = colors,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        if (color == null) {
            Icon(painterResource(iconId), contentDescription, modifier = Modifier.size(iconSize))
        } else {
            Icon(
                painterResource(iconId),
                contentDescription,
                modifier = Modifier.size(iconSize),
                tint = color
            )
        }
    }
}

@Composable
fun CIconButton(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonShape,
    useDefaultTint: Boolean = false,
    colors: ButtonColors? = null,
    iconSize: Dp = 32.dp,
    onClick: () -> Unit = {},
) {
    val color = if (useDefaultTint) Color.Unspecified else null
    CButton(
        enabled = enabled,
        shape = shape,
        modifier = modifier,
        colors = colors,
        contentPadding = PaddingValues(0.dp),
        onClick = onClick
    ) {
        if (color == null) {
            Icon(imageVector = imageVector, contentDescription, modifier = Modifier.size(iconSize))
        } else {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize),
                tint = color
            )
        }
    }
}

@Composable
fun CButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonShape,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    colors: ButtonColors? = null,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        contentPadding = contentPadding,
        colors = colors ?: ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        elevation = if (colors?.containerColor == Color.Transparent) null else ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        modifier = modifier,
        content = content
    )
}

@Composable
fun RowScope.VerticalControls(
    centerText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    topButton: @Composable () -> Unit,
    bottomButton: @Composable () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        color = if (enabled) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            topButton()

            Text(
                text = centerText.uppercase(),
                modifier = Modifier.padding(vertical = 2.dp),
                fontSize = 2.2.em,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = if (enabled) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                style = MaterialTheme.typography.labelSmall
            )

            bottomButton()
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun PreviewLight() {
    LGTVControllerTheme {
        CTextButton("Hello")
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewDark() {
    LGTVControllerTheme {
        CTextButton("Hello")
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun PreviewLightDisabled() {
    LGTVControllerTheme {
        CTextButton("Hello", enabled = false)
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewDarkDisabled() {
    LGTVControllerTheme {
        CTextButton("Hello", enabled = false)
    }
}