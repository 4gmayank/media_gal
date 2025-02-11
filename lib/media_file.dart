import 'package:flutter/services.dart';

class MediaFile {
  static const platform = MethodChannel(
      'com.example.media'); // Channel name should match with native code

  static Future<void> showToast(String message) async {
    try {
      await platform.invokeMethod('showToast', {'message': message});
    } on PlatformException catch (e) {
      print("Failed to show toast: ${e.message}");
    }
  }

  static Future<List<MediaDetails?>> loadMediaList() async {
    try {
      List<MediaDetails> imageDetails = List.empty(growable: true);
      var files = await platform.invokeMethod(
          'gallery', {'message': 'Brother, Click Attachment send Media!'});
      print(files.toString());
      print(files.length);
      for (var file in files) {
        imageDetails.add(MediaDetails.fromMap(file));
      }
      return imageDetails;
    } on PlatformException catch (e) {
      print("Failed to show toast: ${e.message}");
    }
    return [];
  }

  static Future<List<MediaDetails?>> loadVideoMediaList() async {
    try {
      List<MediaDetails> imageDetails = List.empty(growable: true);
      var files = await platform.invokeMethod(
          'video_gallery', {'message': 'Brother, Click Attachment send Media!'});
      print(files.toString());
      print(files.length);
      for (var file in files) {
        imageDetails.add(MediaDetails.fromMap(file));
      }
      return imageDetails;
    } on PlatformException catch (e) {
      print("Failed to show toast: ${e.message}");
    }
    return [];
  }
}

class MediaDetails {
  final int duration;
  final String title;
  final int orientation;
  final String path;
  final int width;
  final int height;
  final String mimeType;
  final int dateTaken;
  final String displayName;
  final int size;

  MediaDetails({
    required this.duration,
    required this.title,
    required this.orientation,
    required this.path,
    required this.width,
    required this.height,
    required this.mimeType,
    required this.dateTaken,
    required this.displayName,
    required this.size,
  });

  factory MediaDetails.fromMap(Map<dynamic, dynamic> map) {
    return MediaDetails(
      duration: map['duration'],
      title: map['title'],
      orientation: map['orientation'],
      path: map['path'],
      width: map['width'],
      height: map['height'],
      mimeType: map['mimeType'],
      dateTaken: map['dateTaken'],
      displayName: map['displayName'],
      size: map['size'],
    );
  }

  @override
  String toString() {
    return 'ImageDetails(title: $title, path: $path, size: $size, width: $width, height: $height, dateTaken: $dateTaken, mimeType: $mimeType, orientation: $orientation, displayName: $displayName)';
  }

  String filePath(){
    return path;
  }
}
