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
        val imageList = mutableListOf<String>()
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (it.moveToNext()) {
                val imagePath = it.getString(columnIndex)
                imageList.add(imagePath)
            }
        }

        displayImagesInGrid(imageList)
    }

    private fun displayImagesInGrid(imageList: List<String>) {
        val gridView: GridView = findViewById(R.id.gridView)
        val adapter = ImageAdapter(this, imageList)
        gridView.adapter = adapter
    }
}

class ImageAdapter(private val context: Context, private val imageList: List<String>) : BaseAdapter() {

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

        Picasso.get().load("file://${imageList[position]}").into(imageView)

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



