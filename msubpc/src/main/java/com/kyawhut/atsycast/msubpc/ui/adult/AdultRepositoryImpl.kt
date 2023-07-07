package com.kyawhut.atsycast.msubpc.ui.adult

import com.kyawhut.atsycast.msubpc.data.network.MSubAPI
import com.kyawhut.atsycast.msubpc.data.network.response.AdultResponse
import com.kyawhut.atsycast.msubpc.utils.AesEncryptDecrypt
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class AdultRepositoryImpl @Inject constructor(
    private val api: MSubAPI,
    private val crashlytics: Crashlytics,
) : AdultRepository {

    override suspend fun getAdult(
        callback: (NetworkResponse<List<AdultResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.getAdultMovie().mapNotNull {
                if (it.adultStream != null) it.copy(
                    adultStream = AesEncryptDecrypt.getDecryptedString(it.adultStream)
                ) else null
            }
        }
        response.post(callback)
    }
}
