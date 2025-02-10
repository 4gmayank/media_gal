//import android.content.ContentResolver
//import android.provider.MediaStore
//import android.database.Cursor
//import android.net.Uri
//import android.widget.GridView
//
//fun displayImagesInGrid(imageList: List<String>) {
//    val gridView: GridView = findViewById(R.id.gridView) // GridView ID from XML layout
//    val adapter = ImageAdapter(this, imageList)
//    gridView.adapter = adapter
//}
//
//fun fetchImages() {
//    val imageList = mutableListOf<String>()
//    val projection = arrayOf(MediaStore.Images.Media.DATA) // Path to image
//    val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//
//    val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
//
//    cursor?.use {
//        val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//        while (it.moveToNext()) {
//            val imagePath = it.getString(columnIndex)
//            imageList.add(imagePath)
//        }
//    }
//
//    // Now we have all image paths in imageList, display them in GridView
//    displayImagesInGrid(imageList)
//}
