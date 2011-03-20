package com.catgen.servlet;

import org.apache.commons.codec.binary.Base64;

public class Base64Converter
{
	public static void main(String[] args){
        String hello = "genpepita!";

        byte[] encoded = Base64.encodeBase64(hello.getBytes());
        byte[] decoded = Base64.decodeBase64(encoded);
        System.out.println("Encoded: "+new String(encoded));
        System.out.println("Decoded: "+new String(decoded));
    }
}