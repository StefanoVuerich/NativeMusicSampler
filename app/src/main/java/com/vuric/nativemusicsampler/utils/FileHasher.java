package com.vuric.nativemusicsampler.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * Created by stefano on 4/25/2016.
 */
public class FileHasher {

    public FileHasher() {}

    public static String getHash(String samplePath) {

        File sample = new File(samplePath);
        byte[] bFile = new byte[(int) sample.length()];
        FileInputStream fileInputStream;

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(sample);

            int read = 0;
            while ((read = fileInputStream.read(bFile)) != -1) {
                md.update(bFile, 0, read);
            }

            fileInputStream.close();
            byte[] mdBytes = md.digest();

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < mdBytes.length; ++i) {
                hash.append(String.format("%02x", mdBytes[i]));
            }
            return hash.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
