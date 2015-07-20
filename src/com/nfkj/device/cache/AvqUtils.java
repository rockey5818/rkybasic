package com.nfkj.device.cache;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.Selection;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.nfkj.model.hardware.MobileCategory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.SAXParserFactory;
/**
 * @author Rockey
 *
 */
@SuppressWarnings("unchecked")
public class AvqUtils
{

    public static class json
    {
    	public static class ValuePair
    	{
    		String key;
    		String value;
    		
			public ValuePair(String key, String value)
			{
				this.key = key;
				this.value = value;
			}
    	}
    	
    	public static String getJson(ValuePair ... pairs)
    	{
    		if (pairs == null)
    		{
    			return null;
    		}
    		
    		Map<String, String> map = new HashMap<String, String>();
    		
    		for (ValuePair pair : pairs)
    		{
    			if (pair.key != null)
    			{
    				map.put(pair.key, pair.value);
    			}
    		}
    		
    		String result = null;
    		
    		try
    		{
    			result = toStringFromStringMap(map);
    		}
    		catch (Exception e)
    		{
    			
    		}    		
    		
    		return result;
    	}
    	
    	public static String getStringValueFromJson(String key, String json)
    	{
    		Map<String, String> map = null;
    		
    		try
    		{
    			map = parseStringMapFromJson(json);
    		}
    		catch (Exception e)
    		{
    			
    		}
    		
    		if (map != null && key != null)
    		{
    			return map.get(key);
    		}
    		
    		return null;
    	}

        public static Object parseFromJson(String jsonString) throws JSONException
        {
            if (jsonString == null)
            {
                return null;
            }

            if (jsonString != null && jsonString.startsWith("\ufeff"))
            {
                jsonString = jsonString.substring(1);
            }

            Object o = AvqUtils.json.parseMapFromJson(jsonString);
            if (o != null)
            {
                return o;
            }
            o = AvqUtils.json.parseListFromJson(jsonString);
            if (o != null)
            {
                return o;
            }
            return null;

        }

        public static Map<String, String> parseStringMapFromJson(String jsonString) throws JSONException
        {
            if (jsonString == null)
            {
                return null;
            }

            if (jsonString != null && jsonString.startsWith("\ufeff"))
            {
                jsonString = jsonString.substring(1);
            }

            JSONObject jobj = null;
            try
            {
                jobj = new JSONObject(jsonString);
            }
            catch (JSONException e)
            {

            }
            finally
            {
                if (jobj != null)
                {
                    Map<String, String> map = new HashMap<String, String>();
                    Iterator<?> it = jobj.keys();

                    while (it.hasNext())
                    {
                        String key = it.next().toString();
                        String value = jobj.getString(key);
                        map.put(key, value);
                    }

                    return map;
                }
            }

            return null;
        }

        public static Map<Object, Object> parseMapFromJson(String jsonString) throws JSONException
        {
            if (jsonString == null)
            {
                return null;
            }

            if (jsonString != null && jsonString.startsWith("\ufeff"))
            {
                jsonString = jsonString.substring(1);
            }

            JSONObject jobj = null;
            try
            {
                jobj = new JSONObject(jsonString);
            }
            catch (JSONException e)
            {

            }
            finally
            {
                if (jobj != null)
                {
                    Map<Object, Object> map = new HashMap<Object, Object>();
                    Iterator<?> it = jobj.keys();

                    while (it.hasNext())
                    {
                        String key = it.next().toString();
                        String value = jobj.getString(key);

                        Map<Object, Object> tempMap = AvqUtils.json.parseMapFromJson(value);
                        List<Object> tempList = AvqUtils.json.parseListFromJson(value);

                        if (tempMap != null)
                        {
                            map.put(key, tempMap);
                        }
                        else if (tempList != null)
                        {
                            map.put(key, tempList);
                        }
                        else
                        {
                            map.put(key, value);
                        }

                    }

                    return map;
                }
            }

            return null;
        }

        public static List<Object> parseListFromJson(String jsonString) throws JSONException
        {
            JSONArray jarray = null;

            if (jsonString == null)
            {
                return null;
            }

            if (jsonString != null && jsonString.startsWith("\ufeff"))
            {
                jsonString = jsonString.substring(1);
            }

            try
            {
                jarray = new JSONArray(jsonString);
            }
            catch (JSONException e)
            {
            }
            finally
            {
                if (jarray != null)
                {
                    List<Object> list = new ArrayList<Object>();

                    for (int i = 0; i < jarray.length(); i++)
                    {
                        String value = jarray.getString(i);

                        Map<Object, Object> tempMap = AvqUtils.json.parseMapFromJson(value);

                        List<Object> tempList = AvqUtils.json.parseListFromJson(value);

                        if (tempMap != null)
                        {
                            list.add(tempMap);
                        }
                        else if (tempList != null)
                        {
                            list.add(tempList);
                        }
                        else
                        {
                            list.add(value);
                        }

                    }

                    return list;
                }
            }

            return null;

        }

        public static List<String> parseStringListFromJson(String jsonString) throws JSONException
        {
            JSONArray jarray = null;

            if (jsonString == null)
            {
                return null;
            }

            if (jsonString != null && jsonString.startsWith("\ufeff"))
            {
                jsonString = jsonString.substring(1);
            }

            try
            {
                jarray = new JSONArray(jsonString);
            }
            catch (JSONException e)
            {

            }
            finally
            {
                if (jarray != null)
                {
                    List<String> list = new ArrayList<String>();

                    for (int i = 0; i < jarray.length(); i++)
                    {
                        String value = jarray.getString(i);
                        list.add(value);
                    }

                    return list;
                }
            }

            return null;
        }

        public static String toStringFromStringMap(Map<String, String> map) throws JSONException
        {
            if (map == null)
            {
                return null;
            }

            Iterator<Entry<String, String>> iter = map.entrySet().iterator();
            JSONObject holder = new JSONObject();

            while (iter.hasNext())
            {
                Map.Entry<String, String> pairs = (Map.Entry<String, String>) iter.next();
                String key = (String) pairs.getKey();
                String value = pairs.getValue();
                holder.put(key, value);
            }

            return holder.toString();
        }

        public static String toStringFromMap(Map<Object, Object> map) throws JSONException
        {
            if (map == null)
            {
                return null;
            }

            Iterator<Entry<Object, Object>> iter = map.entrySet().iterator();
            JSONObject holder = new JSONObject();

            while (iter.hasNext())
            {
                Map.Entry<Object, Object> pairs = (Map.Entry<Object, Object>) iter.next();
                String key = (String) pairs.getKey();
                Object value = pairs.getValue();
                String jsonString = "";

                if (value instanceof Map)
                {
                    jsonString = AvqUtils.json.toStringFromMap((Map<Object, Object>) value);
                }
                else if (value instanceof List)
                {
                    jsonString = AvqUtils.json.toStringFromList((List<Object>) value);
                }
                else
                {
                    if (value == null)
                    {
                        jsonString = (String) value;
                    }
                    else
                    {
                        jsonString = value.toString();
                    }
                }

                holder.put(key, jsonString);

            }
            return holder.toString();
        }

        public static String toStringFromList(List<?> list) throws JSONException
        {
            if (list == null)
            {
                return null;
            }

            JSONArray array = new JSONArray();

            for (Object value : list)
            {
                String jsonString = "";

                if (value instanceof Map)
                {
                    jsonString = AvqUtils.json.toStringFromMap((Map<Object, Object>) value);
                }
                else if (value instanceof List)
                {
                    AvqUtils.json.toStringFromList((List<Object>) value);
                }
                else
                {
                    if (value != null)
                    {
                        jsonString = value.toString();
                    }
                    else
                    {
                        jsonString = (String) value;
                    }
                }

                array.put(jsonString);

            }

            return array.toString();

        }
    }

    public static class file
    {
    	public static boolean isExist(String path)
    	{
    		try
    		{
    			if (path == null)
    			{
    				return false;
    			}
    			
    			return new File(path).exists();
    		}
    		catch (Exception e)
    		{
    			
    		}
    		
    		return false;
    	}
    	
    	public static String getFilename(String path)
    	{
    		if (path == null)
    		{
    			return path;
    		}
    		
    		int index = path.lastIndexOf("/");
        	
        	if (index != -1)
        	{
        		return path.substring(index + 1);
        	}
        	
        	return path;
    	}    	
    	
    	public static long getFileSize(String path)
    	{
    		long size = -1;
    		try
    		{
    			String name = path.substring(path.lastIndexOf("/") + 1, path.length());
    			path = path.substring(0, path.lastIndexOf("/"));
    			File file = new File(path, name);
    			if (file.exists())
    			{
    				size = file.length();
    			}
    		}
    		catch (Exception e)
            {
                System.out.println("file error:" + e.getMessage());
            }

        	return size;
    	}
    	
        /*
         * sample:
         * AvqUtils.file.extractZipFile(CacheManager.get().getDebugLogPath
         * ("test.zip"), CacheManager.get().getZipFolderPath());
         */
        public static Map<String, String> extractZipFile(String source, String desBase)
        {
            if (source == null || desBase == null)
            {
                return null;
            }

            File file = new File(source);

            if (file.exists() == false)
            {
                return null;
            }

            String endString = desBase.substring(desBase.length() - 1, desBase.length());
            String base = String.valueOf(desBase);
            if (endString.equals("/") == false)
            {
                base = base + "/";
            }

            try
            {
                ZipEntry entry;
                ZipFile zipInStream = new ZipFile(source);
                Enumeration<? extends ZipEntry> e = zipInStream.entries();

                FileOutputStream fos = null;
                InputStream is = null;
                byte[] buffer = new byte[1024 * 32];
                int k = 0;
                Map<String, String> map = new HashMap<String, String>();

                while (e.hasMoreElements())
                {
                    entry = (ZipEntry) e.nextElement();
                    String entryName = entry.getName();
                    String outPath = base + entryName;
                    if (entry.isDirectory())
                    {
                        continue;
                    }

                    System.out.println("zip:" + entryName);

                    checkAndCreateDirectory(outPath);

                    fos = new FileOutputStream(new File(outPath));
                    is = zipInStream.getInputStream(entry);

                    while ((k = is.read(buffer, 0, buffer.length)) > 0)
                    {
                        fos.write(buffer, 0, k);
                        fos.flush();
                    }

                    fos.close();
                    is.close();
                    map.put(entryName, outPath);
                }

                zipInStream.close();

                return map;
            }
            catch (Exception e)
            {
                System.out.println("zip error:" + e.getMessage());
            }

            return null;
        }
        
        public static boolean deleteFile(String path)
        {
        	boolean result = false;
        	
        	try
        	{
        		if (path != null)
        		{
	        		File file =  new File(path);
	        		result = file.delete();
        		}        		
        	}
        	catch (Exception e)
        	{
        		
        	}
        	
        	return result;
        }
        

        public static boolean deleteDirectory(File dir)
        {
            if (dir == null)
            {
                return true;
            }

            if (!dir.exists() || !dir.isDirectory())
            {
                return false;
            }

            String[] files = dir.list();

            for (int i = 0, len = files.length; i < len; i++)
            {
                File f = new File(dir, files[i]);

                if (f.isDirectory())
                {
                    deleteDirectory(f);
                }
                else
                {
                    f.delete();
                }
            }

            return dir.delete();
        }

        public static void copyFile(String from, String to)
        {
            if (from == null || to == null)
            {
                return;
            }
            try
            {
                File fromFile = new File(from);
                if (fromFile.exists() == false)
                {
                    return;
                }
                checkAndCreateDirectory(to);
                File toFile = new File(to);
                FileInputStream fis = new FileInputStream(fromFile);
                FileOutputStream fos = new FileOutputStream(toFile);

                byte[] buffer = new byte[32 * 1024];
                int k;
                while ((k = fis.read(buffer)) > 0)
                {
                    fos.write(buffer, 0, k);
                    fos.flush();
                }
                fis.close();
                fos.close();
            }
            catch (Exception e)
            {

            }
        }

        public static void printLogToFile(String filename, String text, boolean append)
        {
            if (filename == null || text == null)
            {
                return;
            }

            try
            {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowString = df.format(new Date());
                PrintWriter pw = new PrintWriter(new FileWriter(filename, append));
                pw.println();
                pw.println(nowString + "\r\n" + text);
                pw.close();
            }
            catch (Exception e)
            {

            }
        }

        public static boolean rename(String from, String to)
        {
            File source = new File(from);
            return source.renameTo(new File(to));
        }

        public static String readStringFromFile(String path)
        {
            byte[] bytes = readFromFile(path);

            try
            {
                return new String(bytes, "utf-8");
            }
            catch (Exception e)
            {

            }

            return null;
        }

        public static byte[] readFromFile(String path)
        {
            try
            {
                FileInputStream fis = new FileInputStream(new File(path));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024 * 32];
                int k = 0;
                while ((k = fis.read(buffer)) > 0)
                {
                    bos.write(buffer, 0, k);
                    bos.flush();
                }

                fis.close();
                bos.close();

                return bos.toByteArray();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        public static byte[] readFromInputStream(InputStream is)
        {
            if (is == null)
            {
                return new byte[0];
            }

            try
            {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024 * 32];
                int k = 0;
                while ((k = is.read(buffer)) > 0)
                {
                    bos.write(buffer, 0, k);
                    bos.flush();
                }

                is.close();
                bos.close();

                return bos.toByteArray();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return new byte[0];
        }

        public static void writeToFile(String path, byte[] buffer)
        {
            if (buffer == null || path == null)
            {
                return;
            }

            checkAndCreateDirectory(path);

            try
            {
                FileOutputStream fos = new FileOutputStream(new File(path));
                fos.write(buffer);
                fos.flush();
                fos.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        // String baseUrl="/data/data/"+this.getPackageName()+"/";
        public static void scanFolder(String baseUrl)
        {
            if (baseUrl == null)
            {
                return;
            }

            if (new File(baseUrl).exists() == false)
            {
                return;
            }

            File file = new File(baseUrl);
            String[] list = file.list();

            if (list == null)
            {
                return;
            }

            for (String s : list)
            {
                File subFile = new File(baseUrl + s);
                if (subFile.isDirectory())
                {
                    AvqUtils.file.scanFolder(baseUrl + s + "/");
                }
                else
                {
                    String path = baseUrl + s;
                    System.out.println(path + "\nsize: " + new File(path).length());
                }

            }
        }

        // String baseUrl="/data/data/"+this.getPackageName()+"/";
        // this.copyFoler(baseUrl, "wificity");
        public static void copyFoler(String baseUrl, String outputFolderName)
        {
            String outputUrl = Environment.getExternalStorageDirectory().getPath() + "/" + outputFolderName + baseUrl;

            File file = new File(baseUrl);
            String[] list = file.list();

            for (String s : list)
            {
                File subFile = new File(baseUrl + s);

                if (subFile.isDirectory())
                {
                    File outFile = new File(outputUrl + s);
                    outFile.mkdirs();
                    AvqUtils.file.copyFoler(baseUrl + s + "/", outputFolderName);

                }
                else
                {
                    try
                    {
                        AvqUtils.Stream.CopyFile(baseUrl + s, outputUrl + s);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }

        public static void checkAndCreateDirectory(String path)
        {
            if (path == null)
            {
                return;
            }

            File file = new File(path);
            File parent = file.getParentFile();

            if (parent != null && !parent.exists())
            {
                parent.mkdirs();
            }
        }
    }

    public static class Stream
    {
        public static byte[] getBytes(InputStream is)
        {
            return getBytes(is, true);
        }

        public static byte[] getBytes(InputStream is, boolean isCloseStream)
        {
            if (is == null)
            {
                return null;
            }

            try
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024 * 32];
                int k = 0;
                while ((k = is.read(buffer)) > 0)
                {
                    baos.write(buffer, 0, k);
                    baos.flush();
                }

                if (isCloseStream)
                {
                    is.close();
                }

                baos.close();

                return baos.toByteArray();
            }
            catch (Exception e)
            {

            }

            return null;
        }

        public static void CopyFile(String scrPath, String desPath) throws IOException
        {
            file.checkAndCreateDirectory(desPath);
            FileInputStream fis = new FileInputStream(new File(scrPath));
            FileOutputStream fos = new FileOutputStream(new File(desPath));
            byte[] buffer = new byte[1024];
            int l;
            while ((l = fis.read(buffer)) > 0)
            {
                fos.write(buffer, 0, l);
                fos.flush();
            }

            fis.close();
            fos.close();
        }
    }

    public static class Encode
    {
        private static final String ALGORITHM = "MD5";

        private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
                'd', 'e', 'f' };

        /**
         * encode string
         * 
         * @param algorithm
         * @param str
         * @return String
         */
        public static String encode(String algorithm, String str)
        {
            if (str == null)
            {
                return null;
            }
            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        }

        /**
         * encode By MD5
         * 
         * @param str
         * @return String
         */
        public static String encodeByMD5(String str)
        {
            if (str == null)
            {
                return null;
            }
            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        }

        /**
         * encode By MD5
         * 
         * @param str
         * @return String
         */
        public static String endcodeByMD5(InputStream is)
        {
            if (is == null)
            {
                return null;
            }

            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
                byte[] buffer = new byte[1024 * 32];
                int k = 0;

                while ((k = is.read(buffer)) > 0)
                {
                    messageDigest.update(buffer, 0, k);
                }

                return getFormattedText(messageDigest.digest());
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        public static String encodeByMD5(byte[] bytes)
        {
            if (bytes == null)
            {
                return null;
            }
            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
                messageDigest.update(bytes);
                return getFormattedText(messageDigest.digest());
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        }

        /**
         * Takes the raw bytes from the digest and formats them correct.
         * 
         * @param bytes
         *            the raw bytes from the digest.
         * @return the formatted bytes.
         */
        private static String getFormattedText(byte[] bytes)
        {
            int len = bytes.length;
            StringBuilder buf = new StringBuilder(len * 2);
            for (int j = 0; j < len; j++)
            {
                buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
                buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
            }
            return buf.toString();
        }

        // public static void main(String[] args) {
        // System.out.println("111111 MD5  :"
        // + EncoderHandler.encodeByMD5("111111"));
        // System.out.println("111111 MD5  :"
        // + EncoderHandler.encode("MD5", "111111"));
        // System.out.println("111111 SHA1 :"
        // + EncoderHandler.encode("SHA1", "111111"));
        // }
    }

    public static class context
    {
        public static boolean isWifi(Context c)
        {
            if (c == null)
            {
                return false;
            }

            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (null != activeNetwork)
            {
                int nType = activeNetwork.getType();

                if (nType == ConnectivityManager.TYPE_WIFI || nType == ConnectivityManager.TYPE_WIMAX)
                {
                    return true;
                }
            }

            return false;
        }
        
        public static boolean is3G(Context c)
        {
            if (c == null)
            {
                return false;
            }

            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (null != activeNetwork)
            {
                int nType = activeNetwork.getType();

                if (nType == ConnectivityManager.TYPE_MOBILE)
                {
                    return true;
                }
            }

            return false;
        }
        
        public static boolean isConnected(Context c)
        {
            if (c == null)
            {
                return false;
            }

            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (null != activeNetwork)
            {
            	return true;
            }

            return false;
        }

        public static void showLongToast(Context c, String text)
        {
            Toast.makeText(c, text, Toast.LENGTH_LONG).show();
        }

        public static void showShortToast(Context c, String text)
        {
            Toast.makeText(c, text, Toast.LENGTH_SHORT).show();
        }

        public static void setEditTextSelectionToRightEnd(EditText etext)
        {
            if (etext == null)
            {
                return;
            }

            int position = etext.length();
            Selection.setSelection(etext.getText(), position);
        }

        public static float getScreenDensity(Context c)
        {
            return c.getResources().getDisplayMetrics().density;
        }

        public static int getScreenWidth(Context c)
        {
            return c.getResources().getDisplayMetrics().widthPixels;
        }

        public static int getScreenHeight(Context c)
        {
            return c.getResources().getDisplayMetrics().heightPixels;
        }

        public static void vibrator(Context c, long duration)
        {
            if (duration <= 0 || c == null)
            {
                return;
            }

            Vibrator vibrator = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(duration);
        }

        public static void PrintMemeryInfo(Context c)
        {
            if (c != null)
            {
                System.out.println("memory:" + getFreeMemoryInfo(c) + "/" + Debug.getNativeHeapAllocatedSize() / 1024);
            }
        }

        public static int getFreeMemoryInfo(Context c)
        {

            // Debug.getNativeHeapAllocatedSize()/1024

            if (c == null)
            {
                return 0;
            }

            ActivityManager am = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(outInfo);
            long a = outInfo.availMem / 1024;

            return (int) a;
        }

        public static String getVersionName(Context c)
        {
            if (c == null)
            {
                return null;
            }

            PackageManager manager = c.getPackageManager();

            try
            {
                PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
                return info.versionName;
            }
            catch (android.content.pm.PackageManager.NameNotFoundException e)
            {
                e.printStackTrace();
            }

            return null;

        }

        public static String getPackageName(Context c)
        {
            if (c == null)
            {
                return null;
            }

            PackageManager manager = c.getPackageManager();

            try
            {
                PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
                return info.packageName;
            }
            catch (android.content.pm.PackageManager.NameNotFoundException e)
            {
                e.printStackTrace();
            }

            return null;

        }

        public static int getVersionCode(Context c)
        {
            if (c == null)
            {
                return -1;
            }

            PackageManager manager = c.getPackageManager();

            try
            {
                PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
                return info.versionCode;
            }
            catch (android.content.pm.PackageManager.NameNotFoundException e)
            {
                e.printStackTrace();
            }

            return -1;

        }

        public static float autoResizeFont(float originTextSize, String text, int constraitWidth, int constraintHeight)
        {

            String tempText = "0";
            if (text != null && text.length() != 0)
            {
                tempText = text;
            }

            float tempSize = new Float(originTextSize);
            if (tempSize <= 5)
            {
                return tempSize;
            }

            for (float i = tempSize; i < 100; i++)
            {
                int width = getTextRect(i, tempText).width();
                int height = getTextRect(i, tempText).height();
                if (width > constraitWidth || height > constraintHeight)
                {
                    break;
                }
                tempSize = new Float(i);
            }

            for (float i = tempSize; i >= 5; i--)
            {
                int width = getTextRect(i, tempText).width();
                int height = getTextRect(i, tempText).height();
                if (width < constraitWidth && height < constraintHeight)
                {
                    return i;
                }
            }

            return tempSize;
        }

        public static Rect getTextRect(float fontsize, String textString)
        {
            String text = null;
            if (textString == null)
            {
                text = "";
            }
            else
            {
                text = new String(textString);
            }

            Paint textPaint = new Paint();
            textPaint.setTextSize(fontsize);
            Rect bounds = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), bounds);
            return bounds;
        }

        public static void hideSoftKeyBoard(View view)
        {
            if (view == null)
            {
                return;
            }
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            if (imm.isActive())
            {
            	imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

        public static void showSoftKeyBoard(View view)
        {
            if (view == null)
            {
                return;
            }
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }

        // public static void showLongToast(Context c,String text){
        // Toast.makeText(c, text, Toast.LENGTH_LONG).show();
        // }
        //
        // public static void showShortToast(Context c,String text){
        // Toast.makeText(c, text, Toast.LENGTH_SHORT).show();
        // }

        public static Bitmap getBitmap(Context c, int resid)
        {
            BitmapDrawable bd = (BitmapDrawable) c.getResources().getDrawable(resid);
            if (bd != null)
            {
                return bd.getBitmap();
            }
            return null;
        }

        public static boolean isNetworkConnectionAvailable(Context c)
        {
            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null)
            {
                return false;
            }

            State network = info.getState();

            return (network == NetworkInfo.State.CONNECTED);
        }

        public static boolean isAboveVersionICE_CREAM_SANDWICH()
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            {
                return true;
            }

            return false;
        }

        public static boolean isAboveVersionHONEYCOMB()
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            {
                return true;
            }

            return false;
        }

        public static boolean isAboveVersionJellyBean()
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            {
                return true;
            }

            return false;
        }

        public static void writeToSPref(Context c, String filename, String key, String value)
        {
            if (c == null)
            {
                return;
            }

            SharedPreferences.Editor edit = c.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
            edit.putString(key, value);
            edit.commit();
        }

        public static String readFromSPref(Context c, String filename, String key)
        {
            if (c == null)
            {
                return null;
            }
            SharedPreferences pre = c.getSharedPreferences(filename, Context.MODE_PRIVATE);
            String value = pre.getString(key, null);
            return value;
        }

        public static void writeToSPrefBoolean(Context c, String filename, String key, boolean b)
        {
            if (c == null)
            {
                return;
            }

            SharedPreferences.Editor edit = c.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
            edit.putBoolean(key, b);
            edit.commit();
        }

        public static boolean readFromSPrefBoolean(Context c, String filename, String key, boolean bDefault)
        {
            if (c == null)
            {
                return bDefault;
            }
            SharedPreferences pre = c.getSharedPreferences(filename, Context.MODE_PRIVATE);
            boolean value = pre.getBoolean(key, bDefault);
            return value;
        }
    }

    public static class handler
    {
        public static void send_message(Handler handler, Object content, int msg_what)
        {
            if (handler == null)
            {
                return;
            }

            Message msg = handler.obtainMessage();
            msg.what = msg_what;
            msg.obj = content;
            handler.sendMessage(msg);
        }
    }

    public static class Weak
    {
        public static boolean isValidWeak(WeakReference<?> weak)
        {
            if (weak != null && weak.get() != null)
            {
                return true;
            }

            return false;
        }

        public static <T> T get(WeakReference<T> weak)
        {
            T object = null;

            if (weak != null && (object = weak.get()) != null)
            {
                return object;
            }

            return null;
        }
    }

    public static class bitmap
    {
        public static Bitmap loadAssetsBitmap(String path)
        {
            if (AvqUtils.string.isEmpty(path))
            {
                return null;
            }

            Bitmap bm = null;
            InputStream is = null;
            try
            {
                is = ContextUtils.getSharedContext().getAssets().open(path);
                bm = BitmapFactory.decodeStream(is);
            }
            catch (Exception e)
            {

            }
            finally
            {
                if (is != null)
                {
                    try
                    {
                        is.close();
                    }
                    catch (IOException e)
                    {

                    }
                }
            }

            return bm;
        }

        public static void insertToSystemAlbum(String path)
        {
            if (string.isEmpty(path))
            {
                return;
            }

            ContentValues values = new ContentValues();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk.mm.ss");
            String newname = df.format(new Date());
            values.put(MediaStore.Images.Media.TITLE, newname);// 名称，随便
            values.put(Images.Media.DESCRIPTION, newname);
            values.put(Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, path);
            ContextUtils.getSharedContext().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
        public static int sizeOf(Bitmap data)
        {
            if (data == null)
            {
                return 0;
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1)
            {
                return data.getRowBytes() * data.getHeight();
            }
            else
            {
                return data.getByteCount();
            }
        }

        public static byte[] Bitmap2Bytes(Bitmap bm, int compressRatio)
        {
            if (bm == null)
                return null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, compressRatio, baos);
            return baos.toByteArray();
        }

        public static Bitmap getRealSizeBitmap(Context context, Uri uri)
        {
            InputStream in = null;

            try
            {
                in = context.getContentResolver().openInputStream(uri);
                Bitmap b = BitmapFactory.decodeStream(in);
                in.close();
                return b;
            }
            catch (IOException e)
            {
                return null;
            }
        }

        public static Object ByteToObject(byte[] bytes)
        {
            Object obj = null;

            try
            {
                // bytearray to object
                ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
                ObjectInputStream oi = new ObjectInputStream(bi);

                obj = oi.readObject();

                bi.close();
                oi.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return obj;
        }

        public static byte[] ObjectToByte(Object obj)
        {
            byte[] bytes = null;
            try
            {
                // object to bytearray
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                ObjectOutputStream oo = new ObjectOutputStream(bo);
                oo.writeObject(obj);
                oo.flush();

                bytes = bo.toByteArray();

                bo.close();
                oo.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return bytes;
        }

        public static byte[] getJPEGByteArray(Bitmap bitmap, int ratio)
        {
            if (bitmap == null)
            {
                return new byte[0];
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, ratio, baos);
            byte[] b = baos.toByteArray();

            return b;
        }

        public static byte[] getJPEGByteArray(String path)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(path);

            if (bitmap == null)
            {
                return new byte[0];
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] b = baos.toByteArray();
            return b;
        }

        public static void writeToFile(Bitmap bm, String path)
        {
            byte[] buffer = getJPEGByteArray(bm, 80);
            AvqUtils.file.writeToFile(path, buffer);
        }

        public static Bitmap decodeScaleBitmap(String path, float fScaleRatio)
        {
            if (path == null)
            {
                return null;
            }

            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            Bitmap bitmap = null;

            if (fScaleRatio > 1)
            {
                bmpFactoryOptions.inSampleSize = Math.round(fScaleRatio);
            }

            bmpFactoryOptions.inJustDecodeBounds = false;
            try
            {
                bitmap = BitmapFactory.decodeFile(path, bmpFactoryOptions);
            }
            catch (OutOfMemoryError e)
            {
                System.gc();
                bmpFactoryOptions.inSampleSize += 1;
                bitmap = BitmapFactory.decodeFile(path, bmpFactoryOptions);
            }

            return bitmap;
        }

        public static Bitmap decodeScaleBitmap(String path, int width, int height)
        {
            if (path == null)
            {
                return null;
            }

            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(path, bmpFactoryOptions);

            float maxDim = Math.max((float) bmpFactoryOptions.outWidth, (float) bmpFactoryOptions.outHeight);
            float ratio = maxDim / Math.max(width, height);

            if (ratio > 1)
            {
                bmpFactoryOptions.inSampleSize = (int) (ratio + 0.75); // decrease
                                                                       // the
                                                                       // images
                                                                       // whose
                                                                       // dimension
                                                                       // is
                                                                       // larger
                                                                       // than
                                                                       // 2000px
            }

            bmpFactoryOptions.inJustDecodeBounds = false;
            try
            {
                bitmap = BitmapFactory.decodeFile(path, bmpFactoryOptions);
            }
            catch (OutOfMemoryError e)
            {
                System.gc();
                bmpFactoryOptions.inSampleSize += 1;
                bitmap = BitmapFactory.decodeFile(path, bmpFactoryOptions);
            }

            return bitmap;
        }
        
        public static Bitmap decodeScaleBitmap(byte[] bytes, int width, int height)
        {
            if (bytes == null)
            {
                return null;
            }

            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmpFactoryOptions);

            float maxDim = Math.max((float) bmpFactoryOptions.outWidth, (float) bmpFactoryOptions.outHeight);
            float ratio = maxDim / Math.max(width, height);

            if (ratio > 1)
            {
                bmpFactoryOptions.inSampleSize = (int) (ratio + 0.75); // decrease
                                                                       // the
                                                                       // images
                                                                       // whose
                                                                       // dimension
                                                                       // is
                                                                       // larger
                                                                       // than
                                                                       // 2000px
            }

            bmpFactoryOptions.inJustDecodeBounds = false;
            try
            {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmpFactoryOptions);
            }
            catch (OutOfMemoryError e)
            {
                System.gc();
                bmpFactoryOptions.inSampleSize += 1;
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmpFactoryOptions);
            }

            return bitmap;
        }

        private static int calculateExactScaleRatio(String path, int width, int height)
        {

            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, bmpFactoryOptions);
            float maxDim = Math.max((float) bmpFactoryOptions.outWidth, (float) bmpFactoryOptions.outHeight);
            float ratio = maxDim / Math.max(width, height);

            if (ratio > 1)
            {
                bmpFactoryOptions.inSampleSize = (int) ratio;
            }
            bmpFactoryOptions.inSampleSize = calculateOOMFactor(bmpFactoryOptions.inSampleSize, width, height);

            return bmpFactoryOptions.inSampleSize;
        }

        public static int calculateOOMFactor(int nFactor, int nWidth, int nHeight)
        {
            long lNeedMemorySrc = nWidth * nHeight * 4;
            long lNeedMemory = lNeedMemorySrc;
            if (nFactor > 0)
            {
                lNeedMemory /= nFactor;
            }
            long maxMemory = Runtime.getRuntime().maxMemory(); 
            long totalMemory = Runtime.getRuntime().totalMemory(); 
            long freeMemory = Runtime.getRuntime().freeMemory(); 
            
            long canUseMemory = maxMemory - totalMemory + freeMemory;
            
            while (lNeedMemory >= canUseMemory)
            {
                ++nFactor;
                lNeedMemory = lNeedMemorySrc / nFactor / 2 + 1;
            }

            return nFactor;
        }

        private static Bitmap getPreDecodeScaleBitmapExact(String path, int width, int height)
        {
            int nRatio = calculateExactScaleRatio(path, width, height);
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inSampleSize = nRatio;
            bmpFactoryOptions.inJustDecodeBounds = false;
            Bitmap bm = null;
            
            try
            {
                bm = BitmapFactory.decodeFile(path, bmpFactoryOptions);
            }
            catch (OutOfMemoryError e)
            {
                System.gc();
                bmpFactoryOptions.inSampleSize += 1;
                bm = BitmapFactory.decodeFile(path, bmpFactoryOptions);
            }
            return bm;
        }

        public static Bitmap decodeScaleBitmapExact(String path, int width, int height)
        {
            if (path == null)
            {
                return null;
            }

            Bitmap bm = getPreDecodeScaleBitmapExact(path, width, height);
            if (bm == null)
            {
                return null;
            }

            if (bm.getWidth() <= width && bm.getHeight() <= height)
            {
                return bm;
            }

            float widthRatio = (float) width / bm.getWidth();
            float heightRatio = (float) height / bm.getHeight();
            float ratio = Math.min(widthRatio, heightRatio);

            int w = (int) (ratio * bm.getWidth());
            int h = (int) (ratio * bm.getHeight());

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);

            Bitmap result = Bitmap.createBitmap(w, h, Config.RGB_565);
            Canvas canvas = new Canvas(result);
            canvas.drawBitmap(bm, new Rect(0, 0, bm.getWidth(), bm.getHeight()), new Rect(0, 0, w, h), paint);

            return result;
        }

        public static Bitmap decodeScaleBitmapExt(String path, int width, int height)
        {
            if (path == null)
            {
                return null;
            }

            Bitmap bm = BitmapFactory.decodeFile(path);

            if (bm == null)
            {
                return null;
            }

            if (bm.getWidth() <= width && bm.getHeight() <= height)
            {
                return bm;
            }

            float widthRatio = (float) width / bm.getWidth();
            float heightRatio = (float) height / bm.getHeight();
            float ratio = Math.min(widthRatio, heightRatio);

            int w = (int) (ratio * bm.getWidth());
            int h = (int) (ratio * bm.getHeight());

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);

            Bitmap result = Bitmap.createBitmap(w, h, Config.RGB_565);
            Canvas canvas = new Canvas(result);
            canvas.drawBitmap(bm, new Rect(0, 0, bm.getWidth(), bm.getHeight()), new Rect(0, 0, w, h), paint);

            return result;
        }
    }

    public static class Model
    {
        private static String Key_MobileTopic_Name = "Key_MobileTopic_Name";
        private static String Key_MobileTopic_CreateUserName = "Key_MobileTopic_CreateUserName";
        private static String Key_MobileTopic_Description = "Key_MobileTopic_Description";
        private static String Key_MobileTopic_RoomName = "Key_MobileTopic_RoomName";
        private static String Key_MobileTopic_CategoryName = "Key_MobileTopic_CategoryName";
        private static String Key_MobileTopic_LastOneDayMessageCount = "Key_MobileTopic_LastOneDayMessageCount";
        private static String Key_MobileTopic_TotalMessageCount = "Key_MobileTopic_TotalMessageCount";
        private static String Key_MobileTopic_JoinRoomTopicCount = "Key_MobileTopic_JoinRoomTopicCount";
        private static String Key_MobileTopic_photos = "Key_MobileTopic_photos";
        private static String Key_MobileTopic_RoomTopicId = "Key_MobileTopic_RoomTopicId";

        private static String Key_MobileTopicCategory_roomTopicCategoryId = "Key_MobileTopic_RoomTopicId";
        private static String Key_MobileTopicCategory_name = "Key_MobileTopicCategory_name";
        private static String Key_MobileTopicCategory_checkedColor = "Key_MobileTopicCategory_checkedColor";

        public static String encodeToJsonForMobileCategory(MobileCategory model)
        {
            if (model == null)
            {
                return null;
            }
            Map<Object, Object> map = new HashMap<Object, Object>();
            map.put(Key_MobileTopicCategory_roomTopicCategoryId, String.valueOf(model.getCategoryId()));
            map.put(Key_MobileTopicCategory_name, model.getName() == null ? "" : model.getName());
            map.put(Key_MobileTopicCategory_checkedColor,
                    model.getCheckedColor() == null ? "" : model.getCheckedColor());
            try
            {
                return json.toStringFromMap(map);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return null;

        }

        public static MobileCategory decodeFromJsonForMobileCategory(String json)
        {
            Map<Object, Object> map = null;
            try
            {
                map = AvqUtils.json.parseMapFromJson(json);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            if (map != null)
            {
                MobileCategory model = new MobileCategory();
                model.setName((String) map.get(Key_MobileTopicCategory_name));
                model.setCategoryId(Integer.parseInt((String) map.get(Key_MobileTopicCategory_roomTopicCategoryId)));
                model.setCheckedColor((String) map.get(Key_MobileTopicCategory_checkedColor));
                return model;
            }

            return null;
        }

      
    }

    public static class calendar
    {
    	public static boolean isYesterday(long time)
    	{
    		long yesterday = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
    		
    		if (getYearFromTimeSince1970(time) == getYearFromTimeSince1970(yesterday)    				
    				&& getMonthFromTimeSince1970(time) == getMonthFromTimeSince1970(yesterday)
    				&& getDayFromTimeSince1970(time) == getDayFromTimeSince1970(yesterday))
    		{
    			return true;
    		}
    		
    		return false; 
    	}
    	
    	public static boolean isToday(long time)
    	{
    		long today = System.currentTimeMillis();
    		
    		if (getYearFromTimeSince1970(time) == getYearFromTimeSince1970(today)    				
    				&& getMonthFromTimeSince1970(time) == getMonthFromTimeSince1970(today)
    				&& getDayFromTimeSince1970(time) == getDayFromTimeSince1970(today))
    		{
    			return true;
    		}
    		
    		return false;    			
    	}

        public static int getYearFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            int year = d.get(Calendar.YEAR);
            return year;
        }

        public static int getMonthFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            int month = d.get(Calendar.MONTH) + 1;
            return month;
        }

        public static int getDayFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            int day = d.get(Calendar.DAY_OF_MONTH);
            return day;
        }

        public static int getHourFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            return d.get(Calendar.HOUR_OF_DAY);
        }

        public static int getMinuteFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            return d.get(Calendar.MINUTE);
        }

        public static int getSecondFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            return d.get(Calendar.SECOND);
        }

        public static String getTextFromTimeSince1970(long time)
        {
            return getYearFromTimeSince1970(time) + "年" + getMonthFromTimeSince1970(time) + "月"
                    + getDayFromTimeSince1970(time) + "日" + getTwoDigits(getHourFromTimeSince1970(time)) + "点"
                    + getTwoDigits(getMinuteFromTimeSince1970(time)) + "分";
        }

        public static String getTwoDigits(int number)
        {
            String s = String.valueOf(number);
            if (s.length() <= 1)
            {
                return "0" + s;
            }
            return s;
        }
        
        public static String getTimeText(long time, String f)
        {
        	String result = null;
        
        	try
        	{
	        	String format = f;
	        	
	        	if (format == null)
	        	{
	        		format = "yyyy-MM-dd HH:mm:ss";
	        	}
	        	
	        	SimpleDateFormat df = new SimpleDateFormat(format);
        	
	        	result = df.format(new Date(time));
        	}
        	catch (Exception e)
        	{
        		
        	}
        	
        	return result;
        }
    }

    public static class math
    {

        public static int getRoundHalfUp(double f)
        {
            BigDecimal result = new BigDecimal(f).setScale(0, BigDecimal.ROUND_HALF_UP);
            return result.intValue();
        }

        public static boolean isSameList(List<String> list1, List<String> list2)
        {
            if (list1 == null || list2 == null)
            {
                return false;
            }

            if (list1.size() != list2.size())
            {
                return false;
            }

            int index = 0;

            for (String s : list1)
            {
                if (s == null)
                {
                    return false;
                }

                if (s.equals(list2.get(index)) == false)
                {
                    return false;
                }

                index++;
            }

            return true;
        }
        
        public static boolean isSameCollection(List<?> list1, List<?> list2)
        {
            if (list1 == null || list2 == null)
            {
                return false;
            }

            if (list1.size() != list2.size())
            {
                return false;
            }

            int index = 0;

            for (Object s : list1)
            {
                if (s == null)
                {
                    return false;
                }

                if ((s == list2.get(index)) == false)
                {
                    return false;
                }

                index++;
            }

            return true;
        }

        public static boolean isSameMap(Map<String, String> map1, Map<String, String> map2)
        {
            if (map1 == null || map2 == null)
            {
                return false;
            }

            if (map1.size() != map2.size())
            {
                return false;
            }

            Set<String> keys = map1.keySet();

            for (String key : keys)
            {
                if (key == null)
                {
                    return false;
                }

                String value1 = map1.get(key);
                String value2 = map2.get(key);

                if (value1 == null && value2 == null)
                {
                    continue;
                }

                if (value1 == null)
                {
                    return false;
                }

                if (value1.equals(value2) == false)
                {
                    return false;
                }
            }

            return true;
        }

    }

    public static class byteOperation
    {

        public static int IndexOf(byte[] arrayToSearchThrough, byte[] patternToFind)
        {
            if (patternToFind.length > arrayToSearchThrough.length)
                return -1;
            for (int i = 0; i < arrayToSearchThrough.length - patternToFind.length; i++)
            {
                boolean found = true;
                for (int j = 0; j < patternToFind.length; j++)
                {
                    if (arrayToSearchThrough[i + j] != patternToFind[j])
                    {
                        found = false;
                        break;
                    }
                }
                if (found)
                {
                    return i;
                }
            }
            return -1;
        }

        public static byte[] copy(byte[] org)
        {
            byte[] bytes = new byte[org.length];
            System.arraycopy(org, 0, bytes, 0, org.length);
            return bytes;
        }

        public static byte[] replaceAll(byte[] org, byte[] search, byte[] replace)
        {

            byte[] bytes = copy(org);

            while (true)
            {
                int index = IndexOf(bytes, search);
                if (index < 0)
                {
                    break;
                }
                byte[] newbytes = new byte[bytes.length - search.length + replace.length];

                System.arraycopy(bytes, 0, newbytes, 0, index);
                System.arraycopy(replace, 0, newbytes, index, replace.length);
                System.arraycopy(bytes, index + search.length, newbytes, index + replace.length, bytes.length - index
                        - search.length);
                bytes = newbytes;
            }

            return bytes;
        }

    }

    public static class string
    {
        public static String getString(Object obj)
        {
            if (obj == null)
            {
                return null;
            }

            return String.valueOf(obj);
        }

        public static String getThrowableDescription(Throwable throwable)
        {
            if (throwable == null)
            {
                return null;
            }

            StackTraceElement[] elements = throwable.getStackTrace();

            if (elements == null)
            {
                return null;
            }

            StringBuilder sb = new StringBuilder();

            for (StackTraceElement element : elements)
            {
                sb.append(element.toString() + "\n");
            }

            return sb.toString();
        }

        public static boolean isChinese(char c)
        {
            int min = 0x4E00;
            int max = 0x9FBF;
            int target = c;
            return (target <= max && target >= min) ? true : false;
        }

        public static boolean isEmpty(String target)
        {
            return target == null || target.length() == 0;
        }

        public static boolean getBoolean(String s)
        {
            boolean k = false;

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                if (s.equals("1"))
                {
                    return true;
                }

                k = Boolean.parseBoolean(s);
            }
            catch (Exception e)
            {

            }

            return k;
        }

        public static int getInteger(String s)
        {
            int k = -1;

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                k = Integer.parseInt(s);
            }
            catch (Exception e)
            {

            }

            return k;
        }

        public static int getIntegerDefaultZero(String s)
        {
            int k = 0;

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                k = Integer.parseInt(s);
            }
            catch (Exception e)
            {

            }

            return k;
        }

        public static long getLong(String s)
        {
            long k = -1;

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                k = Long.parseLong(s);
            }
            catch (Exception e)
            {

            }

            return k;
        }

        public static long getLong(String s, long def)
        {
            long k = Long.valueOf(def);

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                k = Long.parseLong(s);
            }
            catch (Exception e)
            {

            }

            return k;
        }

        public static double getDouble(String s)
        {
            double k = -1.0;

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                k = Double.parseDouble(s);
            }
            catch (Exception e)
            {

            }
            return k;
        }

        public static String getNotNullString(String s)
        {
            if (isEmpty(s))
            {
                return "";
            }

            return s;
        }
    }

    public static class print
    {
        public static void printRect(Rect rect, String tagName)
        {
            System.out.println(AvqUtils.string.getNotNullString(tagName) + ": left:" + rect.left + "top:" + rect.top
                    + "right:" + rect.right + "bottom:" + rect.bottom);
        }
    }

    public static class drawable
    {
        public static void changeRightIcon(TextView tv, int resid, int width, int height)
        {
            if (tv == null)
            {
                return;
            }

            Drawable drawable = null;

            if (resid > 0)
            {
                drawable = tv.getContext().getResources().getDrawable(resid);
            }

            if (drawable != null)
            {
                drawable.setBounds(0, 0, width, height);
            }

            tv.setCompoundDrawables(null, null, drawable, null);
        }

        public static void changeRightIcon(TextView tv, Drawable drawable, int width, int height)
        {
            if (tv == null)
            {
                return;
            }

            if (drawable != null)
            {
                drawable.setBounds(0, 0, width, height);
            }

            tv.setCompoundDrawables(null, null, drawable, null);
        }

        public static void changeLeftIcon(TextView tv, int resid, int width, int height)
        {
            if (tv == null)
            {
                return;
            }

            Drawable drawable = null;

            if (resid > 0)
            {
                drawable = tv.getContext().getResources().getDrawable(resid);
            }

            if (drawable != null)
            {
                drawable.setBounds(0, 0, width, height);
            }

            tv.setCompoundDrawables(drawable, null, null, null);
        }

        public static void changeStartIcon(TextView tv, String text, int resid, int width, int height, int residRightPadding, int rightPadding)
        {
            if (tv == null)
            {
                return;
            }

            SpannableString span = new SpannableString("ab" + text);

            Drawable drawable = null;
            Drawable paddingDrawable = null;

            if (resid > 0)
            {
                drawable = tv.getContext().getResources().getDrawable(resid);
            }

            if (residRightPadding > 0)
            {
                paddingDrawable = tv.getContext().getResources().getDrawable(residRightPadding);
            }

            if (drawable != null)
            {
                drawable.setBounds(0, 0, width, height);
            }

            if (paddingDrawable != null)
            {
                paddingDrawable.setBounds(0, 0, rightPadding, height);
            }

            ImageSpan imgSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            span.setSpan(imgSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ImageSpan paddingSpan = new ImageSpan(paddingDrawable, ImageSpan.ALIGN_BASELINE);
            span.setSpan(paddingSpan, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            tv.setText(span);
        }
    }
}
