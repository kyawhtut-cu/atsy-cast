package com.kyawhut.atsycast.gs_mm.ui.detail

import androidx.activity.viewModels
import com.kyawhut.atsycast.share.base.BaseDetailTvActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@AndroidEntryPoint
internal class DetailActivity : BaseDetailTvActivity<DetailFragment>() {

    private val vm: DetailViewModel by viewModels()

    override val fm: DetailFragment by lazy {
        DetailFragment()
    }
}
