package com.kyawhut.atsycast.share.base

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.core.view.updateLayoutParams
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import androidx.leanback.widget.GuidedDatePickerAction
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.utils.binding.ImageBinding.loadImage
import com.kyawhut.atsycast.share.utils.binding.TextViewBinding.applyMMFont
import com.kyawhut.atsycast.share.utils.extension.Extension.getDrawableValue
import java.util.*

/**
 * @author kyawhtut
 * @date 9/7/21
 */
abstract class BaseGuidedStepSupportFragment : GuidedStepSupportFragment() {

    open val title: String = ""
    open val description: String = ""
    open val breadcrumb: String = ""
    open val icon: Drawable? by lazy {
        getDrawableValue(R.drawable.thumbnail_horizontal)
    }

    open fun onCreateActions(actions: MutableList<GuidedAction>) {}
    open fun onViewCreated() {}

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        return GuidanceStylist.Guidance(
            title,
            description,
            breadcrumb,
            icon
        )
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        guidanceStylist.apply {
            iconView.updateLayoutParams {
                width = 200
                height = 140
            }
            titleView.apply {
                setTextColor(Color.WHITE)
                text = title
                this.applyMMFont()
            }
            descriptionView.apply {
                setTextColor(Color.WHITE)
                text = description //HtmlSpanner().fromHtml(description)
                applyMMFont()
            }
            breadcrumbView.apply {
                setTextColor(Color.WHITE)
                text = breadcrumb
                applyMMFont()
            }
        }
        onViewCreated()
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        onCreateActions(actions)
    }

    override fun onGuidedActionClicked(action: GuidedAction) {
        onClickAction(action.id)
    }

    override fun onGuidedActionEditedAndProceed(action: GuidedAction): Long {
        onEditClickAction(action)
        return super.onGuidedActionEditedAndProceed(action)
    }

    override fun onSubGuidedActionClicked(action: GuidedAction): Boolean {
        onSubClickAction(action.id)
        return super.onSubGuidedActionClicked(action)
    }

    fun setLogo(resource: Int) {
        guidanceStylist.iconView.setImageResource(resource)
    }

    fun setLogo(drawable: Drawable) {
        guidanceStylist.iconView.setImageDrawable(drawable)
    }

    fun setLogo(bitmap: Bitmap) {
        guidanceStylist.iconView.setImageBitmap(bitmap)
    }

    fun setLogo(url: String) {
        guidanceStylist.iconView.loadImage(url)
    }

    fun addAction(actions: MutableList<GuidedAction>, id: Long, title: String, desc: String? = "") {
        actions.add(
            GuidedAction.Builder(requireContext())
                .id(id)
                .title(title)
                .description(desc)
                .build()
        )
    }

    fun addEditableAction(
        actions: MutableList<GuidedAction>,
        id: Long,
        title: String,
        desc: String,
        inputType: Int
    ) {
        actions.add(
            GuidedAction.Builder(requireContext())
                .id(id)
                .title(title)
                .descriptionEditable(true)
                .description(desc)
                .descriptionInputType(inputType)
                .build()
        )
    }

    fun addDropDownAction(
        actions: MutableList<GuidedAction>,
        id: Long,
        title: String,
        desc: String,
        selectionAction: List<GuidedAction>
    ) {
        actions.add(
            GuidedAction.Builder(requireContext())
                .id(id)
                .title(title)
                .description(desc)
                .subActions(selectionAction)
                .build()
        )
    }

    fun addCheckAction(
        actions: MutableList<GuidedAction>,
        id: Int,
        title: String,
        desc: String,
        checked: Boolean
    ) {
        actions.add(
            GuidedAction.Builder(requireContext())
                .title(title)
                .description(desc)
                .checkSetId(id)
                .checked(checked)
                .build()
        )
    }

    fun addDateAction(actions: MutableList<GuidedAction>, id: Long, title: String, date: Long) {
        actions.add(
            GuidedDatePickerAction.Builder(requireContext())
                .id(id)
                .date(date)
                .datePickerFormat("DMY")
                .maxDate(Date().time)
                .title(title)
                .build()
        )
    }

    open fun onClickAction(id: Long) {}

    open fun onSubClickAction(id: Long) {}

    open fun onEditClickAction(action: GuidedAction) {}
}
