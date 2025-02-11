import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:media_gal/media_gal.dart';
import 'package:flutter/material.dart';
import 'package:media_gal/media_gal.dart';
import 'package:media_gal/media_model.dart';  // Replace with actual import

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Media Gallery Example',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MediaGalScreen(),
    );
  }
}

class MediaGalScreen extends StatefulWidget {
  @override
  State<MediaGalScreen> createState() => _MediaGalScreenState();
}

class _MediaGalScreenState extends State<MediaGalScreen> {
  List<MediaModel> _mediaFiles = List.empty(growable: true);

  @override
  void initState() {
    super.initState();
    _loadImages();
  }

  Future<void> _loadImages() async {
    try {
      // Assuming getImagesFromGallery is a method in the package
      List<Object?>? images = await MediaGal.loadMediaList();
      for (var mediaPathNam in images??[]) {
        if (mediaPathNam is String?) {
          if (mediaPathNam != null && mediaPathNam.isNotEmpty) {
            this._mediaFiles.add(MediaModel.filePath(mediaPathNam));
          }
        }
      }
      setState(() {

      });
    } catch (e) {
      print("Error fetching images: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("Media Gallery")),
      body: _mediaFiles.isEmpty
          ? Center(child: CircularProgressIndicator())
          : Expanded(
        child: _mediaList(_mediaFiles),
      ),
    );
  }

  Widget _mediaList(List<MediaModel> mediaList) {
    return ListView.builder(
      scrollDirection: Axis.horizontal,
      itemCount: mediaList.length, // Number of items in the list
      itemBuilder: (context, index) {
        return _mediaViewCard(mediaList[index]);
      },
    );
  }



  Widget _mediaViewCard(MediaModel media) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 2.0, vertical: 2),
      child: ConstrainedBox(constraints: BoxConstraints(
        maxWidth: MediaQuery.of(context).size.width,),
        child: Container(
          child: Card(
            elevation: 4.0,
            child: media.image(),
          ),
        ),
      ),
    );
  }
}