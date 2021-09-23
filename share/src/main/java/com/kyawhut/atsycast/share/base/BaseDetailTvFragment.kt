package com.kyawhut.atsycast.share.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.adapter.GenresAdapter
import com.kyawhut.atsycast.share.databinding.AtsyDetailBinding
import com.kyawhut.atsycast.share.model.ActionModel
import com.kyawhut.atsycast.share.ui.card.DetailCard
import com.kyawhut.atsycast.share.ui.card.view.ActionCardView
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 9/7/21
 */
abstract class BaseDetailTvFragment<VM : BaseViewModel> : Fragment() {

    abstract val vm: VM
    protected lateinit var vb: AtsyDetailBinding

    open val relatedHighlightFocus: Int = FocusHighlight.ZOOM_FACTOR_LARGE
    open val relatedUseDimmer: Boolean = true

    private val listRowPresenter by lazy {
        ListRowPresenter(relatedHighlightFocus, relatedUseDimmer)
    }
    private val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(listRowPresenter)
    }
    private var rowsSupportFragment: RowsSupportFragment? = null

    open val actionHighlightFocus: Int = FocusHighlight.ZOOM_FACTOR_XSMALL
    open val actionUseDimmer: Boolean = true

    private val actionListRowPresenter by lazy {
        ListRowPresenter(actionHighlightFocus, actionUseDimmer)
    }
    private val actionRowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(actionListRowPresenter.apply {
            shadowEnabled = false
        })
    }
    private var actionRowsSupportFragment: RowsSupportFragment? = null

    private var focusRowCount: Int = -1
    var numberOfRowCount: Int = 0
        private set

    abstract fun onClickedAction(item: Any)

    abstract fun onClickedItem(rowIndex: Int, item: Any)

    abstract fun onItemFocus(item: Any)

    open val appName: String = ""

    private val genresAdapter: GenresAdapter by lazy {
        GenresAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = DataBindingUtil.inflate(inflater, R.layout.atsy_detail, container, false)
        return vb.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.apply {
            genresAdapter = this@BaseDetailTvFragment.genresAdapter
            genresLayoutManager = LinearLayoutManager(
                this@BaseDetailTvFragment.requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            appName = this@BaseDetailTvFragment.appName
            executePendingBindings()
        }

        bindRowSupportFragment()

        bindAction()
    }

    fun setGenres(genres: String, delimiters: String = ",") {
        genres.split(delimiters).forEach {
            genresAdapter.setGenres(it)
        }
    }

    fun toggleLoading(isLoading: Boolean) {
        vb.apply {
            this.isLoading = isLoading
            executePendingBindings()
        }
    }

    fun bindDetail(
        detailBackground: String,
        detailTitle: String,
        detailDescription: String,
        detailResolution: String,
        detailResolutionColor: Int
    ) {
        vb.apply {
            this.detailTitle = detailTitle
            this.detailBackground = detailBackground
            this.detailDescription = detailDescription
            this.detailResolution = detailResolution
            this.tvDetailResolution.apply {
                text = detailResolution
                setSlantedBackgroundColor(detailResolutionColor)
            }
            executePendingBindings()
        }
    }

    fun addAction(index: Int = 0, action: ActionModel) {
        with(actionRowsAdapter.get(0) as ListRow) {
            with(this.adapter as ArrayObjectAdapter) {
                this.add(index, action)
            }
        }
    }

    fun addAction(action: ActionModel) {
        with(actionRowsAdapter.get(0) as ListRow) {
            with(this.adapter as ArrayObjectAdapter) {
                addAction(this.size(), action)
            }
        }
    }

    fun replaceAction(index: Int, action: ActionModel) {
        with(actionRowsAdapter.get(0) as ListRow) {
            with(this.adapter as ArrayObjectAdapter) {
                this.replace(index, action)
            }
        }
    }

    fun replaceAction(index: Int, block: ActionModel.Builder.() -> Unit) {
        replaceAction(index, ActionModel.Builder().also(block).build())
    }

    fun addAction(action: ActionModel.Builder.() -> Unit) {
        with(actionRowsAdapter.get(0) as ListRow) {
            with(this.adapter as ArrayObjectAdapter) {
                addAction(this.size(), ActionModel.Builder().also(action).build())
            }
        }
    }

    fun addAction(index: Int = 0, action: ActionModel.Builder.() -> Unit) {
        with(actionRowsAdapter.get(0) as ListRow) {
            with(this.adapter as ArrayObjectAdapter) {
                addAction(index, ActionModel.Builder().also(action).build())
            }
        }
    }

    fun removeAction(action: ActionModel) {
        with(actionRowsAdapter.get(0) as ListRow) {
            with(this.adapter as ArrayObjectAdapter) {
                this.remove(action)
            }
        }
    }

    fun removeAction(block: ActionModel.Builder.() -> Unit) {
        removeAction(ActionModel.Builder().also(block).build())
    }

    fun removeAction(position: Int, count: Int = 1) {
        with(actionRowsAdapter.get(0) as ListRow) {
            with(this.adapter as ArrayObjectAdapter) {
                this.removeItems(position, count)
            }
        }
    }

    fun addDetailRow(related: ListRow?) {
        if (related != null) {
            numberOfRowCount += 1
            rowsAdapter.add(related)
        }
    }

    fun getDetailRow(index: Int): ArrayObjectAdapter? {
        return if (rowsAdapter.size() != 0) {
            with(rowsAdapter.get(index)) {
                if (this is ListRow) {
                    this.adapter as ArrayObjectAdapter
                } else null
            }
        } else null
    }

    private fun bindAction() {
        actionRowsSupportFragment =
            childFragmentManager.findFragmentById(R.id.view_action) as RowsSupportFragment?

        if (actionRowsSupportFragment == null) {
            actionRowsSupportFragment = RowsSupportFragment()
            childFragmentManager.commit {
                replace(R.id.view_action, actionRowsSupportFragment!!)
            }
        }

        actionRowsSupportFragment?.adapter = actionRowsAdapter

        actionRowsSupportFragment?.setOnItemViewClickedListener { _, item, _, row ->
            if (item is ActionModel) {
                onClickedAction(item)
            }
        }

        actionRowsAdapter.clear()
        val listRowAdapter = ArrayObjectAdapter(DetailCard(requireContext()))
        listRowAdapter.setItems(listOf<ActionModel>(), ActionModel.diffTv)
        actionRowsAdapter.add(ListRow(null, listRowAdapter))
    }

    fun toggleGuideBottom(keyCode: Int) {
        if (vb.showFullDetail == true) return
        if (keyCode == 20 || keyCode == 19) {
            requireActivity().currentFocus?.let {
                val percent = if (keyCode == 20) {
                    if (numberOfRowCount > 1) {
                        focusRowCount += 1
                        if (focusRowCount >= numberOfRowCount) {
                            focusRowCount = numberOfRowCount - 1
                        }
                    }
                    when (it) {
                        is ActionCardView -> {
                            if (numberOfRowCount > 1) 0.6f
                            else 0.47f
                        }
                        else -> {
                            if (numberOfRowCount > 1) 0.05f
                            else 0.47f // for related card
                        }
                    }
                } else {
                    focusRowCount -= 1
                    if (focusRowCount <= -1) focusRowCount = -1
                    when (it) {
                        is ActionCardView -> 0.75f
                        else -> {
                            when (focusRowCount) {
                                0 -> 0.6f
                                -1 -> 0.75f
                                else -> 0.05f
                            }
                        }
                    }
                }
                Timber.d("focusEpisode => %s", focusRowCount)
                vb.bottomDetail.setGuidelinePercent(percent)
            }
        }
    }

    private fun bindRowSupportFragment() {
        rowsSupportFragment =
            childFragmentManager.findFragmentById(R.id.view_detail_frame) as RowsSupportFragment?

        if (rowsSupportFragment == null) {
            rowsSupportFragment = RowsSupportFragment()
            childFragmentManager.commit {
                replace(R.id.view_detail_frame, rowsSupportFragment!!)
            }
        }

        rowsSupportFragment?.adapter = rowsAdapter

        rowsSupportFragment?.setOnItemViewClickedListener { _, item, _, row ->
            onClickedItem(rowsAdapter.indexOf(row), item)
        }

        rowsSupportFragment?.setOnItemViewSelectedListener { _, item, _, row ->
            item?.let {
                onItemFocus(item)
            }
        }

        rowsAdapter.clear()
    }

    fun showFullScreenDescription(isShow: Boolean = true) {
        vb.scroll.fullScroll(ScrollView.FOCUS_UP)
        vb.showFullDetail = isShow
    }

    val isOpenFullScreenDescription: Boolean
        get() = vb.showFullDetail == true
}
