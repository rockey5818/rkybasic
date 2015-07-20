package com.nfkj.device.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nfkj.basic.memory.MemoryManager;
import com.nfkj.basic.storage.cache.AndroidStorageManager;
import com.nfkj.basic.storage.cache.dbhelper.UserDefaultUtils;
import com.nfkj.basic.util.Logger;
import com.nfkj.basic.util.OperationQueque;
import com.nfkj.basic.util.RkyLog;
import com.nfkj.basic.util.ThreadSafeStrongLimitSizeList;
import com.nfkj.basic.util.ThreadSafeWeakMap;
/**
 * @author Rockey
 * @description  todo
 */
public class CacheManager
{

    private static CacheManager sharedInstance = null;

    public static synchronized CacheManager get()
    {
        if (sharedInstance == null)
        {
            sharedInstance = new CacheManager();
        }
        return sharedInstance;
    }

    public CacheManager()
    {

    }

    final public static long WEBVIEW_MAX_CACHE_SIZE = 1024 * 1024 * 10;

    private static ThreadSafeWeakMap<String> m_bufferUrlPaths = new ThreadSafeWeakMap<String>();
    private static ThreadSafeStrongLimitSizeList m_limitUrlPaths = new ThreadSafeStrongLimitSizeList(200);
    private Object m_tempObj;
    private final static String ApplicationBasePath = Environment.getExternalStorageDirectory().getPath()
            + "/Android/data/" + ContextUtils.getSharedContext().getPackageName() + "/cache";// DirUtil.getDiskCacheDir(ContextUtils.getSharedContext(),
                                                                                             // "").getPath();
    private final static String ApplicationAudioPath = "/audio";
    private final static String ApplicationImagePath = "/thumbs";
    private final static String ApplicationTempPath = "/temp";
    private final static String ApplicationHttpCachPath = "/http";
    private static final String ApplicationLogPath = "/debuglog";
    private static final String ApplicationCrashLogPath = "/log";
    private static final String ApplicationLogUploadPath = "/debuglog/upload";
    private static final String ApplicationPhotoPath = "/local";
    private static final String ApplicationZipPath = "/zip";
    private static final String ApplicationUpdatePath = "/update";
    private static final String ApplicationInternalCacheDir = ContextUtils.getSharedContext().getCacheDir() == null ? "/data/data/"
            + AvqUtils.context.getPackageName(ContextUtils.getSharedContext()) + "/cache"
            : ContextUtils.getSharedContext().getCacheDir().getAbsolutePath();
    private static final String ApplicationInternalDatabaseDir = ContextUtils.getSharedContext().getDatabasePath(
            "database") == null ? "/data/data/" + AvqUtils.context.getPackageName(ContextUtils.getSharedContext())
            + "/databases/database" : ContextUtils.getSharedContext().getDatabasePath("database").getAbsolutePath();
    final private static long TIME_AUTO_CLEAN = 7 * 24 * 60 * 60 * 1000;
    final private static long TIME_AUTO_CLEAN_THUMB = 30 * 24 * 60 * 60 * 1000;
    final private static long TIME_AUTO_CLEAN_AVATAR = 365 * 24 * 60 * 60 * 1000;
    private OperationQueque m_queque = new OperationQueque();
    private long m_lastCleanInternalCache;
    private long m_lastCleanExternalCache;

    private void CheckOrCreateFolder(String fileName)
    {

        File audioFile = new File(fileName.substring(0, fileName.lastIndexOf("/")));
        if (!audioFile.exists())
        {
            audioFile.mkdirs();
        }
    }

    private String getHttpCacheFolderPath()
    {
        String path = ApplicationBasePath + ApplicationHttpCachPath + "/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    private String getHttpAvatarCacheFolderPath()
    {
        String path = ApplicationBasePath + ApplicationHttpCachPath + "/avatar/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    private String getHttpThumbPicCacheFolderPath()
    {
        String path = ApplicationBasePath + ApplicationHttpCachPath + "/thumb/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    private String getHttpSoundCacheFolderPath()
    {
        String path = ApplicationBasePath + ApplicationHttpCachPath + "/sound/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    public String getLogCacheFolderPath()
    {
        String path = ApplicationBasePath + ApplicationLogPath + "/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    public String getCrashLogFolderPath()
    {
        String path = ApplicationBasePath + ApplicationCrashLogPath + "/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    public String getZipFolderPath()
    {
        String path = ApplicationBasePath + ApplicationZipPath + "/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    public String getUpdateFloderPath()
    {
        String path = ApplicationBasePath + ApplicationUpdatePath + "/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    public String getLogUploadFolderPath()
    {
        String path = ApplicationBasePath + ApplicationLogUploadPath + "/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    private String getTempSaveFolderPath()
    {
        String path = ApplicationBasePath + ApplicationTempPath + "/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    public String getPhotoPath()
    {
        String path = ApplicationBasePath + ApplicationPhotoPath + "/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    private String getAudioSaveFolderPath()
    {
        String path = ApplicationBasePath + ApplicationAudioPath + "/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    public String getImageSaveFolderPath()
    {
        String path = ApplicationBasePath + ApplicationImagePath + "/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    public String getHttpWebviewCacheFolderPath()
    {
        String path = ApplicationBasePath + ApplicationHttpCachPath + "/webview/";
        this.CheckOrCreateFolder(path);
        return path;
    }

    public String getSdcardFilePath(String filename)
    {
        if (filename == null)
        {
            return null;
        }

        return Environment.getExternalStorageDirectory().getPath() + "/" + filename;
    }

    public String getTempFilePath(String filename)
    {
        if (filename == null)
        {
            return null;
        }

        return getTempSaveFolderPath() + filename;
    }

    public String getUniqueTempPath()
    {
        String tempPath = getTempSaveFolderPath() + AvqUtils.Encode.encodeByMD5(UUID.randomUUID().toString());
        return tempPath;
    }

    public String getUniqueImagePath()
    {
        String tempPath = getImageSaveFolderPath() + AvqUtils.Encode.encodeByMD5(UUID.randomUUID().toString());
        return tempPath;
    }

    public String getUniqueAudioPath()
    {
        String tempPath = getAudioSaveFolderPath() + AvqUtils.Encode.encodeByMD5(UUID.randomUUID().toString());
        return tempPath;
    }

    public String getDebugLogPath(String path)
    {
        if (path == null)
        {
            return getLogCacheFolderPath() + AvqUtils.Encode.encodeByMD5(UUID.randomUUID().toString());
        }

        String tempPath = getLogCacheFolderPath() + path;

        return tempPath;
    }

    public String getImagePath(String filename)
    {
        if (filename == null)
        {
            return getUniqueImagePath();
        }

        String tempPath = getImageSaveFolderPath() + filename;

        return tempPath;
    }

    public String getDcimFolderPath()
    {
        return Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DCIM;
    }

    public List<String> getDcimPics(int num)
    {
        String path = getDcimFolderPath() + "/";
        List<String> temp = new ArrayList<String>();
        scanFolder(path, temp, num);

        return temp;
    }

    private void scanFolder(String baseUrl, List<String> lists, int num)
    {
        if (lists == null || baseUrl == null)
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
                if (lists.size() >= num)
                {
                    return;
                }

                scanFolder(baseUrl + s + "/", lists, num);
            }
            else
            {
                if (lists.size() >= num)
                {
                    return;
                }

                int index = s.lastIndexOf(".");
                String path = baseUrl + s;

                if (index != -1)
                {
                    String predix = s.substring(index + 1, s.length());
                    if (predix != null)
                    {
                        if ("png".equalsIgnoreCase(predix))
                        {
                            lists.add(path);
                        }
                        else if ("jpg".equalsIgnoreCase(predix))
                        {
                            lists.add(path);
                        }
                    }
                }
            }
        }
    }

  

    private boolean isAvatarUrl(String url)
    {
        if (url == null)
        {
            return false;
        }

        Pattern pattern = Pattern.compile("(.)*_90_90(.)*");
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    private boolean isThumbPicUrl(String url)
    {
        if (url == null)
        {
            return false;
        }

        Pattern pattern = Pattern.compile("(.)*_160_160(.)*");
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    private boolean isSoundUrl(String url)
    {
        if (url == null)
        {
            return false;
        }

        Pattern pattern = Pattern.compile("(.)*.amr");
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    private boolean isAvatarPath(String path)
    {
        if (path == null)
        {
            return false;
        }

        if (path.startsWith(getHttpAvatarCacheFolderPath()))
        {
            return true;
        }

        return false;
    }

    private boolean isThumbPath(String path)
    {
        if (path == null)
        {
            return false;
        }

        if (path.startsWith(getHttpThumbPicCacheFolderPath()))
        {
            return true;
        }

        return false;
    }

    public String getHttpCachPath(String url, Map<String, String> map)
    {
        if (url == null)
        {
            return null;
        }

        String mapString = null;

        try
        {
            mapString = AvqUtils.json.toStringFromStringMap(map);
        }
        catch (Exception e)
        {

        }

        String tempPath = getHttpCacheFolderPath()
                + AvqUtils.Encode.encodeByMD5(url + AvqUtils.string.getNotNullString(mapString));
        return tempPath;
    }

    private String getThreadId()
    {
        String s = "Thread id:" + Thread.currentThread().getId() + "\r\n" + "Thread name:"
                + Thread.currentThread().getName() + "\r\n";
        return s;
    }

    private String getLogHead()
    {
        String log = String.format("%s\r\n\r\n" + "Manufacturer: %s\r\n" + "Model: %s\r\nProduct: %s\r\n\r\n"
                + "Android Version: %s\r\n" + "API Level: %d\r\n" + "Heap Size: %sMB\r\n\r\n"
                + "Total Size: %sMB\r\n\r\n" + "Free Memory Size: %sMB\r\n\r\n" + "availMem Memory Size: %sMB\r\n\r\n"
                + "Rom Available Memory Size: %sMB\r\n\r\n" + "Rom Total Memory Size: %sMB\r\n\r\n"
                + "SD Available Memory Size: %sMB\r\n\r\n" + "SD Total Memory Size: %sMB\r\n\r\n"
                + "Version Route: %s\r\n\r\n" + "Log Trace:\r\n\r\n", new Date(), Build.MANUFACTURER, Build.MODEL,
                Build.PRODUCT, Build.VERSION.RELEASE, Build.VERSION.SDK_INT,
                Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024,
                Runtime.getRuntime().freeMemory() / 1024 / 1024, MemoryManager.getFreeMemoryInfo(),
                MemoryManager.getAvailableInternalMemorySize(), MemoryManager.getTotalInternalMemorySize(),
                MemoryManager.getSDAvailaleSize(), MemoryManager.getSDTotalSize(),
                AvqUtils.string.getNotNullString(UserDefaultUtils.getVersionRoute()));

        return log;
    }

    private String getTodayFileName(String tag)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return tag + "_" + df.format(new Date()) + ".txt";
    }

    public boolean isTodayLogExist(String tag)
    {
        String tempPath = getLogCacheFolderPath() + getTodayFileName(tag);
        File file = new File(tempPath);

        return file.exists();
    }

    public void writeLog(String text)
    {
        this.writeCustomLog(text, "log");
    }

    public String getLogPath()
    {
        String tempPath = getLogCacheFolderPath() + getTodayFileName("log");
        return tempPath;
    }

    public String getCustomLogPath(String Tagname)
    {
        String tempPath = getLogCacheFolderPath() + getTodayFileName(Tagname);

        return tempPath;
    }

    public String getCustomLogPath(String Tagname, long timeMilles)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timeMilles);
        String filename = AvqUtils.string.getNotNullString(Tagname) + "_" + df.format(date) + ".txt";

        String tempPath = getLogCacheFolderPath() + filename;

        return tempPath;
    }

    public void writeFatalErrorLog(String text)
    {
        this.writeCustomLog(text, "fatalerror");
    }

    public void writeNetworkStaticsLog(String text)
    {
        this.writeCustomLog(text, "network");
    }

    public synchronized void writeCustomLog(String text, String Tagname)
    {
        if (RkyLog.getIns().needPrint())
        {
            try
            {
                String tempPath = getLogCacheFolderPath() + getTodayFileName(Tagname);
                File file = new File(tempPath);

                if (!file.exists())
                {
                    AvqUtils.file.printLogToFile(tempPath, getLogHead(), true);
                }

                AvqUtils.file.printLogToFile(tempPath, getThreadId() + text, true);
            }
            catch (Exception e)
            {

            }
        }
    }

    public void writeHttpLog(String text)
    {
        writeCustomLog(text, "behindHttp");
    }

    public void writeSqlLog(String text)
    {
        writeCustomLog(text, "sql");
    }

    public void writePref(String text)
    {
        writeCustomLog(text, "pref");
    }

    public String getNowString()
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * File Operation
     */
    public File getRandomFile()
    {
        String path = getUniqueTempPath();

        writeToFile(path, String.valueOf(UUID.randomUUID().toString()).getBytes());

        return new File(path);
    }

    public void scanInternalCacheFolders()
    {
        String path = getInternalCachePath();

        if (path != null)
        {
            String parentPath = new File(path).getParent() + "/";
            AvqUtils.file.scanFolder(parentPath);
        }
    }

    public void scanInternalDatabaseFolders()
    {
        String path = getInternalDatabasePath();

        if (path != null)
        {
            String parentPath = new File(path).getParent() + "/";
            AvqUtils.file.scanFolder(parentPath);
        }
    }

    public void copyInternalDatabaseFoldersToExternal()
    {
        String path = getInternalDatabasePath();

        if (path != null)
        {
            String parentPath = new File(path).getParent() + "/";
            AvqUtils.file.copyFoler(parentPath, "Android/data/" + ContextUtils.getSharedContext().getPackageName()
                    + "/cache");
        }
    }

    public void copyExternalDatabaseFoldersToInternal(String filename)
    {
        if (filename == null)
        {
            return;
        }

        String path = getInternalDatabasePath();

        if (path != null)
        {
            String parentPath = new File(path).getParent() + "/";
            String toPath = parentPath + filename;
            String fromPath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/"
                    + ContextUtils.getSharedContext().getPackageName() + "/cache" + parentPath + filename;

            try
            {
                AvqUtils.Stream.CopyFile(fromPath, toPath);
            }
            catch (Exception e)
            {

            }
        }
    }

    private String getInternalDatabasePath()
    {
        return ApplicationInternalDatabaseDir;
    }

    public void clearInernalCache()
    {
        long current = System.currentTimeMillis();

        if (current - m_lastCleanInternalCache <= 60 * 60 * 1000)
        {
            return;
        }

        writeLog("clean internal");
        m_lastCleanInternalCache = current;

        String path = getInternalCachePath();

        if (path != null)
        {
            String parentPath = new File(path).getParent();
            AvqUtils.file.deleteDirectory(new File(parentPath));
        }
    }

    public String getInternalCachePath()
    {
        String nativePath = ApplicationInternalCacheDir + "/" + UUID.randomUUID().toString();
        this.CheckOrCreateFolder(nativePath);
        return nativePath;
    }

    public String saveToInternalCache(byte[] bytes)
    {
        String tempPath = getInternalCachePath();
        writeToFile(tempPath, bytes);
        return tempPath;
    }

    public void writeToFile(String path, byte[] buffer)
    {
        AvqUtils.file.writeToFile(path, buffer);
    }

    public byte[] readFromFile(String path)
    {
        return AvqUtils.file.readFromFile(path);

    }

    public void copyFile(String from, String to)
    {
        AvqUtils.file.copyFile(from, to);
    }

    public void rename(String from, String to)
    {
        AvqUtils.file.rename(from, to);
    }

    public boolean isSDMounted()
    {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            return true;
        }

        return false;
    }

    /**
     * Entity
     */
    public void saveTopicEnableNotificaton(boolean isEnable)
    {
       
    }

    public boolean loadTopicEnableNotification()
    {
        return false;
    }

    public void setEntity(Object obj)
    {
        this.m_tempObj = obj;
    }

    public Object getEntity()
    {
        return this.m_tempObj;
    }

    public void clearEntity()
    {
        this.m_tempObj = null;
    }

    /* *uri path* */
    public String getMediaURIPath(String path)
    {
        final char SLASH = '/';
        final String AVATAR_URI_PREFIX = "file://";
        if (path == null || path.isEmpty())
        {
            return path;
        }
        if (path.indexOf("://") > 0)
        {
            return path;
        }

        final int prefixLength = AVATAR_URI_PREFIX.length();
        Character prefixLastChar = AVATAR_URI_PREFIX.charAt(prefixLength - 1);
        Character urlFirstChar = path.charAt(0);

        if (SLASH == prefixLastChar && SLASH == urlFirstChar)
        {
            return String.format("%s%s", AVATAR_URI_PREFIX, path.substring(1));
        }
        else if (SLASH == prefixLastChar || SLASH == urlFirstChar)
        {
            return AVATAR_URI_PREFIX.concat(path);
        }
        else
        {
            return String.format("%s" + SLASH + "%s", AVATAR_URI_PREFIX, path);
        }

    }

}
