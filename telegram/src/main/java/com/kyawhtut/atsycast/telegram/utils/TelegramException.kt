package com.kyawhtut.atsycast.telegram.utils

import javax.annotation.concurrent.Immutable

/**
 * Created by Kyaw Htut on 13/10/2022.
 */
@Immutable
internal data class TelegramException(
    val code: Int = 0,
    override val message: String = ""
) : RuntimeException()
