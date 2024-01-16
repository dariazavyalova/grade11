import 'package:flutter_test/flutter_test.dart';
import 'package:network_info/network_info.dart';
import 'package:network_info/network_info_platform_interface.dart';
import 'package:network_info/network_info_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockNetworkInfoPlatform
    with MockPlatformInterfaceMixin
    implements NetworkInfoPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final NetworkInfoPlatform initialPlatform = NetworkInfoPlatform.instance;

  test('$MethodChannelNetworkInfo is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelNetworkInfo>());
  });

  test('getPlatformVersion', () async {
    NetworkInfo networkInfoPlugin = NetworkInfo();
    MockNetworkInfoPlatform fakePlatform = MockNetworkInfoPlatform();
    NetworkInfoPlatform.instance = fakePlatform;

    expect(await networkInfoPlugin.getPlatformVersion(), '42');
  });
}
