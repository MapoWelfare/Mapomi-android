package io.mapomi.android.constants

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import io.mapomi.android.system.LogError
import io.mapomi.android.system.LogInfo
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

fun getHashKey(context : Context) {
    var packageInfo: PackageInfo? = null
    try {
        packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    if (packageInfo == null) LogError("KeyHash", "KeyHash:null")
    for (signature in packageInfo!!.signatures) {
        try {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            //Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            LogInfo("KeyHash", Base64.getEncoder().encodeToString(md.digest()))
        } catch (e: NoSuchAlgorithmException) {
            LogInfo("KeyHash", "Unable to get MessageDigest. signature=$signature")

        }
    }

}