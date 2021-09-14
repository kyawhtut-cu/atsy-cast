package com.kyawhut.atsycast.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.free2air.Free2Air.goToFree2Air
import com.kyawhut.atsycast.msubpc.MsubPC.goToMsubPC
import com.kyawhut.atsycast.msys.MsysApp.goToMSYS
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
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
        ArrayObjectAdapter(ListRowPresenter(FocusHighlight.ZOOM_FACTOR_SMALL))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = rowsAdapter

        setOnItemViewSelectedListener { _, item, _, _ ->
        }

        setOnItemViewClickedListener { _, item, _, _ ->
            if (item is HomeFeatureResponse) {
                when (item.featureKey) {
                    1 -> goToFree2Air(item.featureAPIKey, item.featureName)
                    2 -> goToMsubPC(item.featureName, item.featureAPIKey)
                    3 -> goToZCM(item.featureName, item.featureAPIKey)
                    4 -> goToMSYS(item.featureName, item.featureAPIKey)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshPage()
    }

    fun refreshPage() {
        vm.getHomeFeatures(::onStateFeature)
    }

    private fun onStateFeature(state: NetworkResponse<List<HomeFeatureResponse>>) {
        when {
            state.isLoading -> {
                (requireActivity() as HomeActivity).toggleLoading(true)
            }
            state.isSuccess -> {
                (requireActivity() as HomeActivity).toggleLoading(false)
                rowsAdapter.clear()
                rowsAdapter.add(
                    ListRow(
                        HeaderItem(1L, "Features"),
                        ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                            setItems(state.data, HomeFeatureResponse.diff)
                        }
                    )
                )
            }
            state.isError -> {
                (requireActivity() as HomeActivity).showError(
                    if (state.error?.message.isNullOrEmpty()) getString(
                        state.error?.resId ?: R.string.app_name
                    )
                    else state.error?.message ?: "Internet connection error."
                )
            }
        }
    }
}
