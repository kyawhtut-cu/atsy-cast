package com.kyawhtut.atsycast.telegram.ui.player

import com.kyawhtut.atsycast.telegram.data.model.PlayerModel
import com.kyawhtut.atsycast.telegram.data.telegram.Telegram
import com.kyawhtut.atsycast.telegram.utils.Response
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getFile
import com.kyawhtut.atsycast.telegram.utils.TelegramExtension.getMessage
import com.kyawhtut.atsycast.telegram.utils.TelegramUtils
import com.kyawhtut.atsycast.telegram.utils.map
import com.kyawhtut.atsycast.telegram.utils.success
import com.kyawhut.atsycast.share.model.VideoSourceModel
import org.drinkless.td.libcore.telegram.TdApi
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 21/10/2022
 */
internal class PlayerRepoImpl @Inject constructor(
    private val telegram: Telegram,
) : PlayerRepo {

    override suspend fun getVideo(chatID: Long, videoID: Long): Response<PlayerModel> {
        return telegram.getMessage(chatID, videoID).map {
            if (it.content.constructor == TdApi.MessageVideo.CONSTRUCTOR) {
                val video = it.content as TdApi.MessageVideo
                var filePath = ""
                var fileID = 0
                telegram.getFile(video.video.video.id).success { file ->
                    filePath = file.local.path
                    fileID = file.id
                }
                PlayerModel(
                    video.video.video.id.toString(),
                    TelegramUtils.formatAsFileSize(video.video.video.size.toLong()),
                    video.video.thumbnail?.file?.local?.path ?: "",
                    VideoSourceModel(
                        fileID.toString(),
                        TelegramUtils.formatAsFileSize(video.video.video.size.toLong()),
                        url = filePath
                    )
                )
            } else throw RuntimeException("Unsupported message type.")
        }
    }
}
