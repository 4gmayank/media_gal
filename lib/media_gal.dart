import 'package:media_gal/media_file.dart';
import 'media_gal_platform_interface.dart';

class MediaGal {

    // List<MediaModel> _mediaFiles = List.empty(growable: true);

  Future<String?> getPlatformVersion() {
    return MediaGalPlatform.instance.getPlatformVersion();
  }

  static Future<void> showToast(String message) async {
    await MediaFile.showToast(message);
  }

  static Future<List<MediaDetails?>?> loadMediaList() async {
    return await MediaFile.loadMediaList();
  }

  //   Future<void> _loadAssets() async {
  //   // Request permissions for photos
  //   //   PermissionHndl.galleryPermission();
  //   List<MediaDetails?>? path1 = await MediaGal.loadMediaList();
  //
  //
  //   for (MediaDetails? mediaPathNam in path1 ?? []) {
  //     if (mediaPathNam != null && (mediaPathNam?.path??'').isNotEmpty) {
  //       // this._mediaFiles.add(MediaFileAttachmentModel.filePath(mediaPathNam.path));
  //     }
  //
  //   }
  // }
}
