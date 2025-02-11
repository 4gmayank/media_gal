import 'package:device_info_plus/device_info_plus.dart';
import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';
import 'dart:developer';

import 'package:flutter/material.dart';

import 'package:permission_handler/permission_handler.dart';

class PermissionHndl {
  final iOSLocalizedLabels = false;




  static Future<bool> galleryPermission() async {
    TargetPlatform defaultTargetPlatform = TargetPlatform.android;

    final DeviceInfoPlugin deviceInfoPlugin = DeviceInfoPlugin();
    AndroidDeviceInfo androidInfo = await deviceInfoPlugin.androidInfo;
    int sdk = androidInfo.version.sdkInt;
    Permission permsi = sdk >= 33 ? Permission.photos : Permission.storage;
    PermissionStatus status =
    await (permsi).request();
    if (status == PermissionStatus.denied) {
      PermissionStatus permissionStatus = await permsi.request();
      SnackBar(content: Text('Contact data not available on device'));
      openAppSettings();
      return false;
    }
    if (status.isPermanentlyDenied) {
      // You can open app settings to allow the user to enable permission manually
      openAppSettings();
      SnackBar(content: Text('Contact data not available on device'));
      return false; // Indicating permission cannot be granted programmatically anymore
    }

    return status.isGranted;
  }

  static Future<bool> storagePermission() async {
    PermissionStatus status = await Permission.storage.request();
    // await Permission.mediaLibrary.request();
    return status.isGranted;
  }
}
