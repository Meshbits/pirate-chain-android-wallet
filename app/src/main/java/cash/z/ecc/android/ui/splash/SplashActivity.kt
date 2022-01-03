package cash.z.ecc.android.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import cash.z.ecc.android.R
import cash.z.ecc.android.ext.Const
import cash.z.ecc.android.preference.SharedPreferenceFactory
import cash.z.ecc.android.ui.MainActivity
import cash.z.ecc.android.ui.newwallet.LoginActivity

class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            object : Runnable {
                override fun run() {
                    openRespectiveScreen()
                }
            },
            1000
        )
        addBottomAndTopNavBarColors()
    }

    fun openRespectiveScreen() {

//        val prefs = SharedPreferenceFactory.getSharedPreferences(
//            this
//        )
//
//        if (prefs.getBoolean(Const.Pref.FIRST_USE_VIEW_TX, false)) {
//            openLoginActivity()
//        } else {
            openMainActivity()
//        }
    }

    fun openLoginActivity() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    fun openMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
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
}
