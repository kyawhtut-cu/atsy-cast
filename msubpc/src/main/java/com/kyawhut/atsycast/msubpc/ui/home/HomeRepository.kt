package com.kyawhut.atsycast.msubpc.ui.home

import androidx.lifecycle.LiveData

/**
 * @author kyawhtut
 * @date 9/9/21
 */
interface HomeRepository {

    val isHasRecently: Boolean

    val isHasWatchLater: Boolean
}
