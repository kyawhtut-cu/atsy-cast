package com.kyawhut.atsycast.zcm.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.SectionRow
import com.kyawhut.atsycast.share.base.BaseBrowseSupportFragment
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.replaceFragment
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.zcm.R
import com.kyawhut.atsycast.zcm.data.network.response.GenresResponse
import com.kyawhut.atsycast.zcm.databinding.ActivityZcmHomeBinding
import com.kyawhut.atsycast.zcm.ui.cache.CacheFragment
import com.kyawhut.atsycast.zcm.ui.genres.MoviesFragment
import com.kyawhut.atsycast.zcm.ui.search.SearchActivity
import com.kyawhut.atsycast.zcm.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/3/21
 */
@AndroidEntryPoint
internal class HomeActivity : BaseTvActivity<ActivityZcmHomeBinding>() {

    override val layoutID: Int
        get() = R.layout.activity_zcm_home

    private val appName: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_APP_NAME) ?: ""
    }
    private val apiKey: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_API_KEY) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) replaceFragment(
            R.id.content_frame, HomeFragment().putArg(
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_API_KEY to apiKey,
            )
        )
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
                        HeaderItem(index.toLong(), genresResponse.genresTitle).apply {
                            description = "${genresResponse.genresID}"
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
                Constants.EXTRA_API_KEY to vm.apiKey,
                Constants.EXTRA_APP_NAME to vm.appName,
            )
        }

        override fun onCreateRowFragment(header: HeaderItem): Fragment {
            return if (header.description == getString(R.string.lbl_watch_later_key) || header.description == getString(
                    R.string.lbl_recently_watch_key
                )
            ) CacheFragment.newInstance(
                vm.apiKey,
                header.description.toString(),
                vm.appName,
            ) else MoviesFragment.newInstance(
                vm.apiKey,
                header.description.toString().toInt(),
                vm.appName,
            )
        }

        private fun checkCache() {
            if (vm.genresList.isEmpty()) return
            val isHasWatchLater = vm.isHasWatchLater
            val isHasRecently = vm.isHasRecently
            with(getRow(2)) {
                if (this is PageRow) {
                    if (this.headerItem.id != watchLater.headerItem.id && isHasWatchLater) {
                        addRowItem(2, watchLater)
                    } else if (this.headerItem.id == watchLater.headerItem.id && !isHasWatchLater) {
                        removeRow(2)
                    }
                }
            }

            val index = if (isHasWatchLater) 3 else 2
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
