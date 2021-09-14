package com.kyawhut.atsycast.share.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.components.IOSLoading
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkError.Companion.printStackTrace
import com.kyawhut.atsycast.share.ui.error.ErrorFragment
import com.kyawhut.atsycast.share.utils.binding.TextViewBinding.applyMMFont
import com.kyawhut.atsycast.share.utils.extension.Extension.getColorValue
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.addFragment

/**
 * @author kyawhtut
 * @date 9/3/21
 */
abstract class BaseBrowseSupportFragment : BrowseSupportFragment() {

    private val rowsAdapter by lazy { ArrayObjectAdapter(ListRowPresenter()) }
    private val rowDiffCallback by lazy { RowDiffCallback() }
    open val onSearchClicked: (() -> Unit)? = null
    abstract fun onClickRetry()
    private val errorFragment by lazy {
        ErrorFragment.newInstance().apply {
            setTargetFragment(this@BaseBrowseSupportFragment, 1)
        }
    }

    abstract fun onCreateRowFragment(header: HeaderItem): Fragment

    private val loadingView by lazy {
        LayoutInflater.from(context).inflate(R.layout.view_loading, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUIElements()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarManager.apply {
            enableProgressBar()
            setProgressBarView(
                loadingView.also {
                    (view.parent as ViewGroup).addView(it)
                })
            initialDelay = 0
        }

        view.findViewById<TitleView>(R.id.browse_title_group)
            .findViewById<TextView>(R.id.title_text).apply {
                textSize = resources.getDimensionPixelSize(R.dimen.text_regular_2x).toFloat()
                applyMMFont()
            }

        adapter = rowsAdapter
    }

    fun clearRows() {
        rowsAdapter.clear()
    }

    fun setRowItem(rows: List<Row>) {
        rowsAdapter.setItems(rows, rowDiffCallback)
        startEntranceTransition()
    }

    fun addRowItem(rows: Row) {
        rowsAdapter.add(rows)
    }

    fun addRowItem(index: Int, rows: Row) {
        rowsAdapter.add(index, rows)
    }

    fun removeRow(rows: Row) {
        rowsAdapter.remove(rows)
    }

    fun removeRow(index: Int, count: Int = 1) {
        rowsAdapter.removeItems(index, count)
    }

    fun getRow(index: Int): Row {
        return rowsAdapter[index] as Row
    }

    private fun setupUIElements() {
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        if (onSearchClicked != null) setOnSearchClickedListener {
            onSearchClicked?.invoke()
        }

        brandColor = getColorValue(R.color.colorPrimary)
        searchAffordanceColor = getColorValue(R.color.colorAccent)
        mainFragmentRegistry.registerFragment(PageRow::class.java, PageRowFragmentFactory())
    }

    fun showLoading() {
        loadingView.findViewById<IOSLoading>(R.id.ios_loading).toggleAnimation(true)
        progressBarManager.show()
    }

    fun hideLoading() {
        loadingView.findViewById<IOSLoading>(R.id.ios_loading).toggleAnimation(false)
        progressBarManager.hide()
    }

    fun showError(error: NetworkError?, isBackEnable: Boolean = false) {
        errorFragment.isBackEnabled = isBackEnable
        error.printStackTrace()
        hideLoading()
        parentFragmentManager.addFragment(R.id.scale_frame, errorFragment, ErrorFragment.TAG)
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
                }
            }
        }
    }

    private inner class PageRowFragmentFactory : BrowseSupportFragment.FragmentFactory<Fragment>() {

        override fun createFragment(row: Any?): Fragment {
            val headerItem = (row as Row).headerItem
            title = headerItem.name
            return onCreateRowFragment(headerItem)
        }
    }

    class RowDiffCallback : DiffCallback<Row>() {

        override fun areItemsTheSame(oldItem: Row, newItem: Row) =
            oldItem.hashCode() == newItem.hashCode()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Row, newItem: Row) =
            oldItem == newItem
    }
}