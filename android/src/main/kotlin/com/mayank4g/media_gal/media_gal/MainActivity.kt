package com.demo.bottomfeat.bottom_feature_app

import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.os.Build

class MainActivity : FlutterActivity() {
    private val REQUEST_CODE_STORAGE_PERMISSION = 100

    private val CHANNEL = "com.example.media"  // Define a channel name
    private var toast: Toast? = null  // Keep a reference to the Toast object

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        Log.d("TAG", "This is a debug message")
        // Create a MethodChannel to handle the toast
        flutterEngine.dartExecutor.let {
            MethodChannel(it, CHANNEL).setMethodCallHandler { call, result ->
                if (call.method == "video_gallery") {
                    if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) && ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        // Request permission
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_CODE_STORAGE_PERMISSION
                        )
                    } else {

                        result.success(fetchVideos())

                    }

                } else if (call.method == "gallery") {
                        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) && ContextCompat.checkSelfPermission(
                                this,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                            != PackageManager.PERMISSION_GRANTED
                        ) {
                            // Request permission
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                                REQUEST_CODE_STORAGE_PERMISSION
                            )
                        } else {

                            result.success(fetchImages())
                            // If permission is granted, fetch images
//                        val imageList = mutableListOf<String>()
//                        val projection = arrayOf(MediaStore.Images.Media.DATA)
//                        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
//       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                        cursor?.use {
//                            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//                            while (it.moveToNext()) {
//                                val imagePath = it.getString(columnIndex)
//                                imageList.add(imagePath)
//                            }
//                            result.success(imageList)
//                        }

                        }
//                    val message =
//                        call.argument<String>("message")  // Get the message from the Flutter side
//                    message?.let {
//                        // Show toast message on Android
//                        Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
//                        result.success(null)  // Return success to Flutter side
//                    }
                        Log.d("TAG", "This is a debug message")
                    } else if (call.method == "showToast") {
                        toast?.cancel()
                        val message =
                            call.argument<String>("message")  // Get the message from the Flutter side
                        message?.let {
                            toast = Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT)
                            toast?.show()
                            result.success(null)  // Return success to Flutter side
                        }
                    } else {
                        Log.d("TAG", "This is a debug message")
                        result.notImplemented()  // If the method is not implemented
                    }
            }
        }
    }

    private fun fetchImages(): MutableList<Map<String, Any>> {
        val imageList = mutableListOf<Map<String, Any>>()
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media.DURATION,               // For duration of the media
            MediaStore.Images.Media.TITLE,                  // For title
            MediaStore.Images.Media.ORIENTATION,            // For orientation
            MediaStore.Images.Media.DATA,                   // For the file path
            MediaStore.Images.Media.WIDTH,                  // For image width
            MediaStore.Images.Media.HEIGHT,                 // For image height
            MediaStore.Images.Media.MIME_TYPE,              // For mime type
            MediaStore.Images.Media.DATE_TAKEN,             // For date taken
            MediaStore.Images.Media.DISPLAY_NAME,           // For display name
            MediaStore.Images.Media.SIZE                    // For file size
        )

        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            val durationIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DURATION)
            val titleIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)
            val orientationIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION)
            val pathIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val widthIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
            val heightIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)
            val mimeTypeIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
            val dateTakenIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
            val displayNameIndex =
                it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (it.moveToNext()) {
                val duration = it.getLong(durationIndex) // Duration of the media
                val title = it.getString(titleIndex) // Title of the image or video
                val orientation = it.getInt(orientationIndex) // Orientation of the media
                val imagePath = it.getString(pathIndex) // Path to the image or video file
                val width = it.getInt(widthIndex) // Width of the image
                val height = it.getInt(heightIndex) // Height of the image
                val mimeType =
                    it.getString(mimeTypeIndex) // Mime type (image/jpeg, video/mp4, etc.)
                val dateTaken = it.getLong(dateTakenIndex) // Date the media was taken
                val displayName = it.getString(displayNameIndex) // Display name of the media
                val size = it.getLong(sizeIndex) // File size in bytes

                // Optionally, you can create a custom object to hold this data
                val imageDetails = ImageDetails(
                    duration, title, orientation, imagePath, width, height, mimeType,
                    dateTaken, displayName, size
                )
                imageList.add(imageDetails.toMap())
            }
        }

        return imageList;
    }


    private fun fetchVideos(): MutableList<Map<String, Any>> {
        val imageList = mutableListOf<Map<String, Any>>()
        val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Video.Media.DURATION,               // For duration of the media
            MediaStore.Video.Media.TITLE,                  // For title
            MediaStore.Video.Media.ORIENTATION,            // For orientation
            MediaStore.Video.Media.DATA,                   // For the file path
            MediaStore.Video.Media.WIDTH,                  // For image width
            MediaStore.Video.Media.HEIGHT,                 // For image height
            MediaStore.Video.Media.MIME_TYPE,              // For mime type
            MediaStore.Video.Media.DATE_TAKEN,             // For date taken
            MediaStore.Video.Media.DISPLAY_NAME,           // For display name
            MediaStore.Video.Media.SIZE                    // For file size
        )

        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            val durationIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val titleIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
            val orientationIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.ORIENTATION)
            val pathIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val widthIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH)
            val heightIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT)
            val mimeTypeIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)
            val dateTakenIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN)
            val displayNameIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val sizeIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            while (it.moveToNext()) {
                val duration = it.getLong(durationIndex) // Duration of the media
                val title = it.getString(titleIndex) // Title of the image or video
                val orientation = it.getInt(orientationIndex) // Orientation of the media
                val imagePath = it.getString(pathIndex) // Path to the image or video file
                val width = it.getInt(widthIndex) // Width of the image
                val height = it.getInt(heightIndex) // Height of the image
                val mimeType =
                    it.getString(mimeTypeIndex) // Mime type (image/jpeg, video/mp4, etc.)
                val dateTaken = it.getLong(dateTakenIndex) // Date the media was taken
                val displayName = it.getString(displayNameIndex) // Display name of the media
                val size = it.getLong(sizeIndex) // File size in bytes

                // Optionally, you can create a custom object to hold this data
                val imageDetails = ImageDetails(
                    duration, title, orientation, imagePath, width, height, mimeType,
                    dateTaken, displayName, size
                )
                imageList.add(imageDetails.toMap())
            }
        }


        return imageList;
    }

}

data class ImageDetails(
    val duration: Long,
    val title: String?,
    val orientation: Int,
    val path: String,
    val width: Int,
    val height: Int,
    val mimeType: String?,
    val dateTaken: Long,
    val displayName: String?,
    val size: Long
) {
    // Returns a human-readable representation of the image details
    override fun toString(): String {
        return "ImageDetails(title=$title, path=$path, size=$size, width=$width, height=$height, dateTaken=$dateTaken, mimeType=$mimeType, orientation=$orientation, displayName=$displayName)"
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "duration" to duration,
            "title" to (title ?: ""),
            "orientation" to orientation,
            "path" to path,
            "width" to width,
            "height" to height,
            "mimeType" to (mimeType ?: ""),
            "dateTaken" to dateTaken,
            "displayName" to (displayName ?: ""),
            "size" to size
        )
    }

    // Custom equals method if you want specific equality checks (this is actually done by `data class` by default)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as ImageDetails

        if (duration != other.duration) return false
        if (title != other.title) return false
        if (orientation != other.orientation) return false
        if (path != other.path) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (mimeType != other.mimeType) return false
        if (dateTaken != other.dateTaken) return false
        if (displayName != other.displayName) return false
        return size == other.size
    }

    // Custom hashCode function if you want to ensure the hash code is consistent with `equals`
    override fun hashCode(): Int {
        var result = duration.hashCode()
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + orientation
        result = 31 * result + path.hashCode()
        result = 31 * result + width
        result = 31 * result + height
        result = 31 * result + (mimeType?.hashCode() ?: 0)
        result = 31 * result + dateTaken.hashCode()
        result = 31 * result + (displayName?.hashCode() ?: 0)
        result = 31 * result + size.hashCode()
        return result
    }

    // Returns the aspect ratio of the image (width / height)
    fun getAspectRatio(): Float {
        return if (height > 0) width.toFloat() / height else 0f
    }

    // Returns the file size in a human-readable format (e.g., KB, MB, GB)
    fun getFileSizeReadable(): String {
        val sizeInKB = size / 1024
        val sizeInMB = sizeInKB / 1024
        val sizeInGB = sizeInMB / 1024

        return when {
            sizeInGB > 1 -> String.format("%.2f GB", sizeInGB)
            sizeInMB > 1 -> String.format("%.2f MB", sizeInMB)
            sizeInKB > 1 -> String.format("%.2f KB", sizeInKB)
            else -> "$size B"
        }
    }

    // Returns true if the image is considered high resolution (e.g., width > 1080 and height > 1080)
    fun isHighResolution(): Boolean {
        return width > 1080 && height > 1080
    }

    // Check if the image is a video based on MIME type (you can extend this as needed)
    fun isVideo(): Boolean {
        return mimeType?.startsWith("video/") == true
    }

    // Returns the formatted date the image was taken (in a readable string format)
    fun getFormattedDate(): String {
        val dateFormat =
            java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
        return dateFormat.format(java.util.Date(dateTaken))
    }
}