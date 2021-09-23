package com.kyawhut.atsycast.twod.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.kyawhut.atsycast.share.components.RoundedConstraintLayout
import com.kyawhut.atsycast.twod.data.network.response.TodayResponse
import com.kyawhut.atsycast.twod.databinding.View2dBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author kyawhtut
 * @date 9/20/21
 */
class TwoDView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RoundedConstraintLayout(context, attrs, defStyleAttr) {

    private val targetDateFormat by lazy {
        SimpleDateFormat("EEEE, dd MMM, yyyy", Locale.ENGLISH)
    }
    private val originalDateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    }

    private val isTwelveHour: Boolean
        get() = getCurrentHours() <= 12

    private val isFourHour: Boolean
        get() = getCurrentHours() >= 12

    private val vb: View2dBinding by lazy {
        View2dBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        bind(data = null)
    }

    fun bind(luckyNumber: String = "", data: TodayResponse? = null) {
        if (data == null) {
            isVisible = false
            return
        }
        isVisible = true
        vb.apply {
            viewLuckyNumber.isVisible = false
            tvTwoDDate.isVisible = false
            view2d.isVisible = false
            view3d.isVisible = false
        }

        data.twoD?.let { twoD ->
            vb.apply {
                viewLuckyNumber.isVisible = true
                tvTwoDDate.isVisible = true
                view2d.isVisible = true

                this.luckyNumber = luckyNumber
                dateTime = targetDateFormat.format(
                    originalDateFormat.parse(twoD.date) ?: Date()
                )

                pmTwoD = if (isFourHour) twoD.pm.twoD ?: "--" else "--"
                pmSet = twoD.pm.set ?: "----.--"
                pmValue = twoD.pm.value ?: "----.--"

                amTwoD = if (isTwelveHour) twoD.am.twoD ?: "--" else "--"
                amSet = twoD.am.set ?: "----.--"
                amValue = twoD.am.value ?: "----.--"

                nineInternet = twoD.am.internet ?: "--"
                nineModern = twoD.am.modern ?: "--"

                twoInternet = twoD.pm.internet ?: "--"
                twoModern = twoD.pm.modern ?: "--"
            }
        }

        data.threeD?.let { threeD ->
            vb.apply {
                tvTwoDDate.isVisible = true
                view3d.isVisible = true

                if (dateTime.isNullOrEmpty()) dateTime = targetDateFormat.format(
                    originalDateFormat.parse(threeD.date) ?: Date()
                )

                this.threeD = threeD.threeD
            }
        }

        vb.executePendingBindings()
    }

    private fun getCurrentHours(): Int {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }
}
