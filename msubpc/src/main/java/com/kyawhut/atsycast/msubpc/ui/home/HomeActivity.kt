package com.kyawhut.atsycast.msubpc.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.leanback.widget.*
import com.kyawhut.atsycast.msubpc.MsubPC.clearAPIKey
import com.kyawhut.atsycast.msubpc.R
import com.kyawhut.atsycast.msubpc.databinding.ActivityMsubHomeBinding
import com.kyawhut.atsycast.msubpc.ui.cache.CacheFragment
import com.kyawhut.atsycast.msubpc.ui.football.FootballFragment
import com.kyawhut.atsycast.msubpc.ui.movies.MoviesFragment
import com.kyawhut.atsycast.msubpc.ui.search.SearchActivity
import com.kyawhut.atsycast.msubpc.ui.series.SeriesFragment
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseBrowseSupportFragment
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.replaceFragment
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/3/21
 */
@AndroidEntryPoint
internal class HomeActivity : BaseTvActivity<ActivityMsubHomeBinding>() {

    override val layoutID: Int
        get() = R.layout.activity_msub_home

    private val appName: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_APP_NAME) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) replaceFragment(
            R.id.content_frame, HomeFragment().putArg(
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_CHANNEL_LOGO to intent?.getStringExtra(Constants.EXTRA_CHANNEL_LOGO)
            )
        )
    }

    override fun onBackPressed() {
        clearAPIKey()
        super.onBackPressed()
    }

    @AndroidEntryPoint
    class HomeFragment : BaseBrowseSupportFragment() {

        private val watchLater: PageRow by lazy {
            PageRow(HeaderItem(17L, getString(R.string.lbl_watch_later)).apply {
                description = getString(R.string.lbl_watch_later_key)
            })
        }
        private val recentlyWatch: PageRow by lazy {
            PageRow(HeaderItem(18L, getString(R.string.lbl_recently_watch)).apply {
                description = getString(R.string.lbl_recently_watch_key)
            })
        }

        private val appName: String
            get() = arguments?.getString(Constants.EXTRA_APP_NAME) ?: ""
        private val channelLogo: String
            get() = arguments?.getString(Constants.EXTRA_CHANNEL_LOGO) ?: ""

        @Inject
        lateinit var homeRepository: HomeRepository

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val moviesMenu = resources.getStringArray(
                R.array.array_movies_filter_title
            ).mapIndexed { index, s ->
                PageRow(HeaderItem(index.toLong(), s).apply {
                    description =
                        resources.getStringArray(R.array.array_movies_filter_key)[index]
                })
            }.filter {
                !it.headerItem.name.isAdult || isAdultOpen
            }.toMutableList<Row>().apply {
                add(0, SectionRow("$appName Movies"))
            }.toList()

            val seriesMenu = resources.getStringArray(
                R.array.array_series_filter_title
            ).mapIndexed { index, s ->
                PageRow(HeaderItem(index.toLong(), s).apply {
                    description =
                        resources.getStringArray(R.array.array_series_filter_key)[index]
                })
            }.filter {
                !it.headerItem.name.isAdult || isAdultOpen
            }.toMutableList<Row>().apply {
                add(0, DividerRow())
                add(1, SectionRow("$appName Series"))
            }.toList()

            setRowItem(moviesMenu + seriesMenu)
        }

        override fun onClickRetry() {
        }

        override val onSearchClicked: () -> Unit = {
            startActivity<SearchActivity>(
                Constants.EXTRA_APP_NAME to appName,
            )
        }

        override fun onCreateRowFragment(header: HeaderItem): Fragment {
            return if (header.description == getString(R.string.lbl_watch_later_key) || header.description == getString(
                    R.string.lbl_recently_watch_key
                )
            ) CacheFragment.newInstance(
                header.description.toString(),
                appName,
                channelLogo,
            ) else if (header.description == getString(R.string.lbl_football_key)) FootballFragment.newInstance(
                header.description.toString(),
                appName,
                channelLogo,
            ) else if (header.name.endsWith("Series")) SeriesFragment.newInstance(
                header.description.toString(),
                appName,
                channelLogo,
            ) else MoviesFragment.newInstance(
                header.description.toString(),
                appName,
                channelLogo,
            )
        }

        private fun checkCache() {
            val isHasWatchLater = homeRepository.isHasWatchLater
            val isHasRecently = homeRepository.isHasRecently
            with(getRow(2)) {
                if (this is PageRow) {
                    if (this.headerItem.id != 17L && isHasWatchLater) {
                        addRowItem(2, watchLater)
                    } else if (this.headerItem.id == 17L && !isHasWatchLater) {
                        removeRow(2)
                    }
                }
            }

            val index = if (isHasWatchLater) 3 else 2
            with(getRow(index)) {
                if (this is PageRow) {
                    if (this.headerItem.id != 18L && isHasRecently) {
                        addRowItem(index, recentlyWatch)
                    } else if (this.headerItem.id == 18L && !isHasRecently) {
                        removeRow(index)
                    }
                }
            }
        }

        override fun onResume() {
            checkCache()
            super.onResume()
        }
    }
}
