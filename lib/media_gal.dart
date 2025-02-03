import 'package:media_gal/media_file.dart';

import 'media_gal_platform_interface.dart';

class MediaGal {
  Future<String?> getPlatformVersion() {
    return MediaGalPlatform.instance.getPlatformVersion();
  }

  static Future<void> showToast(String message) async {
    await MediaFile.showToast(message);
  }

  static Future<List<Object?>?> loadMediaList() async {
    return await MediaFile.loadMediaList();
  }
}
