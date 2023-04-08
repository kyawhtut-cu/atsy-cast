package com.kyawhut.atsycast.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import com.kyawhut.astycast.gsmovie.GSApplication.goToGSDetail
import com.kyawhut.astycast.gsmovie.GSApplication.goToGSHome
import com.kyawhut.astycast.gsmovie.GSApplication.goToGSPlayer
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.doujin.DoujinApp.goToDoujin
import com.kyawhut.atsycast.eporner.EPorner.goToEPorner
import com.kyawhut.atsycast.ets2mm.ET2SMMApp.goToETS2MM
import com.kyawhut.atsycast.ets2mm.ET2SMMApp.goToETS2MMDetail
import com.kyawhut.atsycast.ets2mm.ET2SMMApp.goToETS2MMPlayer
import com.kyawhut.atsycast.free2air.Free2Air.goToFree2Air
import com.kyawhut.atsycast.gs_mm.GSMMApp.goToGSMMDetail
import com.kyawhut.atsycast.gs_mm.GSMMApp.goToGSMMHome
import com.kyawhut.atsycast.gs_mm.GSMMApp.goToGSMMPlayer
import com.kyawhut.atsycast.msubpc.MsubPC.goToMSubPCDetail
import com.kyawhut.atsycast.msubpc.MsubPC.goToMSubPCPlayer
import com.kyawhut.atsycast.msubpc.MsubPC.goToMsubPC
import com.kyawhut.atsycast.msys.MsysApp.goMSYSPlayer
import com.kyawhut.atsycast.msys.MsysApp.goToMSYS
import com.kyawhut.atsycast.msys.MsysApp.goToMSYSDetail
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.utils.ShareUtils
import com.kyawhut.atsycast.share.utils.SourceType
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import com.kyawhut.atsycast.tiktok.TiktokApp.goToTiktok
import com.kyawhut.atsycast.twod.TwoDApp.goTo2D
import com.kyawhut.atsycast.ui.card.CardPresenter
import com.kyawhut.atsycast.zcm.ZCMApp.goToZCM
import com.kyawhut.atsycast.zcm.ZCMApp.goToZCMDetail
import com.kyawhut.atsycast.zcm.ZCMApp.goToZCMPlayer
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@AndroidEntryPoint
class HomeFeaturesFragment : RowsSupportFragment() {

    companion object {
        private const val WATCH_LATER_ID = 1001L
        private const val RECENTLY_WATCHED_ID = 1002L
    }

    private val vm: HomeViewModel by viewModels()

    private val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = rowsAdapter

        setOnItemViewSelectedListener { _, item, _, _ ->
        }

        setOnItemViewClickedListener { _, item, _, _ ->
            when (item) {
                is HomeFeatureResponse.Data -> {
                    when (item.featureKey) {
                        -1 -> goToGSHome(item.featureAPIKey, item.featureName, item.featureCover)
                        1 -> goToFree2Air(item.featureAPIKey, item.featureName, item.featureCover)
                        2 -> goToMsubPC(item.featureName, item.featureCover, item.featureAPIKey)
                        3 -> goToZCM(item.featureName, item.featureCover, item.featureAPIKey)
                        4 -> goToMSYS(item.featureName, item.featureCover, item.featureAPIKey)
                        6 -> goToETS2MM(item.featureName, item.featureCover, item.featureAPIKey)
                        7 -> goToDoujin(item.featureName, item.featureCover, item.featureAPIKey)
                        8 -> goToEPorner(item.featureName, item.featureCover)
                        9 -> goTo2D(item.featureName)
                        10 -> goToTiktok(item.featureAPIKey)
                        11 -> goToGSMMHome(item.featureName, item.featureCover, item.featureAPIKey)
                    }
                }

                is WatchLaterEntity -> {
                    vm.getFeatureDetail(item, ::onFeatureDetailState)
                }

                is RecentlyWatchEntity -> {
                    vm.getFeatureDetail(item, ::onFeatureDetailStateForPlay)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rowsAdapter.clear()

        vm.cacheDB.observe(viewLifecycleOwner) { (watchLater, recentlyWatch) ->

            Timber.d("viewLifecycleOwner => %s %s", watchLater.size, recentlyWatch.size)

            var foundIndex: Int = -1
            (0 until rowsAdapter.size()).forEach { index ->
                with(rowsAdapter.get(index) as ListRow) {
                    if (this.headerItem.id == WATCH_LATER_ID) {
                        foundIndex = index
                        return@forEach
                    }
                }
            }
            if (watchLater.isNotEmpty() && foundIndex == -1) rowsAdapter.add(
                ListRow(
                    HeaderItem(WATCH_LATER_ID, "WatchLater"),
                    ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                        setItems(watchLater, WatchLaterEntity.diff)
                    }
                )
            ) else if (watchLater.isNotEmpty() && foundIndex > -1) {
                with((rowsAdapter.get(foundIndex) as ListRow).adapter as ArrayObjectAdapter) {
                    this.clear()
                    this.addAll(this.size(), watchLater)
                }
            } else if (watchLater.isEmpty() && foundIndex != -1) {
                rowsAdapter.removeItems(foundIndex, 1)
            }

            foundIndex = -1
            (0 until rowsAdapter.size()).forEach { index ->
                with(rowsAdapter.get(index) as ListRow) {
                    if (this.headerItem.id == RECENTLY_WATCHED_ID) {
                        foundIndex = index
                        return@forEach
                    }
                }
            }

            if (recentlyWatch.isNotEmpty() && foundIndex == -1) rowsAdapter.add(
                ListRow(
                    HeaderItem(RECENTLY_WATCHED_ID, "Recently Watch"),
                    ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                        setItems(recentlyWatch, RecentlyWatchEntity.diff)
                    }
                )
            ) else if (recentlyWatch.isNotEmpty() && foundIndex > -1) {
                with((rowsAdapter.get(foundIndex) as ListRow).adapter as ArrayObjectAdapter) {
                    this.clear()
                    this.addAll(this.size(), recentlyWatch)
                }
            } else if (recentlyWatch.isEmpty() && foundIndex != -1) {
                rowsAdapter.removeItems(foundIndex, 1)
            }
        }

        refreshPage()
    }

    fun refreshPage() {
        vm.getHomeFeatures(requireContext(), ::onStateFeature)
    }

    private fun onStateFeature(state: NetworkResponse<HashMap<String, List<HomeFeatureResponse.Data>>>) {
        when {
            state.isLoading -> {
                (requireActivity() as HomeActivity).toggleLoading(true)
            }

            state.isSuccess -> {
                (requireActivity() as HomeActivity).toggleLoading(false)
                vm.homeFeatureList = state.data ?: hashMapOf()
                vm.homeFeatureList.toSortedMap()
                var rowIndex = 1L
                vm.homeFeatureList.filter {
                    it.value.isNotEmpty() && it.value.any {
                        !it.featureRequiredPassword || isAdultOpen
                    }
                }.forEach { data ->
                    rowsAdapter.add(
                        rowIndex.toInt() - 1, ListRow(
                            HeaderItem(rowIndex++, data.key),
                            ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                                setItems(data.value.sortedBy { it.featureOrder }.filter {
                                    !it.featureRequiredPassword || isAdultOpen
                                }, HomeFeatureResponse.diff)
                            }
                        )
                    )
                }
                Handler().postDelayed({
                    setSelectedPosition(0, true)
                    verticalGridView.requestFocus()
                }, 500)
            }

            state.isError -> {
                (requireActivity() as HomeActivity).showError(
                    if (state.error?.message.isNullOrEmpty()) getString(
                        state.error?.resId ?: R.string.app_name
                    )
                    else state.error?.message ?: getString(R.string.lbl_no_internet_connection)
                )
            }
        }
    }

    private fun onFeatureDetailState(state: NetworkResponse<Pair<WatchLaterEntity, HomeFeatureResponse.Data?>>) {
        when {
            state.isLoading -> (requireActivity() as HomeActivity).toggleLoading(true)
            state.isSuccess -> {
                (requireActivity() as HomeActivity).toggleLoading(false)
                val feature = state.data?.second
                if (feature != null) {
                    when (val source = state.data?.first?.videoSourceType) {
                        is SourceType.MSUB_PC -> {
                            goToMSubPCDetail(
                                feature.featureName,
                                feature.featureCover,
                                feature.featureAPIKey,
                                state.data?.first!!
                            )
                        }

                        is SourceType.ZCM -> {
                            goToZCMDetail(
                                feature.featureName,
                                feature.featureCover,
                                feature.featureAPIKey,
                                state.data?.first!!
                            )
                        }

                        is SourceType.MSYS -> {
                            goToMSYSDetail(
                                feature.featureName,
                                feature.featureCover,
                                feature.featureAPIKey,
                                state.data?.first!!
                            )
                        }

                        is SourceType.ET2SMM -> {
                            goToETS2MMDetail(
                                feature.featureName,
                                feature.featureCover,
                                feature.featureAPIKey,
                                state.data?.first!!
                            )
                        }

                        else -> {
                            when (source?.type ?: "") {
                                ShareUtils.MYCINEMA -> goToGSDetail(
                                    feature.featureName,
                                    feature.featureCover,
                                    feature.featureAPIKey,
                                    state.data?.first!!
                                )

                                ShareUtils.VIU -> goToGSMMDetail(
                                    feature.featureName,
                                    feature.featureCover,
                                    feature.featureAPIKey,
                                    state.data?.first!!
                                )
                            }
                        }
                    }
                }
            }

            state.isError -> {
                (requireActivity() as HomeActivity).toggleLoading(false)
            }
        }
    }

    private fun onFeatureDetailStateForPlay(state: NetworkResponse<Pair<RecentlyWatchEntity, HomeFeatureResponse.Data?>>) {
        when {
            state.isLoading -> (requireActivity() as HomeActivity).toggleLoading(true)
            state.isSuccess -> {
                (requireActivity() as HomeActivity).toggleLoading(false)
                val feature = state.data?.second
                if (feature != null) {
                    when (val source = state.data?.first?.videoSourceType) {
                        is SourceType.MSUB_PC -> {
                            goToMSubPCPlayer(
                                feature.featureName,
                                feature.featureCover,
                                feature.featureAPIKey,
                                state.data?.first!!
                            )
                        }

                        is SourceType.ZCM -> {
                            goToZCMPlayer(
                                feature.featureName,
                                feature.featureCover,
                                feature.featureAPIKey,
                                state.data?.first!!
                            )
                        }

                        is SourceType.MSYS -> {
                            goMSYSPlayer(
                                feature.featureName,
                                feature.featureCover,
                                feature.featureAPIKey,
                                state.data?.first!!
                            )
                        }

                        is SourceType.ET2SMM -> {
                            goToETS2MMPlayer(
                                feature.featureName,
                                feature.featureCover,
                                feature.featureAPIKey,
                                state.data?.first!!
                            )
                        }

                        else -> {
                            when (source?.type ?: "") {
                                ShareUtils.MYCINEMA -> goToGSPlayer(
                                    feature.featureName,
                                    feature.featureCover,
                                    feature.featureAPIKey,
                                    state.data?.first!!
                                )

                                ShareUtils.VIU -> goToGSMMPlayer(
                                    feature.featureName,
                                    feature.featureCover,
                                    feature.featureAPIKey,
                                    state.data?.first!!
                                )
                            }
                        }
                    }
                }
            }

            state.isError -> {
                (requireActivity() as HomeActivity).toggleLoading(false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (rowsAdapter.size() != 0 && vm.homeFeatureList.isNotEmpty()) {
            var rowIndex = 0
            vm.homeFeatureList.forEach { data ->
                if (rowIndex < rowsAdapter.size()) {
                    val listRow = rowsAdapter.get(rowIndex++) as ListRow
                    val headerTitle = listRow.headerItem.name
                    with(listRow.adapter as ArrayObjectAdapter) {
                        vm.homeFeatureList[headerTitle]?.mapIndexed { index, featureResponse ->
                            if (featureResponse.featureRequiredPassword) index to featureResponse
                            else index to null
                        }?.filter { it.second != null }?.forEach { (index, feature) ->
                            if (this.indexOf(feature) == -1 && isAdultOpen) {
                                add(index, feature)
                            } else if (this.indexOf(feature) != -1 && !isAdultOpen) {
                                removeItems(this.indexOf(feature), 1)
                            }
                        }
                    }
                } else {
                    if (data.value.isNotEmpty()) rowsAdapter.add(
                        rowIndex,
                        ListRow(
                            HeaderItem((rowIndex + 1).toLong(), data.key),
                            ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                                setItems(data.value.sortedBy { it.featureOrder }.filter {
                                    !it.featureRequiredPassword || isAdultOpen
                                }, HomeFeatureResponse.diff)
                            }
                        )
                    )
                }
            }
            (0 until rowsAdapter.size()).forEach { index ->
                val listRow = rowsAdapter.get(index) as ListRow
                with(listRow.adapter as ArrayObjectAdapter) {
                    if (this.size() == 0) rowsAdapter.remove(listRow)
                }
            }
        }
    }
}
