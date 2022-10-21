package com.kyawhtut.atsycast.telegram.ui.player

import com.kyawhtut.atsycast.telegram.data.model.PlayerModel
import com.kyawhtut.atsycast.telegram.utils.Response

/**
 * @author kyawhtut
 * @date 21/10/2022
 */
internal interface PlayerRepo {

    suspend fun getVideo(chatID: Long, videoID: Long): Response<PlayerModel>
}
