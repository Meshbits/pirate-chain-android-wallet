package cash.z.ecc.android.ui.newwallet

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import cash.z.ecc.android.R

class LoginActivity : AppCompatActivity(R.layout.login_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBottomAndTopNavBarColors()
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
