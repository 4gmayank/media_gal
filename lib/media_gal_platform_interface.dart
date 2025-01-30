import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'media_gal_method_channel.dart';

abstract class MediaGalPlatform extends PlatformInterface {
  /// Constructs a MediaGalPlatform.
  MediaGalPlatform() : super(token: _token);

  static final Object _token = Object();

  static MediaGalPlatform _instance = MethodChannelMediaGal();

  /// The default instance of [MediaGalPlatform] to use.
  ///
  /// Defaults to [MethodChannelMediaGal].
  static MediaGalPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [MediaGalPlatform] when
  /// they register themselves.
  static set instance(MediaGalPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
