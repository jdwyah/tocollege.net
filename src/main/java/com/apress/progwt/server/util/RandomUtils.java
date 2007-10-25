package com.apress.progwt.server.util;

import java.util.Random;

public class RandomUtils {
    private static Random rn = new Random();

	public static int rand(int lo, int hi) {
            int n = hi - lo + 1;
            int i = rn.nextInt() % n;
            if (i < 0){
                    i = -i;
            }
            return lo + i;
    }

    public static String randomstring(int minLength, int maxLength){
            int n = rand(minLength, maxLength);
            byte[] b = new byte[n];
            for (int i = 0; i < n; i++){
                    b[i] = (byte)rand('A', 'Z');
            }
            return new String(b);
    }

    public static String randomstring(int length){
    	return randomstring(length, length);
    }   
    
    
    
}
