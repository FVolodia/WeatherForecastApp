package com.weatherforecastapp.utils

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class PermissionHelper(
    private val fragment: Fragment,
    private val permissionRequestCode: Int,
    private val doFunction: () -> Unit,
    private val permissionDenied: () -> Unit
) {

    fun checkPermission(vararg permission: String) {
        if (allPermissionsGranted(permission)) {
            doFunction()
        } else {
            fragment.requestPermissions(
                permission,
                permissionRequestCode
            )
        }
    }

    @Suppress("UNUSED_PARAMETER", "ControlFlowWithEmptyBody")
    fun checkPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            permissionRequestCode -> {
                if (allPermissionsGranted(permissions)) {
                    doFunction()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    permissionDenied()
                }
                return
            }
        }
    }

    private fun allPermissionsGranted(permissions: Array<out String>) = permissions.all {
        ContextCompat.checkSelfPermission(
            fragment.requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }
}