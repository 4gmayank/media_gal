import 'package:flutter_test/flutter_test.dart';
import 'package:media_gal/media_gal.dart';
import 'package:media_gal/media_gal_platform_interface.dart';
import 'package:media_gal/media_gal_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockMediaGalPlatform
    with MockPlatformInterfaceMixin
    implements MediaGalPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final MediaGalPlatform initialPlatform = MediaGalPlatform.instance;

  test('$MethodChannelMediaGal is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelMediaGal>());
  });

  test('getPlatformVersion', () async {
    MediaGal mediaGalPlugin = MediaGal();
    MockMediaGalPlatform fakePlatform = MockMediaGalPlatform();
    MediaGalPlatform.instance = fakePlatform;

    expect(await mediaGalPlugin.getPlatformVersion(), '42');
  });
}
