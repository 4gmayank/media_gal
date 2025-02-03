

import 'package:flutter/services.dart';

class MediaFile{
  static const platform = MethodChannel('com.example.media'); // Channel name should match with native code

  static Future<void> showToast(String message) async {
    try {

      await platform.invokeMethod('showToast', {'message': message});
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

}