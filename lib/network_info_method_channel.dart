import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'network_info_platform_interface.dart';

/// An implementation of [NetworkInfoPlatform] that uses method channels.
class MethodChannelNetworkInfo extends NetworkInfoPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('network_info');

  final eventChannel = const EventChannel('network_info_listener');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> getNetworkType() async {
    final type = await methodChannel.invokeMethod<String>('getNetworkType');
    return type;
  }

  Stream<String> networkType() => eventChannel.receiveBroadcastStream().cast<String>();
}
