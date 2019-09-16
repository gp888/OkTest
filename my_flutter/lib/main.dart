/// runApp 内部值也可以直接传入 _buildWidgetForNativeRoute 方法
/// 这边在外层嵌套一层 MaterialApp 主要是防止一些不必要的麻烦，
/// 例如 MediaQuery 这方面的使用等
void main() => runApp(FlutterApp());

class FlutterApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: _buildWidgetForNativeRoute(window.defaultRouteName),
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primaryColor: Color(0XFF008577),
        accentColor: Color(0xFFD81B60),
        primaryColorDark: Color(0xFF00574B),
        iconTheme: IconThemeData(color: Color(0xFFD81B60)),
      ),
    );
  }
}

/// 该方法用于判断原生界面传递过来的路由值，加载不同的页面
Widget _buildWidgetForNativeRoute(String route) {
  switch (route) {
    case 'route1':
      return GreetFlutterPage();
  // 默认的路由值为 '/'，所以在 default 情况也需要返回页面，否则 dart 会报错，这里默认返回空页面
    default:
      return Scaffold();
  }
}

class GreetFlutterPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('NativeMessageContactPage'),
      ),
      body: Center(
        child: Text(
          'This is a flutter fragment page',
          style: TextStyle(fontSize: 20.0, color: Colors.black),
        ),
      ),
    );
  }
}