package com.whpe.qrcode.shandong_jining.utils;

import java.io.UnsupportedEncodingException;

public class StringByte {
	@SuppressWarnings("unused")
	private static String sb(String content) {
		String str = content;

		String hexString = "0123456789ABCDEF";
		byte[] bytes;
		try {
			bytes = str.getBytes("GBK");// ����˴����ӱ���ת�����õ��Ľ���Ͳ�������Ľ��������ת��
			StringBuilder sb = new StringBuilder(bytes.length * 2);

			for (int i = 0; i < bytes.length; i++) {
				sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
				sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
				// sb.append("");
			}
			str = sb.toString();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;

	}
	   public static byte[] HexString2Bytes(String src) {
	        if (null == src || 0 == src.length()) {
	            return null;
	        }
	        byte[] ret = new byte[src.length() / 2];
	        byte[] tmp = src.getBytes();
	        for (int i = 0; i < (tmp.length / 2); i++) {
	            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
	        }
	        return ret;
	    }
	    /**
	     * ������ASCII�ַ��ϳ�һ���ֽڣ� �磺"EF"�C> 0xEF
	     * 
	     * @param src0
	     *            byte
	     * @param src1
	     *            byte
	     * @return byte
	     */
	    public static byte uniteBytes(byte src0, byte src1) {
	        byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
	        _b0 = (byte) (_b0 << 4);
	        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
	        byte ret = (byte) (_b0 ^ _b1);
	        return ret;
	    }

}
