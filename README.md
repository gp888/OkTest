# OkTest
andrid api test


获取Android应用签名MD5/sha1/SHA256等证书指纹
keytool -v -list -keystore  rainy.jks路径

应用key hash 散列值
 keytool -exportcert -alias rainy -keystore rainy.jks | openssl sha1 -binary | openssl base64

生成keystore
keytool -genkey -keystore test.keystore  -alias test -keyalg RSA -validity 10000

keytool -genkey -alias StudyInUK -keypass 987654 -keyalg RSA -keysize 2048 -validity 36500 -keystore /Users/kaichi/AndroidStudioProjects/StudyInUK/app/student1.jks -storepass 987654
keytool -importkeystore -srckeystore H:\hobby\bm_pm\bm.jks -destkeystore H:\hobby\bm_pm\bm.jks -deststoretype pkcs12