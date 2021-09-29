package com.kyawhut.atsycast.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.kyawhut.astycast.gsmovie.GSApplication.goToGSHome
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.doujin.DoujinApp.goToDoujin
import com.kyawhut.atsycast.eporner.EPorner.goToEPorner
import com.kyawhut.atsycast.ets2mm.ET2SMMApp.goToETS2MM
import com.kyawhut.atsycast.free2air.Free2Air.goToFree2Air
import com.kyawhut.atsycast.msubpc.MsubPC.goToMsubPC
import com.kyawhut.atsycast.msys.MsysApp.goToMSYS
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import com.kyawhut.atsycast.twod.TwoDApp.goTo2D
import com.kyawhut.atsycast.ui.card.CardPresenter
import com.kyawhut.atsycast.zcm.ZCMApp.goToZCM
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@AndroidEntryPoint
class HomeFeaturesFragment : RowsSupportFragment() {

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
            if (item is HomeFeatureResponse.Data) {
                when (item.featureKey) {
                    -1 -> goToGSHome(item.featureAPIKey, item.featureName)
                    1 -> goToFree2Air(item.featureAPIKey, item.featureName)
                    2 -> goToMsubPC(item.featureName, item.featureAPIKey)
                    3 -> goToZCM(item.featureName, item.featureAPIKey)
                    4 -> goToMSYS(item.featureName, item.featureAPIKey)
                    6 -> goToETS2MM(item.featureName, item.featureAPIKey)
                    7 -> goToDoujin(item.featureName, item.featureAPIKey)
                    8 -> goToEPorner(item.featureName)
                    9 -> goTo2D(item.featureName)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshPage()
    }

    fun refreshPage() {
        vm.getHomeFeatures(requireContext(), ::onStateFeature)
    }

    private fun onStateFeature(state: NetworkResponse<List<HomeFeatureResponse.Data>>) {
        when {
            state.isLoading -> {
                (requireActivity() as HomeActivity).toggleLoading(true)
            }
            state.isSuccess -> {
                (requireActivity() as HomeActivity).toggleLoading(false)
                vm.homeFeatureList = state.data ?: listOf()
                if (vm.homeFeatureList.isNotEmpty()) {
                    rowsAdapter.clear()
                    rowsAdapter.add(
                        ListRow(
                            HeaderItem(1L, "Features"),
                            ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                                setItems(state.data?.filter {
                                    !it.featureRequiredPassword || isAdultOpen
                                }, HomeFeatureResponse.diff)
                            }
                        )
                    )
                }
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

    override fun onResume() {
        super.onResume()
        if (rowsAdapter.size() != 0 && vm.homeFeatureList.isNotEmpty()) {
            with((rowsAdapter.get(0) as ListRow).adapter as ArrayObjectAdapter) {
                vm.homeFeatureList.mapIndexed { index, featureResponse ->
                    if (featureResponse.featureRequiredPassword) index to featureResponse
                    else index to null
                }.filter { it.second != null }.forEach { (index, feature) ->
                    if (this.indexOf(feature) == -1 && isAdultOpen) {
                        add(index, feature)
                    } else if (this.indexOf(feature) != -1 && !isAdultOpen) {
                        removeItems(this.indexOf(feature), 1)
                    }
                }
            }
        }
    }
}
