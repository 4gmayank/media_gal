
import 'dart:io';

import 'package:flutter/material.dart';

class MediaModel {
  File _file;
  String? _fileName;
  String _filePath;
  String? preview;
  bool isImage = false;
  bool isSelected = false;
  MediaModel.filePath(this._filePath):_file =File(_filePath) {
    this.isImage = true;
  }
  @Deprecated("use file path")
  MediaModel(this._file, this._fileName, this._filePath, this.isImage){
    // /data/user/0/_$package_name_/cache/e3aacd28-bcb8-43b3-8492-302c48d6476086457922708461484.jpg
    print('MediaFile.MediaFile $_filePath');
  }


  File file() => _file?? File("");

  Uri uri() => _file.uri;

  Future<void> loadImage(String imagePath) async {
    try {
      final file = File(imagePath);

      // Check if the file exists and is not empty
      if (await file.exists() && await file.length() > 0) {
        // Proceed with loading the image
        final image = FileImage(file);
        // Now use the image in your widget
      } else {
        // Handle the case where the image is empty or doesn't exist
        print('Image file is empty or does not exist.');
      }
    } catch (e) {
      // Handle any other exceptions (e.g., file read errors)
      print('Error loading image: $e');
    }
  }


  Widget image() {
    Widget? widget;
    try {

      widget  = Image.file(
        _file,
        // Loading image from file path
        fit: BoxFit.fitHeight,
      );
    }catch(e){

    }
    return widget?? Container(
      height: 100,
      width: 100,
      color: Colors.red,
    );
  }
}
