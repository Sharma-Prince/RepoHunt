package com.adishakti.repohunt.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardBackspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun AppBar(header: String, modifier: Modifier, onBackClick: () -> Unit) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        val (navigationIcon, headerId) = createRefs()
        Icon(
            modifier = Modifier
                .constrainAs(navigationIcon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(headerId.start, margin = 6.dp)
                    width = Dimension.value(56.dp)
                }
                .clickable {
                    onBackClick()
                },
            tint = MaterialTheme.colorScheme.scrim,
            imageVector = Icons.Rounded.KeyboardBackspace,
            contentDescription = "Back Navigation Arrow"
        )

        Text(
            modifier = Modifier.constrainAs(headerId) {
                top.linkTo(parent.top)
                end.linkTo(parent.end, margin = 48.dp)
                start.linkTo(navigationIcon.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            },
            text = header,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(24f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.scrim
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun AppBar(header: String, modifier: Modifier) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {

        Text(
            modifier = Modifier
                .padding(18.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth(),
            text = header,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(24f, TextUnitType.Sp),
                color = MaterialTheme.colorScheme.scrim
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TopAppBarPrev() {
    AppBar("Prince", modifier = Modifier, onBackClick = {
    })
}