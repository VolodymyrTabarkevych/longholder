package com.traday.longholder.presentation.active

import android.os.Parcelable
import com.traday.longholder.domain.model.Active
import kotlinx.android.parcel.Parcelize

@Parcelize
open class ActiveScreenMode : Parcelable {

    @Parcelize
    object Create : ActiveScreenMode(), Parcelable

    @Parcelize
    class Update(val active: Active) : ActiveScreenMode(), Parcelable

    @Parcelize
    class View(val active: Active) : ActiveScreenMode(), Parcelable
}



