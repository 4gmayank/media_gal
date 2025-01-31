
import 'media_gal_platform_interface.dart';

class MediaGal {
  Future<String?> getPlatformVersion() {
    return MediaGalPlatform.instance.getPlatformVersion();
  }
  static const platform = MethodChannel('com.example.media'); // Channel name should match with native code

  static Future<void> showToast() async {
    try {

      await platform.invokeMethod('showToast', {'message': 'Brother, Click Attachment send Media!'});
    } on PlatformException catch (e) {
      print("Failed to show toast: ${e.message}");
    }
  }

    static Future<List<Object?>> loadMediaList() async {
    try {

      var files = await platform.invokeMethod('gallery', {'message': 'Brother, Click Attachment send Media!'});
      print(files.toString());
      print(files.length);
      return files;
    } on PlatformException catch (e) {
      print("Failed to show toast: ${e.message}");
    }
    return [];
  }

    Future<void> _loadAssets() async {
    // Request permissions for photos
    await Permission.photos.request();
    List<Object?> path1 = await Config.loadMediaList();

    for (var test in path1) {
      if(test is String?){
        if(test != null && test.isNotEmpty){
          this.path.add(test+test);
        }
      }
    }
    }
}
