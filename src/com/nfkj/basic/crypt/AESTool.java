package com.nfkj.basic.crypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nfkj.basic.defer.CoreDeferObject;


/**
 * @author Rockey
 * @Description todo
 */
public class  AESTool {  
    public static void main(String[] args) {  
    	String content = "qC9p8k3lfqtzJHIe9NyVFw==";  
    	String password = "2XmsdC7dL8Nx1f3zYfjBhvspfVnKJz4W";  
    	
    	byte[] decryptResult = null;
		try {
		  byte[] b=	db(content.getBytes("UTF-8"));
			decryptResult = decrypt(b,password);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
    	System.out.println("解密后：" + new String(decryptResult));  
    }  
    
    public static byte[] decrypt(byte[] content, String password) { 
        try { 
            Cipher cipher = Cipher.getInstance("AES");   
            try {
				keyval = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
            cipher.init(Cipher.DECRYPT_MODE, keyval);   
            byte[] result = cipher.doFinal(content);
            return result;  
        } catch (NoSuchAlgorithmException e) {   
            e.printStackTrace();   
        } catch (NoSuchPaddingException e) {   
            e.printStackTrace();   
        } catch (InvalidKeyException e) {   
            e.printStackTrace();   
        } catch (IllegalBlockSizeException e) {   
                e.printStackTrace();   
        } catch (BadPaddingException e) {   
                e.printStackTrace();   
        }   
        return null;   
	}  
  static  SecretKeySpec  keyval = null;
	public static byte[] encrypt(String content, String password) {  
        try {             
        	
                
                keyval = new SecretKeySpec(password.getBytes("UTF-8"), "AES");  
                Cipher cipher = Cipher.getInstance("AES");
                byte[] byteContent = content.getBytes("UTF-8");  
                cipher.init(Cipher.ENCRYPT_MODE, keyval);
                byte[] result = cipher.doFinal(byteContent); 
                byte[] enresult = eb(result);
                return result; 
        } catch (NoSuchAlgorithmException e) {  
                e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
                e.printStackTrace();  
        } catch (InvalidKeyException e) {  
                e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
                e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
                e.printStackTrace();  
        } catch (BadPaddingException e) {  
                e.printStackTrace();  
        }  
        return null;  
}  
	public static byte[] db(byte[] s)
	{
		return CoreDeferObject.get().getDeferBase64().base64Decode(new String(s));
	}
	
	public static byte[] eb(byte[] b)
	{
		return CoreDeferObject.get().getDeferBase64().base64(b);
	}
    
    
  
}  