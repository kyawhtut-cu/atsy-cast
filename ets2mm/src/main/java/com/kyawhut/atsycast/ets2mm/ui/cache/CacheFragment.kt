package com.kyawhut.atsycast.ets2mm.ui.cache

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.ets2mm.R
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.ets2mm.ui.card.CardPresenter
import com.kyawhut.atsycast.ets2mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseGridSupportFragment
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.utils.extension.putArg
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/9/21
 */
@AndroidEntryPoint
internal class CacheFragment : BaseGridSupportFragment<CacheViewModel>() {

    companion object {
        fun newInstance(key: String, appName: String): CacheFragment {
            return CacheFragment().putArg(
                Constants.EXTRA_PAGE_KEY to key,
                Constants.EXTRA_APP_NAME to appName,
            )
        }
    }

    override val vm: CacheViewModel by viewModels()
    override val numberOfColumns: Int
        get() = if (vm.cacheKeyType == getString(R.string.lbl_recently_watch_key)) 4 else super.numberOfColumns

    override val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(CardPresenter(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (vm.cacheKeyType == "recentlyWatch") {
            vm.recentlyWatch.observe(viewLifecycleOwner) {
                rowsAdapter.clear()
                rowsAdapter.setItems(it, RecentlyWatchEntity.diff)
            }
        } else if (vm.cacheKeyType == "watchLater") {
            vm.watchLater.observe(viewLifecycleOwner) {
                rowsAdapter.clear()
                rowsAdapter.setItems(it, VideoResponse.diff)
            }
        }
    }

    override fun onItemClicked(it: Any) {
        if (it is VideoResponse) {
            /* startActivity<DetailActivity>(
                 Constants.EXTRA_API_KEY to vm.apiKey,
                 Constants.EXTRA_VIDEO_DATA to it,
                 Constants.EXTRA_APP_NAME to vm.appName,
             )*/
        } else if (it is RecentlyWatchEntity) {
            /*startActivity<PlayerActivity>(
                Constants.EXTRA_VIDEO_ID to it.videoID.toInt(),
                Constants.EXTRA_IS_RESUME to true,
                Constants.EXTRA_VIDEO_TITLE to it.videoTitle,
                Constants.EXTRA_VIDEO_COVER to it.videoCover,
                Constants.EXTRA_APP_NAME to vm.appName,
                Constants.EXTRA_VIDEO_SOURCE to VideoSourceModel(
                    it.videoID.toInt(),
                    it.videoTitle,
                    null,
                    it.videoURL,
                    it.videoAgent,
                    it.videoCookies,
                    it.videoCustomHeader
                ),
                Constants.EXTRA_RELATED_EPISODE to Gson().fromJson<List<EpisodeResponse>>(
                    it.videoRelatedVideo,
                    object : TypeToken<List<EpisodeResponse>>() {}.type
                )
            )*/
        }
    }

    override fun onItemFocus(it: Any) {
        if (it is VideoResponse) {
            changeBackground(it.videoCover)
        } else if (it is RecentlyWatchEntity) {
            changeBackground(it.videoCover)
        }
    }

    override fun onClickRetry() {
    }
}
