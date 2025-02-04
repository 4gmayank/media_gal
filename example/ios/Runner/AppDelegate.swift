import UIKit
import Flutter
import Photos
import Toast_Swift  // Add this dependency to show toast messages

@UIApplicationMain
@main
@objc class AppDelegate: FlutterAppDelegate {
  
    private let CHANNEL = "com.example.media" // Define the same channel name
  
    override func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
    //     GeneratedPluginRegistrant.register(with: self)
    // return super.application(application, didFinishLaunchingWithOptions: launchOptions)
        let controller = window?.rootViewController as! FlutterViewController
        let channel = FlutterMethodChannel(name: CHANNEL, binaryMessenger: controller.binaryMessenger)
        
        channel.setMethodCallHandler { (call: FlutterMethodCall, result: @escaping FlutterResult) in
            if call.method == "gallery" {
                self.fetchImages(result: result)
            } else if call.method == "showToast" {
                if let args = call.arguments as? [String: Any],
                   let message = args["message"] as? String {
                    self.showToast(message: message)
                    result(nil)
                } else {
                    result(FlutterError(code: "ERROR", message: "Invalid arguments", details: nil))
                }
            } else {
                result(FlutterMethodNotImplemented)
            }
        }
        return super.application(application, didFinishLaunchingWithOptions: launchOptions)
    }
  
    private func fetchImages(result: @escaping FlutterResult) {
        // Check for photo library permissions
        PHPhotoLibrary.requestAuthorization { status in
            if status == .authorized {
                // Fetch all images
                let fetchOptions = PHFetchOptions()
                let assets = PHAsset.fetchAssets(with: .image, options: fetchOptions)
                
                var imagePaths: [String] = []
                assets.enumerateObjects { (asset, _, _) in
                    let manager = PHImageManager.default()
                    manager.requestImageData(for: asset, options: nil) { (data, _, _, _) in
                        if let data = data, let fileURL = asset.localIdentifier {
                            imagePaths.append(fileURL)
                        }
                    }
                }
                // Return the list of image paths
                result(imagePaths)
            } else {
                result(FlutterError(code: "PERMISSION_DENIED", message: "Permission denied", details: nil))
            }
        }
    }
  
    private func showToast(message: String) {
        DispatchQueue.main.async {
            if let rootViewController = self.window?.rootViewController {
                rootViewController.view.makeToast(message)
            }
        }
    }
}
