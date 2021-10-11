package com.kyawhut.atsycast.msubpc.ui.detail

import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseDetailTvActivity
import com.kyawhut.atsycast.share.utils.extension.putArg
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/7/21
 */
@AndroidEntryPoint
internal class DetailActivity : BaseDetailTvActivity<DetailFragment>() {

    override val fm: DetailFragment by lazy {
        DetailFragment().putArg(
            Constants.EXTRA_API_KEY to intent?.getStringExtra(Constants.EXTRA_API_KEY),
            Constants.EXTRA_APP_NAME to intent?.getStringExtra(Constants.EXTRA_APP_NAME),
            Constants.EXTRA_CHANNEL_LOGO to intent?.getStringExtra(Constants.EXTRA_CHANNEL_LOGO),
            Constants.EXTRA_VIDEO_DATA to intent?.getSerializableExtra(Constants.EXTRA_VIDEO_DATA) as VideoResponse?
        )
    }
}
