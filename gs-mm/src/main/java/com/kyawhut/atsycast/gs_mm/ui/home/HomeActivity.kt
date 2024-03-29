package com.kyawhut.atsycast.gs_mm.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.SectionRow
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.network.response.DrawerResponse
import com.kyawhut.atsycast.gs_mm.databinding.ActivityGsmmHomeBinding
import com.kyawhut.atsycast.gs_mm.ui.cache.CacheFragment
import com.kyawhut.atsycast.gs_mm.ui.search.SearchActivity
import com.kyawhut.atsycast.gs_mm.ui.videogrid.VideoGridFragment
import com.kyawhut.atsycast.gs_mm.ui.videolist.VideoListFragment
import com.kyawhut.atsycast.gs_mm.utils.Constants
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
internal class HomeActivity : BaseTvActivity<ActivityGsmmHomeBinding>() {

    override val layoutID: Int
        get() = R.layout.activity_gsmm_home

    private val appName: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_APP_NAME) ?: ""
    }
    private val channelLogo: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }
    private val apiKey: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_API_KEY) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) replaceFragment(
            R.id.content_frame, HomeFragment().putArg(
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_CHANNEL_LOGO to channelLogo,
                Constants.EXTRA_API_KEY to apiKey,
            )
        )
    }

    @AndroidEntryPoint
    class HomeFragment : BaseBrowseSupportFragment() {

        private val watchLater: PageRow by lazy {
            PageRow(
                HeaderItem(
                    (vm.categoryList.size + 1).toLong(),
                    getString(R.string.lbl_watch_later)
                ).apply {
                    description = getString(R.string.lbl_watch_later_key)
                })
        }
        private val recentlyWatch: PageRow by lazy {
            PageRow(
                HeaderItem(
                    (vm.categoryList.size + 2).toLong(),
                    getString(R.string.lbl_recently_watch)
                ).apply {
                    description = getString(R.string.lbl_recently_watch_key)
                })
        }

        private val vm: HomeViewModel by viewModels()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            vm.getHome(::onCategoryResult)
        }

        private fun onCategoryResult(result: NetworkResponse<List<DrawerResponse.Data>>) {
            when (result.networkStatus) {
                is NetworkStatus.LOADING -> showLoading()
                is NetworkStatus.SUCCESS -> {
                    hideLoading()
                    vm.categoryList = result.data ?: listOf()
                    bindRows()
                }
                is NetworkStatus.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
            }
        }

        override fun onClickRetry() {
            vm.getHome(::onCategoryResult)
        }

        override val onSearchClicked: () -> Unit = {
            startActivity<SearchActivity>(
                Constants.EXTRA_API_KEY to vm.route,
                Constants.EXTRA_APP_NAME to vm.appName,
                Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
            )
        }

        private fun bindRows() {
            clearRows()
            setRowItem(
                vm.categoryList.mapIndexed { index, drawer ->
                    PageRow(
                        HeaderItem(index.toLong(), drawer.drawerTitle).apply {
                            description = "${drawer.drawerKey}~${drawer.drawerType}"
                        }
                    )
                }
            )
            addRowItem(0, SectionRow(vm.appName))
            checkCache()
        }

        override fun onCreateRowFragment(header: HeaderItem): Fragment {
            return if (header.description == getString(R.string.lbl_watch_later_key) || header.description == getString(
                    R.string.lbl_recently_watch_key
                )
            ) CacheFragment.newInstance(
                vm.route,
                header.description.toString(),
                vm.appName,
                vm.channelLogo,
            ) else {
                val data = header.description.toString().split("~")
                if (data.last() == "list") VideoListFragment.newInstance(
                    vm.appName,
                    vm.channelLogo,
                    data.first(),
                    vm.route,
                ) else VideoGridFragment.newInstance(
                    vm.appName,
                    vm.channelLogo,
                    data.first(),
                    vm.route,
                )
            }
        }

        private fun checkCache() {
            if (vm.categoryList.isEmpty()) return
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
