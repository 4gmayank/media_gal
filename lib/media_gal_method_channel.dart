import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'media_gal_platform_interface.dart';

/// An implementation of [MediaGalPlatform] that uses method channels.
class MethodChannelMediaGal extends MediaGalPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('media_gal');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
