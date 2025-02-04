import 'package:media_gal/media_file.dart';
import 'package:media_gal/media_model';
import 'package:permission_handler/permission_handler.dart';

import 'media_gal_platform_interface.dart';

class MediaGal {

    List<MediaModel> _mediaFiles = List.empty(growable: true);

  Future<String?> getPlatformVersion() {
    return MediaGalPlatform.instance.getPlatformVersion();
  }

  static Future<void> showToast(String message) async {
    await MediaFile.showToast(message);
  }

  static Future<List<Object?>?> loadMediaList() async {
    return await MediaFile.loadMediaList();
  }

    Future<void> _loadAssets() async {
    // Request permissions for photos
    await Permission.photos.request();
    List<Object?>? path1 = await MediaGal.loadMediaList();
    // List<Object?> path1 = await Config.loadMediaList();


    for (var mediaPathNam in path1??[]) {
      if (mediaPathNam is String?) {
        if (mediaPathNam != null && mediaPathNam.isNotEmpty) {
          _mediaFiles.add(MediaModel.filePath(mediaPathNam));
        }
      }
    }
  }
}
