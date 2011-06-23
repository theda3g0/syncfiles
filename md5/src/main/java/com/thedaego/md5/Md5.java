package com.thedaego.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Md5 
{
    public static void main( String[] args )
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            File file = new File(args[0]);
            InputStream inStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int read = 0;
            while((read = inStream.read(buffer)) > 0){
                md.update(buffer, 0, read);
            }
            byte[] md5sum = md.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);
            
            inStream.close();
            try{
                String compareMd5 = args[1];
                if(compareMd5 != null){
                    if(compareMd5.equals(output)){
                        System.out.println("pass");
                        System.out.println(args[1]+"<-- Provided");
                        System.out.println(output+"<-- Result");
                    }
                }
            }catch(Exception e){
                System.out.println(output+"<-- Result");
            }
        } catch (IOException ex) {
            Logger.getLogger(Md5.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Md5.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            
        }
    }
}
