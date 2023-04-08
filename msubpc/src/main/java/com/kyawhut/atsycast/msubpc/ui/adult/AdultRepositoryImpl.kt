package com.kyawhut.atsycast.msubpc.ui.adult

import com.kyawhut.atsycast.msubpc.data.network.MSubAPI
import com.kyawhut.atsycast.msubpc.data.network.response.AdultResponse
import com.kyawhut.atsycast.msubpc.utils.AesEncryptDecrypt
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import timber.log.Timber
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
                    adultStream = AesEncryptDecrypt.getDecryptedString(it.adultStream).also { url ->
                        Timber.d(
                            "AdultRepository => %s, %s, %s, %s, %s, %s",
                            it.adultTitle,
                            url,
                            AesEncryptDecrypt.getDecryptedStringForAnother("81203849-6b12-408a-9228-2fd315b13cf4", it.adultStream),
                            AesEncryptDecrypt.getDecryptedStringForAnother("17677416837613499657665379123578", it.adultStream),
                            AesEncryptDecrypt.getDecryptedStringForAnother("6d8b0d7d19b343a2844408a925b56e50", it.adultStream),
                            AesEncryptDecrypt.getDecryptedStringForAnother("5CBB5AD9EAE58DCB5DDCFEAD19F8F0298F4388A7", it.adultStream)
                        )
                    }
                ) else null
            }
        }
        response.post(callback)
    }
}
