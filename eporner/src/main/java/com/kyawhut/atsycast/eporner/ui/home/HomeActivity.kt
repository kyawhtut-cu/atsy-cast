package com.kyawhut.atsycast.eporner.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.Row
import androidx.leanback.widget.SectionRow
import com.kyawhut.atsycast.eporner.BuildConfig
import com.kyawhut.atsycast.eporner.R
import com.kyawhut.atsycast.eporner.databinding.ActivityEpornerHomeBinding
import com.kyawhut.atsycast.eporner.ui.search.SearchActivity
import com.kyawhut.atsycast.eporner.ui.video.VideoFragment
import com.kyawhut.atsycast.eporner.utils.Constants
import com.kyawhut.atsycast.share.base.BaseBrowseSupportFragment
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.replaceFragment
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@AndroidEntryPoint
internal class HomeActivity : BaseTvActivity<ActivityEpornerHomeBinding>() {

    override val layoutID: Int
        get() = R.layout.activity_eporner_home

    private val appName: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_APP_NAME) ?: ""
    }
    private val channelLogo: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) replaceFragment(
            R.id.content_frame, HomeFragment().putArg(
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_CHANNEL_LOGO to channelLogo,
            )
        )
    }

    @AndroidEntryPoint
    class HomeFragment : BaseBrowseSupportFragment() {

        private val appName: String
            get() = arguments?.getString(Constants.EXTRA_APP_NAME) ?: ""
        private val channelLogo: String
            get() = arguments?.getString(Constants.EXTRA_CHANNEL_LOGO) ?: ""

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            setRowItem(
                BuildConfig.EPORNER_KEY.split(",").mapIndexed { index, s ->
                    PageRow(HeaderItem(index.toLong(), s.capitalize(Locale.getDefault())).apply {
                        description = s
                    })
                }.toMutableList<Row>().apply {
                    add(0, SectionRow(appName))
                }.toList()
            )
        }

        override fun onClickRetry() {
        }

        override val onSearchClicked: () -> Unit = {
            startActivity<SearchActivity>(
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_CHANNEL_LOGO to channelLogo,
            )
        }

        override fun onCreateRowFragment(header: HeaderItem): Fragment {
            return VideoFragment.newInstance(
                appName,
                channelLogo,
                header.description.toString(),
            )
        }
    }
}
