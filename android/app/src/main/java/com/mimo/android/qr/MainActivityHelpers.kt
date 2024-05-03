package com.mimo.android.qr

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanOptions
import com.mimo.android.MainActivity

//fun MainActivity.createBarcodeLauncher(context: Context): ActivityResultLauncher<ScanOptions>{
//    return registerForActivityResult(ScanContract()) {
//            result ->
//        if (result.contents == null) {
//            Toast.makeText(
//                context,
//                "취소",
//                Toast.LENGTH_SHORT
//            ).show()
//            return@registerForActivityResult
//        }
//        // textResult.value = result.contents
//        Toast.makeText(
//            context,
//            "${result.contents}",
//            Toast.LENGTH_SHORT
//        ).show()
//    }
//}

fun MainActivity.createQRRequestPermissionLauncher(barCodeLauncher: ActivityResultLauncher<ScanOptions>): ActivityResultLauncher<String> {
    return registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
            isGranted ->
        if (isGranted) {
            showCamera(barCodeLauncher)
        }
    }
}

fun MainActivity.checkCameraPermission(
    context: Context,
    barCodeLauncher: ActivityResultLauncher<ScanOptions>,
    qRRequestPermissionLauncher: ActivityResultLauncher<String>
){
    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        showCamera(barCodeLauncher)
        return
    }

    if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
        Toast.makeText(context, "Camera required", Toast.LENGTH_SHORT).show()
        return
    }

    qRRequestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
}

fun showCamera(barCodeLauncher: ActivityResultLauncher<ScanOptions>){
    val options = ScanOptions()
    options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
    options.setPrompt("QR코드를 스캔해주세요")
    options.setCameraId(0)
    options.setBeepEnabled(false)
    options.setOrientationLocked(true)
    barCodeLauncher.launch(options)
}