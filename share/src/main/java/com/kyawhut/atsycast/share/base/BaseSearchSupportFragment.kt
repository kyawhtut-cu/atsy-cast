package com.kyawhut.atsycast.share.base

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.ProgressBarManager
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.*
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.components.IOSLoading
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkError.Companion.printStackTrace
import com.kyawhut.atsycast.share.ui.error.ErrorFragment
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.addFragment
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 9/7/21
 */
abstract class BaseSearchSupportFragment<VM : BaseViewModel> : SearchSupportFragment(),
    SearchSupportFragment.SearchResultProvider {

    companion object {
        const val FINISH_ON_RECOGNIZER_CANCELED = true
        const val REQUEST_SPEECH = 0x00000010
    }

    open val focusHighlight: Int = FocusHighlight.ZOOM_FACTOR_LARGE
    open val useFocusDimmer: Boolean = true

    protected val rowAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(ListRowPresenter(focusHighlight, useFocusDimmer))
    }
    open val isPagingEnable: Boolean
        get() = false

    protected var _isResultFound: Boolean = false
    val isResultFound: Boolean
        get() = rowAdapter.size() > 0 && _isResultFound
    private var isSearchNow: Boolean = false
    private var isRunningTimer: Boolean = false
    private var query: String = ""
    private val loadingView by lazy {
        LayoutInflater.from(context).inflate(R.layout.view_loading, null)
    }
    private val progressBarManager by lazy {
        ProgressBarManager()
    }
    private var workSelected: Any? = null

    abstract fun onClickRetry(query: String)
    private val errorFragment by lazy {
        ErrorFragment.newInstance().apply {
            setTargetFragment(this@BaseSearchSupportFragment, 1)
        }
    }

    abstract val vm: VM

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

        setSearchResultProvider(this)
        setOnItemViewClickedListener { _, item, _, row ->
            item?.let {
                onItemClicked(row.headerItem.id.toInt(), it)
            }
        }
        setOnItemViewSelectedListener { _, item, _, row ->
            workSelected = item
            workSelected?.let {
                onItemSelected(it)
                val listRowAdapter = (row as ListRow).adapter as ArrayObjectAdapter
                if (isPagingEnable && listRowAdapter.indexOf(item) == listRowAdapter.size() - 1) {
                    onLoadMore(query)
                }
            }
        }

        if (!hasPermission(Manifest.permission.RECORD_AUDIO)) {
            Timber.d("Does not have RECORD_AUDIO, using SpeechRecognitionCallback")
            setSpeechRecognitionCallback {
                try {
                    startActivityForResult(recognizerIntent, REQUEST_SPEECH)
                } catch (e: ActivityNotFoundException) {
                    Timber.e(e, "Cannot find activity for speech recognizer")
                }
            }
        } else {
            Timber.d("We DO have RECORD_AUDIO")
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == requireContext().packageManager.checkPermission(
            permission,
            requireContext().packageName
        )
    }

    fun showLoading() {
        loadingView.findViewById<IOSLoading>(R.id.ios_loading).toggleAnimation(true)
        progressBarManager.show()
    }

    fun hideLoading() {
        loadingView.findViewById<IOSLoading>(R.id.ios_loading).toggleAnimation(false)
        progressBarManager.hide()
    }

    fun showError(error: NetworkError?, isBackEnabled: Boolean = false) {
        errorFragment.isBackEnabled = isBackEnabled
        error.printStackTrace()
        hideLoading()
        parentFragmentManager.addFragment(android.R.id.content, errorFragment, ErrorFragment.TAG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1 -> {
                parentFragmentManager.popBackStack(
                    ErrorFragment.TAG,
                    androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                if (resultCode == Activity.RESULT_OK) {
                    onClickRetry(query)
                } else {
                    requireActivity().finishAndRemoveTask()
                }
            }
            REQUEST_SPEECH -> when (resultCode) {
                Activity.RESULT_OK -> setSearchQuery(data, true)
                else ->                         // If recognizer is canceled or failed, keep focus on the search orb
                    if (FINISH_ON_RECOGNIZER_CANCELED) {
                        if (!isResultFound) {
                            Timber.d("Voice search canceled.")
                            requireView().findViewById<View>(R.id.lb_search_bar_speech_orb)
                                .requestFocus()
                        }
                    }
            }
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Timber.d("Search text submitted: %s", query)
        this.query = query
        onSearch(query)
        title = query
        return true
    }

    override fun getResultsAdapter(): ObjectAdapter {
        return rowAdapter
    }

    override fun onQueryTextChange(newQuery: String): Boolean {
        return true
    }

    fun focusOnSearch() {
        requireView().findViewById<View>(R.id.lb_search_bar).requestFocus()
    }

    abstract fun onItemClicked(index: Int, it: Any)
    open fun onItemSelected(item: Any) {}
    abstract fun onSearch(query: String)
    open fun onLoadMore(query: String) {}
}
