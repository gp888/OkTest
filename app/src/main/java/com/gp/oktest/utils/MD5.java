package com.gp.oktest.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	public static String Md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(str.getBytes(Charset.defaultCharset().toString()));

			byte[] b = md.digest();
			StringBuffer result = new StringBuffer();
			for (byte aB : b)
				result.append(byteHex(aB));
			return result.toString();
		} catch (Throwable e) {
//			Log.e(e.getMessage(), e);
			return "";
		}
	}

    /**
     * 标准32位Md5加密
     * @param plainText
     * @return
     */
    public static String encryption(String plainText) {
        if(null == plainText || "".equals(plainText)){
            return "";
        }
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes("utf-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return re_md5;
    }
	
	private static String byteHex(byte ib) {
		char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];

		return new String(ob);
	}
	public static void main(String[] args) {
		System.out.println(MD5.Md5("2fde982d-ce13-4287-b4b3-55e4452963972356+-*/1422265993051"));
	}
	
}
