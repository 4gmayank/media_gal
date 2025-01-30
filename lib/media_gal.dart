
import 'media_gal_platform_interface.dart';

class MediaGal {
  Future<String?> getPlatformVersion() {
    return MediaGalPlatform.instance.getPlatformVersion();
  }
}
