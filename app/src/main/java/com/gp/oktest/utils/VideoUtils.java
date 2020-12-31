package com.gp.oktest.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.gp.oktest.minivideo.LocalPhoto;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VideoUtils {

	public static List<LocalPhoto> getFrams(String viedioPath, String potoPath) {
		File photoFile = new File(potoPath);
		if (!photoFile.exists()) {
			photoFile.mkdirs();
		} else {
			deleteDirectory(potoPath);
		}
		List<LocalPhoto> mThumbPaths = new ArrayList<>();
		try {
			File f = new File(viedioPath);
			if(!f.exists()) {
				return mThumbPaths;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return mThumbPaths;
		}
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		retriever.setDataSource(viedioPath);
		// 取得视频的长度(单位为毫秒)
		String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		// 取得视频的长度(单位为微秒)
		long microSeconds = Long.valueOf(time) * 1000;
		long atTime = microSeconds / 10;
		long currenttime = System.currentTimeMillis();
		for (long i = atTime, j = 0; i <= microSeconds; i += atTime, j++) {
			// 获取的是微秒
			Bitmap bitmap = retriever.getFrameAtTime(i, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
			String name = (currenttime + j) + "";
			String path = potoPath + File.separator + name + ".jpg";

			LocalPhoto photo = new LocalPhoto();
			photo.setPath(path);
			photo.setHeight(bitmap.getHeight());
			photo.setWidth(bitmap.getWidth());
			if(j == 0) {
				photo.setSelect(true);
			}

			mThumbPaths.add(photo);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(path);
				bitmap.compress(CompressFormat.JPEG, 100, fos);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mThumbPaths;

	}

	public String getFrams(String viedioPath, String potoPath, int number) {
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		retriever.setDataSource(viedioPath);

		if (!new File(potoPath).exists()) {
			new File(potoPath).mkdirs();
		}
		// 获取的是微秒
		Bitmap bitmap = retriever.getFrameAtTime(number * 1000 * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
		String path = potoPath + File.separator + number + ".jpg";
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return path;

	}

	/** 删除目录及目录下的文件
	 * @param filePath 要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String filePath) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!filePath.endsWith(File.separator))
			filePath = filePath + File.separator;
		File dirFile = new File(filePath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			Log.e("VideoUtils", "删除目录失败：" + filePath + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (File file : files) {
			// 删除子文件
			if (file.isFile()) {
				flag = deleteSingleFile(file.getAbsolutePath());
				if (!flag)
					break;
			} else if (file.isDirectory()) {
				//子目录
				flag = deleteDirectory(file.getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			Log.e("VideoUtils", "删除目录失败！");
			return false;
		} else {
			Log.e("VideoUtils", "删除目录成功！");
			return true;
		}
	}

	/** 删除单个文件
	 * @param filePath$Name 要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteSingleFile (String filePath$Name){
		File file = new File(filePath$Name);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				Log.e("VideoUtils", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
				return true;
			} else {
				Log.e("VideoUtils", "删除单个文件" + filePath$Name + "失败！");
				return false;
			}
		} else {
			Log.e("VideoUtils", "删除单个文件失败：" + filePath$Name + "不存在！");
			return false;
		}
	}

}
