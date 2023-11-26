package com.nsicyber.coiintrackerapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text


@Composable
fun ApiResultDialog(dialogType : DialogType = DialogType.SUCCESS, body : String?, isNegativeButtonEnabled : Boolean = false, onResult : (Boolean) -> Unit){
    CustomDialogPopUp(
        title = {
            Image(modifier = Modifier.padding(top = 24.dp, bottom = 16.dp).size(48.dp), painter = painterResource(id = when(dialogType){
                DialogType.SUCCESS -> com.nsicyber.coiintrackerapp.R.drawable.icon_success
                DialogType.ERROR -> com.nsicyber.coiintrackerapp.R.drawable.icon_danger
                DialogType.INFO -> com.nsicyber.coiintrackerapp.R.drawable.icon_info
            } ), contentDescription = "")
        },
        isNegativeButtonEnabled = isNegativeButtonEnabled,
        isPositiveButtonEnabled = true,
        content = {
            Text(
                modifier = Modifier.padding(horizontal = 66.dp),
                text = body?:"",
                textAlign = TextAlign.Center,
            )
        },
        onDialogNegativeButtonClicked = {
            onResult(false)
        },
        positiveButtonText = "Tamam",
        negativeButtonText = if(isNegativeButtonEnabled) "İptal" else "",
        onDialogPositiveButtonClicked = {
            onResult(true)
        }
    )

}

enum class DialogType{
    ERROR,
    INFO,
    SUCCESS
}