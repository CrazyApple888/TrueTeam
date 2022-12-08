package me.idrew.main_cards.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import me.idrew.main_cards.domain.location.PermissionChecker

class PermissionCheckerImpl(
    private val context: Context
) : PermissionChecker {

    override fun isPermissionLocationGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
}