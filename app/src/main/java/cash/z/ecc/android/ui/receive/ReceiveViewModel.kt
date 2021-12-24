package cash.z.ecc.android.ui.receive

import androidx.lifecycle.ViewModel
import cash.z.ecc.android.sdk.Synchronizer
import cash.z.ecc.android.util.twig
import javax.inject.Inject

class ReceiveViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var synchronizer: Synchronizer

    suspend fun getAddress(): String = synchronizer.getAddress()
    suspend fun getTranparentAddress(): String = synchronizer.getTransparentAddress()

    override fun onCleared() {
        super.onCleared()
        twig("ReceiveViewModel cleared!")
    }
}
