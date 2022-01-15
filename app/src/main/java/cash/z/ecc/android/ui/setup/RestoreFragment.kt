package cash.z.ecc.android.ui.setup

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.SystemClock
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import cash.z.ecc.android.R
import cash.z.ecc.android.databinding.FragmentRestoreBinding
import cash.z.ecc.android.di.viewmodel.activityRestoreViewModel
import cash.z.ecc.android.ext.Const
import cash.z.ecc.android.ext.Const.ARRRConstants.anArrayOfBirthdays
import cash.z.ecc.android.ext.showInvalidSeedPhraseError
import cash.z.ecc.android.ext.showSharedLibraryCriticalError
import cash.z.ecc.android.feedback.Report
import cash.z.ecc.android.feedback.Report.Funnel.Restore
import cash.z.ecc.android.feedback.Report.Tap.RESTORE_DONE
import cash.z.ecc.android.feedback.Report.Tap.RESTORE_SUCCESS
import cash.z.ecc.android.ui.base.BaseFragment
import cash.z.ecc.android.ui.restore.SeedViewModel
import com.khoiron.actionsheets.ActionSheet
import com.khoiron.actionsheets.callback.ActionSheetCallBack
import com.tylersuehr.chips.Chip
import kotlinx.coroutines.launch

class RestoreFragment : BaseFragment<FragmentRestoreBinding>() {
    override val screen = Report.Screen.RESTORE
    private var seedViewModel: SeedViewModel = SeedViewModel()

    private val walletSetup: WalletSetupViewModel by activityRestoreViewModel(false)

    override fun inflate(inflater: LayoutInflater): FragmentRestoreBinding =
        FragmentRestoreBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.proceed.setOnClickListener {
            onDone().also { tapped(RESTORE_DONE) }
        }

        binding.buttonSuccess.setOnClickListener {
            onEnterWallet().also { tapped(RESTORE_SUCCESS) }
        }
        binding.viewModel = seedViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.dropDownBirthday.setOnClickListener {
            showBirthdayMenu()
        }
    }

    private fun showBirthdayMenu() {

        restoreActivity?.hideKeyboard()
        context?.let {
            ActionSheet(it, anArrayOfBirthdays)
                .setTitle(getString(R.string.choose_a_birthday))
                .setColorTitleCancel(Color.parseColor("#FF4081"))
                .create(object : ActionSheetCallBack {
                    override fun data(data: String, position: Int) {
                        if (!data.equals(getString(R.string.cancel_text))) {
                            binding.inputBirthdate.setText(data)
                        }
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        // Require one less tap to enter the seed words
        touchScreenForUser()
    }

    private fun onExit() {
        mainActivity?.reportFunnel(Restore.Exit)
        hideAutoCompleteWords()
        restoreActivity?.hideKeyboard()
        mainActivity?.navController?.popBackStack()
    }

    private fun onEnterWallet() {
        mainActivity?.reportFunnel(Restore.Success)
        mainActivity?.safeNavigate(R.id.action_nav_restore_to_nav_home)
    }

    private fun onDone() {

        mainActivity?.reportFunnel(Restore.Done)
        restoreActivity?.hideKeyboard()
        val seedPhrase = binding.inputSeedphrase.text.toString()

        var birthday = binding.root.findViewById<TextView>(R.id.input_birthdate).text.toString()
            .let { birthdateString ->
                if (birthdateString.isNullOrEmpty()) {
                    Const.ARRRConstants.DEFAULT_BIRTHDAY_HEIGHT
                } else {
                    birthdateString.toInt()
                }
            }.coerceAtLeast(Const.ARRRConstants.DEFAULT_BIRTHDAY_HEIGHT)

        try {
            walletSetup.validatePhrase(seedPhrase)

            // TODO: Open Main Activity and initiate import wallet flow
            importWallet(seedPhrase, birthday)
        } catch (t: Throwable) {
            restoreActivity?.showInvalidSeedPhraseError(t)
        }
    }

    private fun importWallet(seedPhrase: String, birthday: Int) {
        mainActivity?.reportFunnel(Restore.ImportStarted)
        restoreActivity?.hideKeyboard()
        mainActivity?.apply {
            lifecycleScope.launch {
                try {
                    mainActivity?.startSync(walletSetup.importWallet(seedPhrase, birthday))
                    // bugfix: if the user proceeds before the synchronizer is created the app will crash!
                    binding.buttonSuccess.isEnabled = true
                    mainActivity?.reportFunnel(Restore.ImportCompleted)
                    playSound("sound_receive_small.mp3")
                    vibrateSuccess()
                } catch (e: UnsatisfiedLinkError) {
                    mainActivity?.showSharedLibraryCriticalError(e)
                }
            }
        }

        binding.groupDone.visibility = View.GONE
        binding.groupStart.visibility = View.GONE
        binding.groupSuccess.visibility = View.VISIBLE
        binding.buttonSuccess.isEnabled = false
    }

    // forcefully show the keyboard as a hack to fix odd behavior where the keyboard
    // sometimes closes randomly and inexplicably in between seed word entries
    private fun forceShowKeyboard() {
        requireView().postDelayed(
            {
                val focusedView = binding.inputSeedphrase
                restoreActivity!!.showKeyboard(focusedView)
                focusedView.requestFocus()
            },
            500L
        )
    }

    private fun hideAutoCompleteWords() {
        binding.inputSeedphrase?.setText("")
    }

    private fun touchScreenForUser() {
        binding.inputSeedphrase?.apply {
            postDelayed(
                {
                    binding.inputSeedphrase?.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    dispatchTouchEvent(motionEvent(ACTION_DOWN))
                    dispatchTouchEvent(motionEvent(ACTION_UP))
                },
                100L
            )
        }
    }

    private fun motionEvent(action: Int) = SystemClock.uptimeMillis().let { now ->
        MotionEvent.obtain(now, now, action, 0f, 0f, 0)
    }
}

class SeedWordChip(val word: String, var index: Int = -1) : Chip() {
    override fun getSubtitle(): String? = null // "subtitle for $word"
    override fun getAvatarDrawable(): Drawable? = null
    override fun getId() = index
    override fun getTitle() = word
    override fun getAvatarUri() = null
}
