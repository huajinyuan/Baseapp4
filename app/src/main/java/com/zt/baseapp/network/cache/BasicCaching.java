package com.zt.baseapp.network.cache;

import android.util.Log;
import android.util.LruCache;

import com.jakewharton.disklrucache.DiskLruCache;
import com.zt.baseapp.utils.TimeUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Request;
import retrofit2.Response;


/**
 * A basic caching system that stores responses in RAM & disk
 * It uses {@link DiskLruCache} and {@link LruCache} to do the former.
 */
public class BasicCaching implements CachingSystem {
    private DiskLruCache diskCache;
    private LruCache<String, Object> memoryCache;

    public BasicCaching(File diskDirectory, long maxDiskSize, int memoryEntries) {
        try {
            diskCache = DiskLruCache.open(diskDirectory, 1, 1, maxDiskSize);
        } catch (IOException exc) {
            Log.e("SmartCall", "", exc);
            diskCache = null;
        }

        memoryCache = new LruCache<>(memoryEntries);
    }

    private static final long REASONABLE_DISK_SIZE = 1024 * 1024; // 1 MB
    private static final int REASONABLE_MEM_ENTRIES = 50; // 50 entries

    /***
     * Constructs a BasicCaching system using settings that should work for everyone
     *
     * @param cacheFile
     * @return
     */
    public static BasicCaching fromCtx(File cacheFile) {
        return new BasicCaching(cacheFile,
                REASONABLE_DISK_SIZE,
                REASONABLE_MEM_ENTRIES);
    }

    @Override
    public <T> void addInCache(Response<T> response, byte[] rawResponse) {
        String cacheKey = urlToKey(response.raw().request().url().url());
        memoryCache.put(cacheKey, rawResponse);

        try {
            DiskLruCache.Editor editor = diskCache.edit(urlToKey(response.raw().request().url().url()));
            editor.set(0, new String(rawResponse, Charset.defaultCharset()));
            editor.commit();
        } catch (IOException exc) {
            Log.e("SmartCall", "", exc);
        }
    }

    @Override
    public <T> byte[] getFromCache(Request request) {
        String cacheKey = urlToKey(request.url().url());
        byte[] memoryResponse = (byte[]) memoryCache.get(cacheKey);
        if (memoryResponse != null) {
            Log.d("SmartCall", "Memory hit!");
            return memoryResponse;
        }

        try {
            DiskLruCache.Snapshot cacheSnapshot = diskCache.get(cacheKey);
            if (cacheSnapshot != null) {
                Log.d("SmartCall", "Disk hit!");
                return cacheSnapshot.getString(0).getBytes();
            } else {
                return null;
            }
        } catch (IOException exc) {
            return null;
        }
    }

    private String urlToKey(URL url) {
        String cacheKey;
        String urlStr;
        urlStr = url.toString() + TimeUtils.millis2String(System.currentTimeMillis(), "yyyyMMddHHmm");
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(urlStr.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(urlStr.hashCode());
        }
        return cacheKey;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
