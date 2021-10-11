package com.kyawhut.atsycast.share.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.app.ProgressBarManager
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.databinding.ViewLoadingBinding
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkError.Companion.printStackTrace
import com.kyawhut.atsycast.share.ui.error.ErrorFragment
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.addFragment

abstract class BaseRowSupportFragment<VM : BaseViewModel> : RowsSupportFragment(),
    BrowseSupportFragment.MainFragmentAdapterProvider {

    protected abstract val vm: VM

    private val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(ListRowPresenter(FocusHighlight.ZOOM_FACTOR_LARGE))
    }

    private var lastItem: Any? = null

    private val progressBarManager by lazy { ProgressBarManager() }
    private val loadingView: ViewLoadingBinding by lazy {
        ViewLoadingBinding.inflate(LayoutInflater.from(requireContext()))
    }

    private val fragmentAdapter by lazy { BrowseSupportFragment.MainFragmentAdapter(this) }

    abstract fun onItemFocus(it: Any)
    abstract fun onItemClicked(headerItem: HeaderItem, it: Any)

    abstract fun onClickRetry()

    open val isEnableLoadMore: Boolean = false
    open fun onLoadMore(header: HeaderItem) {}

    private val errorFragment by lazy {
        ErrorFragment.newInstance().apply {
            setTargetFragment(this@BaseRowSupportFragment, 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = rowsAdapter

        setOnItemViewSelectedListener { _, item, _, row ->
            if (row is ListRow) {
                item?.let {
                    lastItem = it
                    onItemFocus(it)
                }

                val listRowAdapter = row.adapter as ArrayObjectAdapter
                if (isEnableLoadMore && listRowAdapter.indexOf(item) == listRowAdapter.size() - 1) {
                    onLoadMore(row.headerItem)
                }
            }
        }

        setOnItemViewClickedListener { _, item, _, row ->
            if (row is ListRow) {
                row.headerItem?.let {
                    onItemClicked(it, item)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarManager.apply {
            enableProgressBar()
            setProgressBarView(
                loadingView.root.also {
                    (view.parent as ViewGroup).addView(it)
                }
            )
            initialDelay = 0
        }

        mainFragmentAdapter.fragmentHost.notifyViewCreated(mainFragmentAdapter)

        rowsAdapter.clear()

    }

    fun showLoading() {
        loadingView.iosLoading.toggleAnimation(true)
        progressBarManager.show()
    }

    fun hideLoading() {
        loadingView.iosLoading.toggleAnimation(false)
        progressBarManager.hide()
    }

    override fun getMainFragmentAdapter(): BrowseSupportFragment.MainFragmentAdapter<*> {
        return fragmentAdapter
    }

    protected fun changeBackground(url: String) {
        (requireActivity() as BaseTvActivity<*>).changeBackground(url)
    }

    fun showError(error: NetworkError?, isBackEnabled: Boolean = false) {
        error.printStackTrace()
        hideLoading()
        parentFragmentManager.addFragment(R.id.rows_frame, errorFragment, ErrorFragment.TAG)
        errorFragment.isBackEnabled = isBackEnabled
        errorFragment.errorMessage = if (error?.resId != 0) getString(
            error?.resId ?: 0
        ) else error.message
    }

    fun setRowItem(row: ListRow) {
        rowsAdapter.add(row)
    }

    override fun onResume() {
        lastItem?.let {
            onItemFocus(it)
        }
        super.onResume()
    }

    override fun onDestroy() {
        progressBarManager.hide()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1 -> {
                parentFragmentManager.popBackStack(
                    ErrorFragment.TAG,
                    androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                if (resultCode == Activity.RESULT_OK) {
                    onClickRetry()
                } else {
                    requireActivity().finish()
                }
            }
        }
    }
}
