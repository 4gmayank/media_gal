import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageAdapter(private val context: Context, private val imageList: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return imageList.size
    }

    override fun getItem(position: Int): Any {
        return imageList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            imageView = inflater.inflate(android.R.layout.simple_gallery_item, parent, false) as ImageView
        } else {
            imageView = convertView as ImageView
        }

        // Load the image into ImageView using Picasso or Glide
        Picasso.get().load("file://${imageList[position]}").into(imageView)

        return imageView
    }
}
