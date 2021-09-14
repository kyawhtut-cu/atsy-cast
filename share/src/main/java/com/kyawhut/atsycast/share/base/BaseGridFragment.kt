package com.kyawhut.atsycast.share.base

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.VerticalGridSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.VerticalGridPresenter
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.databinding.ViewLoadingBinding
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkError.Companion.printStackTrace
import com.kyawhut.atsycast.share.ui.error.ErrorFragment
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.addFragment

abstract class BaseGridFragment<VM : BaseViewModel> : VerticalGridSupportFragment() {

    protected abstract val vm: VM
    protected open val isPagingEnable: Boolean = true
    protected open val numberOfColumns: Int = 6
    abstract val rowsAdapter: ArrayObjectAdapter
    private val loadingView: ViewLoadingBinding by lazy {
        ViewLoadingBinding.inflate(LayoutInflater.from(requireContext()))
    }

    private var lastItem: Any? = null
    open fun onLoadMore() {}
    abstract fun onItemClicked(it: Any)
    abstract fun onItemFocus(it: Any)
    abstract fun onClickRetry()
    private val errorFragment by lazy {
        ErrorFragment.newInstance().apply {
            setTargetFragment(this@BaseGridFragment, 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
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
    }

    fun showLoading() {
        loadingView.iosLoading.toggleAnimation(true)
        progressBarManager.show()
    }

    fun hideLoading() {
        loadingView.iosLoading.toggleAnimation(false)
        progressBarManager.hide()
    }

    fun showError(error: NetworkError?, isBackEnabled: Boolean = false) {
        error.printStackTrace()
        hideLoading()
        parentFragmentManager.addFragment(R.id.browse_grid_dock, errorFragment, ErrorFragment.TAG)
        errorFragment.isBackEnabled = isBackEnabled
        errorFragment.errorMessage = if (error?.resId != 0) getString(
            error?.resId ?: 0
        ) else error.message
    }

    private fun initUI() {
        val verticalGridPresenter = VerticalGridPresenter(
            FocusHighlight.ZOOM_FACTOR_LARGE, true
        )
        verticalGridPresenter.numberOfColumns = numberOfColumns
        gridPresenter = verticalGridPresenter

        adapter = rowsAdapter

        setOnItemViewSelectedListener { _, item, _, _ ->
            lastItem = item
            item?.let {
                onItemFocus(it)
                if (isPagingEnable && rowsAdapter.indexOf(it) >= rowsAdapter.size() - numberOfColumns) {
                    onLoadMore()
                }
            }
        }

        setOnItemViewClickedListener { _, item, _, _ ->
            item?.let {
                onItemClicked(it)
            }
        }
    }

    protected fun changeBackground(
        uri: String? = null,
        drawable: Drawable? = null,
        bitmap: Bitmap? = null
    ) {
        (requireActivity() as BaseTvActivity<*>).changeBackground(uri, drawable, bitmap)
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
