package com.example.galleryview

import android.content.Context
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.galleryview.databinding.ActivityMainBinding

// Inside MainActivity.kt
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import io.flutter.embedding.android.FlutterActivity
//import io.flutter.plugin.common.MethodChannel
//import com.example.flutterwithcompose.ui.theme.FlutterWithComposeTheme


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_STORAGE_PERMISSION = 100
    //    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchImages()
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE_PERMISSION)
        } else {
            // If permission is granted, fetch images
            fetchImages()
        }

        // Use Jetpack Compose to display the UI
//        setContent {
//            FlutterWithComposeTheme {
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    // Button in Compose UI
//                    Button(onClick = {
//                        // Trigger native method call from Compose
//                        MethodChannel(flutterEngine?.dartExecutor, CHANNEL).invokeMethod("showToast", mapOf("message" to "Hello from Jetpack Compose!"))
//                    }) {
//                        Text("Show Toast from Compose")
//                    }
//                }
//            }
//        }
    }
    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }


    // Handle permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchImages()
            }
        }
    }

    private fun fetchImages() {
        val imageList = mutableListOf<ImageDetails>()
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
            val displayNameIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (it.moveToNext()) {
                val duration = it.getLong(durationIndex) // Duration of the media
                val title = it.getString(titleIndex) // Title of the image or video
                val orientation = it.getInt(orientationIndex) // Orientation of the media
                val imagePath = it.getString(pathIndex) // Path to the image or video file
                val width = it.getInt(widthIndex) // Width of the image
                val height = it.getInt(heightIndex) // Height of the image
                val mimeType = it.getString(mimeTypeIndex) // Mime type (image/jpeg, video/mp4, etc.)
                val dateTaken = it.getLong(dateTakenIndex) // Date the media was taken
                val displayName = it.getString(displayNameIndex) // Display name of the media
                val size = it.getLong(sizeIndex) // File size in bytes

                // Optionally, you can create a custom object to hold this data
                val imageDetails = ImageDetails(
                    duration, title, orientation, imagePath, width, height, mimeType,
                    dateTaken, displayName, size
                )
                imageList.add(imageDetails)
            }
        }


        displayImagesInGrid(imageList)
    }


    private fun fetchVideos() {
        val imageList = mutableListOf<ImageDetails>()
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
                val mimeType = it.getString(mimeTypeIndex) // Mime type (image/jpeg, video/mp4, etc.)
                val dateTaken = it.getLong(dateTakenIndex) // Date the media was taken
                val displayName = it.getString(displayNameIndex) // Display name of the media
                val size = it.getLong(sizeIndex) // File size in bytes

                // Optionally, you can create a custom object to hold this data
                val imageDetails = ImageDetails(
                    duration, title, orientation, imagePath, width, height, mimeType,
                    dateTaken, displayName, size
                )
                imageList.add(imageDetails)
            }
        }


        displayImagesInGrid(imageList)
    }


    private fun displayImagesInGrid(imageList: List<ImageDetails>) {
        val gridView: GridView = findViewById(R.id.gridView)
        val adapter = ImageAdapter(this, imageList)
        gridView.adapter = adapter
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
        val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
        return dateFormat.format(java.util.Date(dateTaken))
    }
}

class ImageAdapter(private val context: Context, private val imageList: List<ImageDetails>) : BaseAdapter() {

    override fun getCount(): Int = imageList.size

    override fun getItem(position: Int): Any = imageList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            imageView = inflater.inflate(R.layout.simple_gallery_item, parent, false) as ImageView
        } else {
            imageView = convertView as ImageView
        }

        Picasso.get().load("file://${imageList[position].path}").into(imageView)

        return imageView
    }
}

//    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setSupportActionBar(binding.toolbar)
//
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .setAnchorView(R.id.fab).show()
//        }
//    }
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    FlutterWithComposeTheme {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text("Jetpack Compose UI")
//        }
//    }
//}



