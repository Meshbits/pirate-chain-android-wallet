package cash.z.ecc.android.di.component

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import cash.z.ecc.android.di.annotation.ActivityScope
import cash.z.ecc.android.di.module.RestoreActivityModule
import cash.z.ecc.android.ext.Const
import cash.z.ecc.android.ui.restore.RestoreActivity
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

@ActivityScope
@Subcomponent(modules = [RestoreActivityModule::class])
interface RestoreActivitySubcomponent {

    fun inject(activity: RestoreActivity)

    @Named(Const.Name.BEFORE_SYNCHRONIZER) fun viewModelFactoryRestore(): ViewModelProvider.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: FragmentActivity): RestoreActivitySubcomponent
    }
}
