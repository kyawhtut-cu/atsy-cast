package com.kyawhut.atsycast.ets2mm.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.SectionRow
import com.kyawhut.atsycast.ets2mm.ET2SMMApp.clearAPIKey
import com.kyawhut.atsycast.ets2mm.R
import com.kyawhut.atsycast.ets2mm.data.network.response.GenresResponse
import com.kyawhut.atsycast.ets2mm.databinding.ActivityEts2mmHomeBinding
import com.kyawhut.atsycast.ets2mm.ui.cache.CacheFragment
import com.kyawhut.atsycast.ets2mm.ui.movies.MoviesFragment
import com.kyawhut.atsycast.ets2mm.ui.search.SearchActivity
import com.kyawhut.atsycast.ets2mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseBrowseSupportFragment
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.replaceFragment
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/3/21
 */
@AndroidEntryPoint
internal class HomeActivity : BaseTvActivity<ActivityEts2mmHomeBinding>() {

    override val layoutID: Int
        get() = R.layout.activity_ets2mm_home

    private val appName: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_APP_NAME) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) replaceFragment(
            R.id.content_frame, HomeFragment().putArg(
                Constants.EXTRA_APP_NAME to appName,
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
            PageRow(
                HeaderItem(
                    (vm.genresList.size + 1).toLong(),
                    getString(R.string.lbl_watch_later)
                ).apply {
                    description = getString(R.string.lbl_watch_later_key)
                })
        }
        private val recentlyWatch: PageRow by lazy {
            PageRow(
                HeaderItem(
                    (vm.genresList.size + 2).toLong(),
                    getString(R.string.lbl_recently_watch)
                ).apply {
                    description = getString(R.string.lbl_recently_watch_key)
                })
        }

        private val vm: HomeViewModel by viewModels()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            if (vm.genresList.isEmpty()) vm.getHome(::onGenresResult)
            else bindRows()
        }

        private fun onGenresResult(result: NetworkResponse<List<GenresResponse>>) {
            when (result.networkStatus) {
                is NetworkStatus.LOADING -> showLoading()
                is NetworkStatus.SUCCESS -> {
                    hideLoading()
                    vm.genresList = result.data ?: listOf()
                    bindRows()
                }
                is NetworkStatus.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
            }
        }

        private fun bindRows() {
            clearRows()
            setRowItem(
                vm.genresList.mapIndexed { index, genresResponse ->
                    PageRow(
                        HeaderItem(index.toLong(), genresResponse.genreName).apply {
                            description = genresResponse.genreID
                        }
                    )
                }
            )
            addRowItem(0, SectionRow(vm.appName))
            checkCache()
        }

        override fun onClickRetry() {
            vm.getHome(::onGenresResult)
        }

        override val onSearchClicked: () -> Unit = {
            startActivity<SearchActivity>(
                Constants.EXTRA_APP_NAME to vm.appName,
            )
        }

        override fun onCreateRowFragment(header: HeaderItem): Fragment {
            return if (header.description == getString(R.string.lbl_watch_later_key) || header.description == getString(
                    R.string.lbl_recently_watch_key
                )
            ) CacheFragment.newInstance(
                header.description.toString(),
                vm.appName,
            ) else MoviesFragment.newInstance(
                header.description.toString(),
                vm.appName,
            )
        }

        private fun checkCache() {
            if (vm.genresList.isEmpty()) return
            val isHasWatchLater = vm.isHasWatchLater
            val isHasRecently = vm.isHasRecently
            with(getRow(3)) {
                if (this is PageRow) {
                    if (this.headerItem.id != watchLater.headerItem.id && isHasWatchLater) {
                        addRowItem(3, watchLater)
                    } else if (this.headerItem.id == watchLater.headerItem.id && !isHasWatchLater) {
                        removeRow(3)
                    }
                }
            }

            val index = if (isHasWatchLater) 4 else 3
            with(getRow(index)) {
                if (this is PageRow) {
                    if (this.headerItem.id != recentlyWatch.headerItem.id && isHasRecently) {
                        addRowItem(index, recentlyWatch)
                    } else if (this.headerItem.id == recentlyWatch.headerItem.id && !isHasRecently) {
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
