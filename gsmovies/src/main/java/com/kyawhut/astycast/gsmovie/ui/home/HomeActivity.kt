package com.kyawhut.astycast.gsmovie.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.SectionRow
import com.kyawhut.astycast.gsmovie.R
import com.kyawhut.astycast.gsmovie.data.network.response.CategoryResponse
import com.kyawhut.astycast.gsmovie.databinding.ActivityGsHomeBinding
import com.kyawhut.astycast.gsmovie.ui.video.VideoFragment
import com.kyawhut.astycast.gsmovie.utils.Constants
import com.kyawhut.atsycast.share.base.BaseBrowseSupportFragment
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.replaceFragment
import com.kyawhut.atsycast.share.utils.extension.putArg
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/3/21
 */
@AndroidEntryPoint
internal class HomeActivity : BaseTvActivity<ActivityGsHomeBinding>() {

    override val layoutID: Int
        get() = R.layout.activity_gs_home

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

        private val vm: HomeViewModel by viewModels()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            vm.getHome(::onCategoryResult)
        }

        private fun onCategoryResult(result: NetworkResponse<List<CategoryResponse.Data>>) {
            when (result.networkStatus) {
                is NetworkStatus.LOADING -> showLoading()
                is NetworkStatus.SUCCESS -> {
                    hideLoading()
                    clearRows()
                    setRowItem(
                        (result.data ?: listOf()).mapIndexed { index, genresResponse ->
                            PageRow(
                                HeaderItem(index.toLong(), genresResponse.categoryTitle).apply {
                                    description = "${genresResponse.categoryID}"
                                }
                            )
                        }
                    )
                    addRowItem(0, SectionRow(vm.appName))
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
            /*startActivity<SearchActivity>(
                Constants.EXTRA_API_KEY to vm.apiKey,
                Constants.EXTRA_APP_NAME to vm.appName,
            )*/
        }

        override fun onCreateRowFragment(header: HeaderItem): Fragment {
            return VideoFragment.newInstance(
                vm.route,
                header.description.toString().toInt(),
                vm.appName
            )
        }
    }
}
