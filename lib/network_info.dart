import 'network_info_platform_interface.dart';

class NetworkInfo {
  Future<String?> getPlatformVersion() {
    return NetworkInfoPlatform.instance.getPlatformVersion();
  }

  Future<String?> getNetworkType() {
    return NetworkInfoPlatform.instance.getNetworkType();
  }

  Stream<String> networkType() => NetworkInfoPlatform.instance.networkType();
}
