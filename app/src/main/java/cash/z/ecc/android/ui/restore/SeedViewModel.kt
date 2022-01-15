package cash.z.ecc.android.ui.restore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeedViewModel : ViewModel() {
    // variable that will listen to user's input
    var _userInput = MutableLiveData<String>()
    // expose the variable to the owner(activity/fragment)
    val getUserInput: LiveData<String> = _userInput
}
