package com.nsicyber.coiintrackerapp.viewmodel

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.NetworkException
import android.os.Environment
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.Locale


open class BaseViewModel : ViewModel() {


    private var _isBusy = mutableStateOf(false)
    val isBusy: State<Boolean> = _isBusy

    val takeCount = 20

    var listener: (() -> Unit)? = null

    private var _isDialogBusy = mutableStateOf(false)
    val isDialogBusy: State<Boolean> = _isDialogBusy

    private var _successDialogState = mutableStateOf(false)
    val successDialogState: State<Boolean> = _successDialogState

    private var _errorDialogState = mutableStateOf(false)
    val errorDialogState: State<Boolean> = _errorDialogState

    var previewDialogState = mutableStateOf(false)

    private var _previewDialogType = mutableStateOf<String?>(null)
    val previewDialogType: State<String?> = _previewDialogType

    private var _previewDialogUri = mutableStateOf<String?>(null)
    val previewDialogUri: State<String?> = _previewDialogUri

    private var _errorDialogContent = mutableStateOf("")
    val errorDialogContent: State<String> = _errorDialogContent

    private var _successDialogContent = mutableStateOf("")
    val successDialogContent: State<String> = _successDialogContent

    fun setBusy(value: Boolean) {
        _isBusy.value = value;
    }

    private fun setDialogBusy(value: Boolean) {
        _isDialogBusy.value = value;
    }

    fun setSuccessDialogState(
        value: Boolean,
        body: String? = null,
        callBack: (() -> Unit)? = null
    ) {
        _successDialogState.value = value
        _successDialogContent.value = body ?: ""
        listener = callBack
    }

    fun setErrorDialogState(value: Boolean, body: String? = null) {
        _errorDialogState.value = value
        _errorDialogContent.value = body ?: ""
    }






    fun apiCall(
        isDialog: Boolean = false,
        isBusyEnabled: Boolean = true,
        call: suspend () -> Unit
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            if (isBusyEnabled) if (isDialog) setDialogBusy(true) else setBusy(true);
            try {
                call();
                if (isBusyEnabled) if (isDialog) setDialogBusy(false) else setBusy(false);
            } catch (e: Exception) {
                when (e) {
                    //java.util.concurrent.CancellationException: olduğu zaman response handler tarafına bak
                    is ApiException -> setErrorDialogState(true, e.message)
                    //is UnauthorizedException -> navHostController!!.navigate(login)
                    is IOException -> setErrorDialogState(true, e.message)
                    is CancellationException -> setErrorDialogState(true, "Bir Hata Oluştu")
                    else -> {
                        if (e !is com.google.gson.JsonSyntaxException) {
                            setErrorDialogState(true, e.message);
                            if (isDialog) setDialogBusy(false) else setBusy(false);
                        }

                    }
                }
                if (isBusyEnabled) if (isDialog) setDialogBusy(false) else setBusy(false)
            }
        }
    }

    fun onSuccessCallback() {
        listener?.let { it() }
        listener = null
    }

    fun onActivityResult(activity: Activity) {
        val resultIntent = Intent()
        resultIntent.putExtra("some_key", "String data")
        activity.setResult(Activity.RESULT_OK, resultIntent);
        activity.finish();

    }

    fun onDismissRequest() {
        previewDialogState.value = false
        _previewDialogType.value = null
    }


}