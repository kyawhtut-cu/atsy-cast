package com.kyawhut.atsycast.ui.drivetest

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.AbstractInputStreamContent
import com.google.api.client.http.ByteArrayContent
import com.google.api.client.http.FileContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import com.kyawhut.atsycast.R
import timber.log.Timber
import java.io.*
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

/**
 * @author kyawhtut
 * @date 12/21/21
 */
class DriveTestActivity : Activity() {

    companion object {
        private const val REQUEST_CODE_SIGN_IN = 1
        private const val REQUEST_CODE_OPEN_DOCUMENT = 2
    }

    private lateinit var driveServiceHelper: DriveServiceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drive_test)
    }

    override fun onStart() {
        super.onStart()

        val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (googleSignInAccount == null) {
            // Authenticate the user. For most apps, this should be done when the user performs an
            // action that requires Drive access rather than in onCreate.
            requestSignIn()
        } else {
            driveServiceHelper = DriveServiceHelper(
                DriveServiceHelper.getGoogleDrive(
                    this,
                    googleSignInAccount,
                    getString(R.string.app_name)
                )
            )
            queryFile()
        }
    }

    private fun requestSignIn() {
        Timber.d("Requesting sign-in")
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(DriveScopes.DRIVE))
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(this, signInOptions)
        startActivityForResult(client.signInIntent, REQUEST_CODE_SIGN_IN)
    }

    /**
     * Handles the {@code data} of a completed sign-in activity initiated from {@link
     * #requestSignIn()}.
     */
    private fun handleSignInResult(data: Intent) {
        GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener {
            Timber.d("Signed in as ${it.email}")
            // Use the authenticated account to sign in to the Drive service.
            // The DriveServiceHelper encapsulates all REST API and SAF functionality.
            // Its instantiation is required before handling any onClick actions.
            driveServiceHelper = DriveServiceHelper(
                DriveServiceHelper.getGoogleDrive(
                    this,
                    it,
                    getString(R.string.app_name)
                )
            )
            queryFile()
        }.addOnFailureListener {
            Timber.e(it, "Unable to sign in")
        }
    }

    private fun queryFile() {
        if (::driveServiceHelper.isInitialized) {
            Timber.d("Querying for files.")
            driveServiceHelper.queryFiles().addOnSuccessListener {
                Timber.d("File size => %s", it.size)
                it.forEach { file ->
                    Timber.d("File => %s", file)
                }
            }.addOnFailureListener {
                Timber.e(it, "Unable to query files.")
            }

//            driveServiceHelper.createTextFile("driveTestActivity.txt", "Hello")
//                .addOnSuccessListener {
//                    Timber.d("Success => %s", it)
//                }.addOnFailureListener {
//                Timber.e(it, "Unable to createTextFile.")
//            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("onActivityResult => %s %s %s", requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SIGN_IN -> {
                if (data != null) {
                    handleSignInResult(data)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

class DriveServiceHelper(private val driveService: Drive) {

    companion object {
        const val TYPE_AUDIO = "application/vnd.google-apps.audio"
        const val TYPE_GOOGLE_DOCS = "application/vnd.google-apps.document"
        const val TYPE_GOOGLE_DRAWING = "application/vnd.google-apps.drawing"
        const val TYPE_GOOGLE_DRIVE_FILE = "application/vnd.google-apps.file"
        const val TYPE_GOOGLE_DRIVE_FOLDER = "application/vnd.google-apps.folder"
        const val TYPE_GOOGLE_FORMS = "application/vnd.google-apps.form"
        const val TYPE_GOOGLE_FUSION_TABLES = "application/vnd.google-apps.fusiontable"
        const val TYPE_GOOGLE_MY_MAPS = "application/vnd.google-apps.map"
        const val TYPE_PHOTO = "application/vnd.google-apps.photo"
        const val TYPE_GOOGLE_SLIDES = "application/vnd.google-apps.presentation"
        const val TYPE_GOOGLE_APPS_SCRIPTS = "application/vnd.google-apps.script"
        const val TYPE_GOOGLE_SITES = "application/vnd.google-apps.site"
        const val TYPE_GOOGLE_SHEETS = "application/vnd.google-apps.spreadsheet"
        const val TYPE_UNKNOWN = "application/vnd.google-apps.unknown"
        const val TYPE_VIDEO = "application/vnd.google-apps.video"
        const val TYPE_3_RD_PARTY_SHORTCUT = "application/vnd.google-apps.drive-sdk"

        const val EXPORT_TYPE_HTML = "text/html"
        const val EXPORT_TYPE_HTML_ZIPPED = "application/zip"
        const val EXPORT_TYPE_PLAIN_TEXT = "text/plain"
        const val EXPORT_TYPE_RICH_TEXT = "application/rtf"
        const val EXPORT_TYPE_OPEN_OFFICE_DOC = "application/vnd.oasis.opendocument.text"
        const val EXPORT_TYPE_PDF = "application/pdf"
        const val EXPORT_TYPE_MS_WORD_DOCUMENT =
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        const val EXPORT_TYPE_EPUB = "application/epub+zip"
        const val EXPORT_TYPE_MS_EXCEL =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        const val EXPORT_TYPE_OPEN_OFFICE_SHEET = "application/x-vnd.oasis.opendocument.spreadsheet"
        const val EXPORT_TYPE_CSV = "text/csv"
        const val EXPORT_TYPE_TSV = "text/tab-separated-values"
        const val EXPORT_TYPE_JPEG = "application/zip"
        const val EXPORT_TYPE_PNG = "image/png"
        const val EXPORT_TYPE_SVG = "image/svg+xml"
        const val EXPORT_TYPE_MS_POWER_POINT =
            "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        const val EXPORT_TYPE_OPEN_OFFICE_PRESENTATION =
            "application/vnd.oasis.opendocument.presentation"
        const val EXPORT_TYPE_JSON = "application/vnd.google-apps.script+json"

        fun getGoogleDrive(context: Context, account: GoogleSignInAccount, appName: String): Drive {
            val credential = GoogleAccountCredential.usingOAuth2(
                context,
                listOf(DriveScopes.DRIVE_FILE)
            )
            credential.selectedAccount = account.account
            return Drive.Builder(
                AndroidHttp.newCompatibleTransport(),
                GsonFactory(),
                credential
            ).setApplicationName(appName).build()
        }
    }

    private val executor = Executors.newSingleThreadExecutor()

    /**
     * Creates a text file in the user's My Drive folder and returns its file ID.
     */
    fun createFile(fileName: String, folderId: String = "root"): Task<GoogleDriveFileHolder> {
        return Tasks.call(executor, {
            val metadata = File().setParents(listOf(folderId))
                .setMimeType(EXPORT_TYPE_PLAIN_TEXT).setName(fileName)
            val googleFile = driveService.files().create(metadata).execute()
                ?: throw IOException("Null result when requesting file creation.")
            GoogleDriveFileHolder(
                googleFile.id,
                googleFile.name,
                googleFile.createdTime,
                googleFile.size,
                googleFile.modifiedTime,
                googleFile.starred,
                googleFile.mimeType
            )
        })
    }

    fun createTextFile(
        fileName: String,
        content: String,
        folderId: String = "root"
    ): Task<GoogleDriveFileHolder> {
        return Tasks.call(executor, {
            val metadata = File()
                .setParents(listOf(folderId))
                .setMimeType(EXPORT_TYPE_PLAIN_TEXT)
                .setName(fileName)
            val contentStream = ByteArrayContent.fromString(EXPORT_TYPE_PLAIN_TEXT, content)
            val googleFile = driveService.files().create(metadata, contentStream).execute()
                ?: throw IOException("Null result when requesting file creation.")
            GoogleDriveFileHolder(
                googleFile.id,
                googleFile.name,
                googleFile.createdTime,
                googleFile.size,
                googleFile.modifiedTime,
                googleFile.starred,
                googleFile.mimeType
            )
        })
    }

    fun createTextFileIfNotExist(
        fileName: String,
        content: String,
        folderId: String = "root"
    ): Task<GoogleDriveFileHolder> {
        return Tasks.call(executor, {
            val result = driveService.files().list()
                .setQ("mimeType = '$EXPORT_TYPE_PLAIN_TEXT' and name = '$fileName' ")
                .setSpaces("drive")
                .execute()
            if (result.files.size > 0) {
                return@call GoogleDriveFileHolder(
                    result.files[0].id,
                    result.files[0].name,
                    result.files[0].createdTime,
                    result.files[0].size,
                    result.files[0].modifiedTime,
                    result.files[0].starred,
                    result.files[0].mimeType
                )
            }
            val metadata = File()
                .setParents(listOf(folderId))
                .setMimeType(EXPORT_TYPE_PLAIN_TEXT)
                .setName(fileName)
            val contentStream = ByteArrayContent.fromString(EXPORT_TYPE_PLAIN_TEXT, content)
            val googleFile = driveService.files().create(metadata, contentStream).execute()
                ?: throw IOException("Null result when requesting file creation.")
            GoogleDriveFileHolder(
                googleFile.id,
                googleFile.name,
                googleFile.createdTime,
                googleFile.size,
                googleFile.modifiedTime,
                googleFile.starred,
                googleFile.mimeType
            )
        })
    }

    fun createFolder(folderName: String, folderId: String = "root"): Task<GoogleDriveFileHolder> {
        return Tasks.call(executor, {
            val metadata = File()
                .setParents(listOf(folderId))
                .setMimeType(TYPE_GOOGLE_DRIVE_FOLDER)
                .setName(folderName)
            val googleFile = driveService.files().create(metadata).execute()
                ?: throw IOException("Null result when requesting folder creation.")
            GoogleDriveFileHolder(
                googleFile.id,
                googleFile.name,
                googleFile.createdTime,
                googleFile.size,
                googleFile.modifiedTime,
                googleFile.starred,
                googleFile.mimeType
            )
        })
    }

    fun createFolderIfNotExist(
        folderName: String,
        folderId: String = "root"
    ): Task<GoogleDriveFileHolder> {
        return Tasks.call(executor, {
            val result = driveService.files().list()
                .setQ("mimeType = '$TYPE_GOOGLE_DRIVE_FOLDER' and name = '$folderName' ")
                .setSpaces("drive")
                .execute()
            if (result.files.size > 0) {
                return@call GoogleDriveFileHolder(
                    result.files[0].id,
                    result.files[0].name,
                    result.files[0].createdTime,
                    result.files[0].size,
                    result.files[0].modifiedTime,
                    result.files[0].starred,
                    result.files[0].mimeType
                )
            }
            val metadata = File()
                .setParents(listOf(folderId))
                .setMimeType(TYPE_GOOGLE_DRIVE_FOLDER)
                .setName(folderName)
            val googleFile = driveService.files().create(metadata).execute()
                ?: throw IOException("Null result when requesting folder creation.")
            GoogleDriveFileHolder(
                googleFile.id,
                googleFile.name,
                googleFile.createdTime,
                googleFile.size,
                googleFile.modifiedTime,
                googleFile.starred,
                googleFile.mimeType
            )
        })
    }

    /**
     * Opens the file identified by {@code fileId} and returns a {@link Pair} of its name and
     * contents.
     */
    fun readFile(fileId: String): Task<Pair<String, String>> {
        return Tasks.call(executor, {
            // Retrieve the metadata as a File object.
            val metadata = driveService.files().get(fileId).execute()
            val name = metadata.name
            // Stream the file contents to a String.
            val inS = driveService.files().get(fileId).executeMediaAsInputStream()
            val reader = BufferedReader(InputStreamReader(inS))
            val stringBuilder = StringBuilder()
            reader.forEachLine {
                stringBuilder.append(it)
            }
            Pair(name, stringBuilder.toString())
        })
    }

    fun deleteFolderFile(fileId: String): Task<Unit> {
        return Tasks.call(executor, {
            driveService.files().delete(fileId).execute()
        })
    }

    fun uploadFile(
        googleDriveFile: File,
        content: AbstractInputStreamContent
    ): Task<GoogleDriveFileHolder> {
        return Tasks.call(executor, {
            val fileMeta = driveService.files().create(googleDriveFile, content).execute()
            GoogleDriveFileHolder(
                fileMeta.id,
                fileMeta.name,
                fileMeta.createdTime,
                fileMeta.size,
                fileMeta.modifiedTime,
                fileMeta.starred,
                fileMeta.mimeType
            )
        })
    }

    fun uploadFile(
        localFile: java.io.File,
        mimeType: String,
        folderId: String = "root"
    ): Task<GoogleDriveFileHolder> {
        return Tasks.call(executor, {
            val metadata = File()
                .setParents(listOf(folderId))
                .setMimeType(mimeType)
                .setName(localFile.name)
            val fileContent = FileContent(mimeType, localFile)
            val fileMeta = driveService.files().create(metadata, fileContent).execute()
            GoogleDriveFileHolder(
                fileMeta.id,
                fileMeta.name,
                fileMeta.createdTime,
                fileMeta.size,
                fileMeta.modifiedTime,
                fileMeta.starred,
                fileMeta.mimeType
            )
        })
    }

    fun searchFolder(folderName: String): Task<List<GoogleDriveFileHolder>> {
        return Tasks.call(executor, {
            val googleDriveFolderList = mutableListOf<GoogleDriveFileHolder>()
            // Retrieve the metadata as a File object.
            val result = driveService.files().list()
                .setQ("mimeType = '$TYPE_GOOGLE_DRIVE_FOLDER' and name = '$folderName' ")
                .setSpaces("drive")
                .execute()
            result.files.forEach {
                googleDriveFolderList.add(
                    GoogleDriveFileHolder(
                        it.id,
                        it.name,
                        it.createdTime,
                        it.size,
                        it.modifiedTime,
                        it.starred,
                        it.mimeType
                    )
                )
            }
            googleDriveFolderList
        })
    }

    fun searchFile(fileName: String, mimeType: String): Task<List<GoogleDriveFileHolder>> {
        return Tasks.call(executor, {
            val googleDriveFolderList = mutableListOf<GoogleDriveFileHolder>()
            // Retrieve the metadata as a File object.
            val result = driveService.files().list()
                .setQ("mimeType = '$mimeType' and name = '$fileName' ")
                .setSpaces("drive")
                .execute()
            result.files.forEach {
                googleDriveFolderList.add(
                    GoogleDriveFileHolder(
                        it.id,
                        it.name,
                        it.createdTime,
                        it.size,
                        it.modifiedTime,
                        it.starred,
                        it.mimeType
                    )
                )
            }
            googleDriveFolderList
        })
    }

    fun downloadFile(fileSaveLocation: java.io.File, fileId: String): Task<Unit> {
        return Tasks.call(executor, {
            // Retrieve the metadata as a File object.
            val outputStream = FileOutputStream(fileSaveLocation)
            driveService.files().get(fileId).executeMediaAndDownloadTo(outputStream)
        })
    }

    fun downloadFile(fileId: String): Task<InputStream> {
        return Tasks.call(executor, {
            driveService.files().get(fileId).executeMediaAsInputStream()
        })
    }

    fun exportFile(fileSaveLocation: java.io.File, fileId: String, mimeType: String): Task<Unit> {
        return Tasks.call(executor, {
            // Retrieve the metadata as a File object.
            val outputStream = FileOutputStream(fileSaveLocation)
            driveService.files().export(fileId, mimeType).executeAndDownloadTo(outputStream)
        })
    }

    /**
     * Updates the file identified by {@code fileId} with the given {@code name} and {@code
     * content}.
     */
    fun saveFile(fileId: String, name: String, content: String): Task<Unit> {
        return Tasks.call(executor, {
            // Create a File containing any metadata changes.
            val metadata = File().setName(name)

            // Convert content to an AbstractInputStreamContent instance.
            val contentStream = ByteArrayContent.fromString(EXPORT_TYPE_PLAIN_TEXT, content)

            // Update the metadata and contents.
            driveService.files().update(fileId, metadata, contentStream).execute()

        })
    }

    /**
     * Returns a {@link FileList} containing all the visible files in the user's My Drive.
     *
     * <p>The returned list will only contain files visible to this app, i.e. those which were
     * created by this app. To perform operations on files not created by the app, the project must
     * request Drive Full Scope in the <a href="https://play.google.com/apps/publish">Google
     * Developer's Console</a> and be submitted to Google for verification.</p>
     */
    fun queryFiles(): Task<List<GoogleDriveFileHolder>> {
        return Tasks.call(
            executor, {
                val googleDriveFolderList = ArrayList<GoogleDriveFileHolder>()
                val result = driveService.files().list()
                    .setQ("sharedWithMe = true")
                    .setFields("files(id, name, size, createdTime, modifiedTime, starred, mimeType)")
                    .setSpaces("drive").execute()
                result.files.forEach {
                    googleDriveFolderList.add(
                        GoogleDriveFileHolder(
                            it.id,
                            it.name,
                            it.createdTime,
                            it.size,
                            it.modifiedTime,
                            it.starred,
                            it.mimeType
                        )
                    )
                }
                googleDriveFolderList
            }
        )
    }

    fun queryFiles(folderId: String = "root"): Task<List<GoogleDriveFileHolder>> {
        return Tasks.call(executor, {
            val googleDriveFolderList = ArrayList<GoogleDriveFileHolder>()
            val result = driveService.files().list().setQ("'$folderId' in parents")
                .setFields("files(id, name, size, createdTime, modifiedTime, starred, mimeType)")
                .setSpaces("drive").execute()
            result.files.forEach {
                googleDriveFolderList.add(
                    GoogleDriveFileHolder(
                        it.id,
                        it.name,
                        it.createdTime,
                        it.size,
                        it.modifiedTime,
                        it.starred,
                        it.mimeType
                    )
                )
            }
            googleDriveFolderList
        })
    }

    /**
     * Returns an {@link Intent} for opening the Storage Access Framework file picker.
     */
    fun createFilePickerIntent(): Intent {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = EXPORT_TYPE_PLAIN_TEXT
        return intent
    }

    /**
     * Opens the file at the {@code uri} returned by a Storage Access Framework {@link Intent}
     * created by {@link #createFilePickerIntent()} using the given {@code contentResolver}.
     */
    fun openFileUsingStorageAccessFramework(
        contentResolver: ContentResolver, uri: Uri
    ): Task<Pair<String, String>> {
        return Tasks.call(executor, {
            // Retrieve the document's display name from its metadata.
            val cursor = contentResolver.query(uri, null, null, null, null)
            val name = if (cursor != null && cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.getString(nameIndex)
            } else {
                throw IOException("Empty cursor returned for file")
            }

            // Read the document's contents as a String.
            val inS = contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inS))
            val stringBuilder = java.lang.StringBuilder()
            reader.forEachLine {
                stringBuilder.append(it)
            }
            Pair(name, stringBuilder.toString())
        })
    }
}

data class GoogleDriveFileHolder(
    val id: String,
    val name: String,
    val modifiedTime: DateTime,
    val size: Int,
    val createdTime: DateTime,
    val starred: Boolean,
    val mimeType: String,
)
