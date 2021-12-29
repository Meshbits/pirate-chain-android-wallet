package cash.z.ecc.android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import cash.z.ecc.android.R
import cash.z.ecc.android.ui.MainActivity

class SplashActivity : AppCompatActivity(R.layout.splash_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed(
            object : Runnable {
                override fun run() {
                    openMainActivity()
                }
            },
            1000
        )
    }

    fun openMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
    }
}
