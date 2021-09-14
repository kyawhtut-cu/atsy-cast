package com.kyawhut.atsycast.doujin.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.Row
import androidx.leanback.widget.SectionRow
import com.kyawhut.atsycast.doujin.R
import com.kyawhut.atsycast.doujin.databinding.ActivityDoujinHomeBinding
import com.kyawhut.atsycast.doujin.ui.video.VideoFragment
import com.kyawhut.atsycast.doujin.utils.Constants
import com.kyawhut.atsycast.share.base.BaseBrowseSupportFragment
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.replaceFragment
import com.kyawhut.atsycast.share.utils.extension.putArg
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@AndroidEntryPoint
internal class HomeActivity : BaseTvActivity<ActivityDoujinHomeBinding>() {

    override val layoutID: Int
        get() = R.layout.activity_doujin_home

    private val appName: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_APP_NAME) ?: "Doujin"
    }
    private val apiKey: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_API_KEY) ?: "com.hentaiser.app#617"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) replaceFragment(
            R.id.content_frame, HomeFragment().putArg(
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_PACKAGE_NAME to apiKey.split("#").first(),
                Constants.EXTRA_APP_VERSION to apiKey.split("#").last(),
            )
        )
    }

    @AndroidEntryPoint
    class HomeFragment : BaseBrowseSupportFragment() {

        private val appName: String
            get() = arguments?.getString(Constants.EXTRA_APP_NAME) ?: ""

        private val packageName: String
            get() = arguments?.getString(Constants.EXTRA_PACKAGE_NAME) ?: ""
        private val appVersion: String
            get() = arguments?.getString(Constants.EXTRA_APP_VERSION) ?: ""

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            setRowItem(
                resources.getStringArray(
                    R.array.drawerMenuTitle
                ).mapIndexed { index, s ->
                    PageRow(HeaderItem(index.toLong(), s).apply {
                        description = resources.getStringArray(R.array.drawerMenuKey)[index]
                    })
                }.toMutableList<Row>().apply {
                    add(0, SectionRow(appName))
                }.toList()
            )
        }

        override fun onClickRetry() {
        }

        override val onSearchClicked: () -> Unit = {
            /*startActivity<SearchActivity>(
                Constants.EXTRA_API_KEY to apiKey,
                Constants.EXTRA_APP_NAME to appName,
            )*/
        }

        override fun onCreateRowFragment(header: HeaderItem): Fragment {
            return VideoFragment.newInstance(
                appName,
                appVersion,
                packageName,
                header.description.toString(),
            )
        }
    }
}
