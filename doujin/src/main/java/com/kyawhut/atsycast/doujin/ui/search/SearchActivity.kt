package com.kyawhut.atsycast.doujin.ui.search

import com.kyawhut.atsycast.doujin.utils.Constants
import com.kyawhut.atsycast.share.base.BaseSearchTvActivity
import com.kyawhut.atsycast.share.utils.extension.putArg
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/7/21
 */
@AndroidEntryPoint
internal class SearchActivity : BaseSearchTvActivity<SearchFragment>() {

    override val searchFragment: SearchFragment by lazy {
        SearchFragment().putArg(
            Constants.EXTRA_APP_NAME to intent?.getStringExtra(Constants.EXTRA_APP_NAME),
            Constants.EXTRA_CHANNEL_LOGO to intent?.getStringExtra(Constants.EXTRA_CHANNEL_LOGO),
        )
    }
}
