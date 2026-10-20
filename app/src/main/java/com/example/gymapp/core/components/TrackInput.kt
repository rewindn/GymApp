package com.example.gymapp.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.ui.theme.GymAppTheme

@Composable
fun TrackInput(modifier: Modifier = Modifier, imeAction: ImeAction, value: String, text: String, setinput : (String) -> Unit){

    OutlinedTextField(
        value = value,
        onValueChange = {input -> setinput(input) },
        label = { Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 16.sp,
                textAlign = TextAlign.Center

            )
        } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction,
            autoCorrect = false),
        singleLine = true,

        shape = RoundedCornerShape(100),
        colors = OutlinedTextFieldDefaults.colors(

        ),
        modifier = modifier
    )
}




@Preview
@Composable
fun TrackInputPreview(){
    GymAppTheme(darkTheme = false) {
        Surface() {
            TrackInput(Modifier.size(81.dp,66.dp), setinput = {}, value = "", imeAction = ImeAction.Done,text = "test")
        }
    }
}
