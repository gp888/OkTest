# OkTest
andrid api test


获取Android应用签名MD5/sha1/SHA256等证书指纹
keytool -v -list -keystore  rainy.jks

应用key hash 散列值
 keytool -exportcert -alias rainy -keystore rainy.jks | openssl sha1 -binary | openssl base64

生成keystore
keytool -genkey -keystore test.keystore  -alias test -keyalg RSA -validity 10000
