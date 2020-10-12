package com.gp.oktest.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class SDCardInfo {
    boolean isExist;
    long totalBlocks;
    long freeBlocks;
    long availableBlocks;
    long blockByteSize;
    long totalBytes;
    long freeBytes;
    long availableBytes;

    @Override
    public String toString() {
        return "isExist=" + isExist +
                "\ntotalBlocks=" + totalBlocks +
                "\nfreeBlocks=" + freeBlocks +
                "\navailableBlocks=" + availableBlocks +
                "\nblockByteSize=" + blockByteSize +
                "\ntotalBytes=" + totalBytes +
                "\nfreeBytes=" + freeBytes +
                "\navailableBytes=" + availableBytes;
    }


    /**
     * 获取SD卡信息
     *
     * @return SDCardInfo
     */
    public static String getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        if (!isSDCardEnable()) return "sdcard unable!";
        sd.isExist = true;
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        sd.totalBlocks = sf.getBlockCountLong();
        sd.blockByteSize = sf.getBlockSizeLong();
        sd.availableBlocks = sf.getAvailableBlocksLong();
        sd.availableBytes = sf.getAvailableBytes() / 1024 / 1024;
        sd.freeBlocks = sf.getFreeBlocksLong();
        sd.freeBytes = sf.getFreeBytes();
        sd.totalBytes = sf.getTotalBytes();
        return sd.toString();
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 获取手机外部总空间大小
     *
     * @return 总大小，字节为单位
     */
    static public long getTotalExternalMemorySize() {
        if (isSDCardEnable()) {
            //获取SDCard根目录
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    public static String getFreeSpace() {
        if (!isSDCardEnable()) return "sdcard unable!";
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize, availableBlocks;
        availableBlocks = stat.getAvailableBlocksLong();
        blockSize = stat.getBlockSizeLong();
        long size = availableBlocks * blockSize / 1024L;
        return String.valueOf(size);
    }


}