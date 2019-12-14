package cash.z.ecc.android.ext

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import cash.z.ecc.android.ZcashWalletApp

/**
 * Grab a color out of the application resources, using the default theme
 */
@ColorInt
internal inline fun @receiver:ColorRes Int.toAppColor(): Int {
    return ResourcesCompat.getColor(ZcashWalletApp.instance.resources, this, ZcashWalletApp.instance.theme)
}

/**
 * Grab a string from the application resources
 */
internal inline fun @receiver:StringRes Int.toAppString(): String {
    return ZcashWalletApp.instance.getString(this)}


/**
 * Grab an integer from the application resources
 */
internal inline fun @receiver:IntegerRes Int.toAppInt(): Int {
    return ZcashWalletApp.instance.resources.getInteger(this)}

