package cash.z.ecc.android.ui.restore

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import cash.z.ecc.android.R
import cash.z.ecc.android.ZcashWalletApp
import cash.z.ecc.android.di.component.RestoreActivitySubcomponent
import cash.z.ecc.android.di.component.SynchronizerSubcomponent
import cash.z.ecc.android.ui.setup.RestoreFragment

class RestoreActivity : AppCompatActivity(R.layout.restore_activity) {

    lateinit var component: RestoreActivitySubcomponent
    lateinit var synchronizerComponent: SynchronizerSubcomponent

    val isInitialized get() = ::synchronizerComponent.isInitialized

    val latestHeight: Int? get() = if (isInitialized) {
        synchronizerComponent.synchronizer().latestHeight
    } else {
        null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = ZcashWalletApp.component.restoreActivitySubcomponent().create(this).also {
            it.inject(this)
        }
        addBottomAndTopNavBarColors()
        addRestoreFragment()
    }

    suspend fun isValidAddress(address: String): Boolean {
        try {
            return !synchronizerComponent.synchronizer().validateAddress(address).isNotValid
        } catch (t: Throwable) { }
        return false
    }

    private fun addBottomAndTopNavBarColors() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // finally change the color
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.pirate_wallet_bg))
                window.setNavigationBarColor(ContextCompat.getColor(this, R.color.pirate_wallet_bg))
            } else {
                window.setStatusBarColor(resources.getColor(R.color.pirate_wallet_bg))
                window.setNavigationBarColor(resources.getColor(R.color.pirate_wallet_bg))
            }
        }
    }

    fun showKeyboard(focusedView: View) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(focusedView, InputMethodManager.SHOW_FORCED)
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
    }

    fun addRestoreFragment() {
        val restoreFragment: RestoreFragment = RestoreFragment()

        val fragmentManager = supportFragmentManager

        val transaction = fragmentManager.beginTransaction()

        transaction.add(R.id.frameLayout, restoreFragment)

        transaction.commitAllowingStateLoss()
    }
}
