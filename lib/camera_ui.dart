import 'package:flutter/material.dart';
import 'package:media_gal/media_model';

class CameraUi extends StatelessWidget {
  const CameraUi({super.key});

  @override
  Widget build(BuildContext context) {
    List<MediaModel> cameraClickList = List.empty(growable: true);
    return  ListView.builder(
      // controller: _scrollController,
      scrollDirection: Axis.horizontal,
      itemCount: cameraClickList.length~/1,
      // Number of items in the list
      itemBuilder: (context, index) {
        if(index == 0)
          return    Padding(
            padding: const EdgeInsets.all(8.0),
            child: ElevatedButton(
              onPressed: _pickImageFile,
              child: Icon(Icons.camera_alt_rounded),
            ),
          );

        return Padding(
          padding: const EdgeInsets.all(8.0),
          child: _imagePreview(cameraClickList[ cameraClickList.length-index-1], index),
        );
      },
    ),
  }

  Widget _imagePreview(MediaModel imageFile, int index) {
    Widget widget = Container(
      height: 100,
      width: 100,
      color: Colors.red,
    );
    try {
      widget = imageFile.image();
    } catch (e) {}
    return Center(
      child: GestureDetector(
        onTap: () {
          imageFile.isSelected = !imageFile.isSelected;
        },
        child: Stack(
          children: [
            widget,
            Text("$index"),
            Positioned(
                bottom: 10,
                right: 10,
                child: Icon(
                    imageFile.isSelected ? Icons.check_circle : Icons.circle,
                    color: imageFile.isSelected ? Colors.blue : Colors.white)),
          ],
        ),
      ),
    ); // Show image preview
  }
}
