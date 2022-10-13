package com.kyawhut.atsycast.msubpc

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kyawhut.atsycast.msubpc.utils.AesEncryptDecrypt
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author kyawhtut
 * @date 7/20/22
 */
@RunWith(AndroidJUnit4::class)
class DecryptionTest {

    @Test
    fun testDecryption() {
        val encryptedURL =
            "eyJpdiI6IjMybmI2Z2dwNS9GdHhUeDhCRmRPeGc9PSIsInZhbHVlIjoibmxZVU4ydjFyakoxdUw0STBxU3ozaXl6cjE0K08yOFM0U1ByeHFVSTdzRDFiUlBLMDhhWDROUmYvUHhGRCt6a2NHOEg3cDlIV3RqRzRjVERJZnZ6STRRY0hRVCtQdjdBU0loSllvQzB4VTJmb3BiMXN4bmNyL0xsczNVRUsxRWFJV2ptV1VUK2FvSVVDM1Zpb1dKRUR3PT0iLCJtYWMiOiJhZTk2NWM3NzQxZGQwMTNmNjBiMzJhMjI3NjIwOTVjMDVlYTU3YmNmZjk3YWQzMDY5NjAxNGFjMzNjZjgzYjY3In0="
        val decrypted = AesEncryptDecrypt.getDecryptedString(encryptedURL)
        Assert.assertEquals("com.kyawhut.atsycast.msubpc.test", decrypted)
    }
}
