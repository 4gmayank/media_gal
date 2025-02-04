import 'dart:io';


import 'package:path_provider/path_provider.dart';

class CacheUtil {
  static Future<List<File>> getImageFilesFromCache() async {
    final List<FileSystemEntity> cacheFiles =
        (await getCacheDirectory()).listSync(); // List all files

    // Filter files to only include images
    final imageFiles = cacheFiles
        .where((file) => file is File && isImageFile(file) && isImage(file))
        .map((file) => file as File)
        .toList();

    return imageFiles;
  }

  static bool isImage(File file){
    return (file.existsSync()&& file.lengthSync() > 10);
  }

  static bool isImageFile(File file) =>
      file.path.endsWith('.jpg') ||
      file.path.endsWith('.jpeg') ||
      file.path.endsWith('.png');

  static Future<Directory> getCacheDirectory() async {
    final directory = await getTemporaryDirectory(); // Get the cache directory
    final cacheDirectoryPath = directory.path;
    print('CacheUtil.getCacheDirectory: $cacheDirectoryPath');
    final cacheDirectory = Directory(cacheDirectoryPath);
    return cacheDirectory;
  }
}
