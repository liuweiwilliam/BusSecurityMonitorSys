package com.lzz.bussecurity.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LzzFileUtil {
	private final static LzzFileUtil singleton = new LzzFileUtil();
	
	public static LzzFileUtil self() { return singleton; }
	public LzzFileUtil getSelf() { return self(); }
	private LzzFileUtil() { }
	
	// statistics all files' count of a given directory
	private long file_count(File f) {
		if (!f.isDirectory()) return -1;
		
		File[] fs = f.listFiles();
		long fc = 0;
		for (File f1: fs) {
			if (f1.isFile()) {
				fc++;
			}
			if (f1.isDirectory()) {
				fc += file_count(f1);
			}
		}
		return fc;
	}
	// statistics all files' total size of a given directory
	private long file_length(File f) { 
		if (!f.isDirectory()) return -1;
		
		File[] fs = f.listFiles();
		long fl = 0;
		for (File f1: fs) {
			if (f1.isFile()) {
				fl += f1.length();
			} 
			if (f1.isDirectory()) {
				fl += file_length(f1);
			}
		}
		return fl;
	}
	
	// get file type of a file
	public String typeOf(String name) {
		List<String> imgs = new ArrayList<String>();
		imgs.add(".png");
		imgs.add(".PNG");
		imgs.add(".jpg");
		imgs.add(".JPG");
		imgs.add(".jpeg");
		imgs.add(".JPEG");
		imgs.add(".gif");
		imgs.add(".GIF");
		imgs.add(".bmp");
		imgs.add(".BMP");
		List<String> auds = new ArrayList<String>();
		imgs.add(".mp3");
		imgs.add(".MP3");
		imgs.add(".wav");
		imgs.add(".WAV");
		imgs.add(".wma");
		imgs.add(".WMA");
		imgs.add(".aac");
		imgs.add(".AAC");
		List<String> vids = new ArrayList<String>();
		imgs.add(".mpeg-1");
		imgs.add(".MPEG-1");
		imgs.add(".mpeg-2");
		imgs.add(".MPEG-2");
		imgs.add(".mpeg-4");
		imgs.add(".MPEG-4");
		imgs.add(".avi");
		imgs.add(".AVI");
		imgs.add(".wmv");
		imgs.add(".WMV");
		imgs.add(".mov");
		imgs.add(".MOV");
		imgs.add(".rmvb");
		imgs.add(".RMVB");
		for (String e: imgs) {
			if (name.contains(e)) return LzzConstString.smFileType_Image;
		}
		for (String e: auds) {
			if (name.contains(e)) return LzzConstString.smFileType_Audio;
		}
		for (String e: vids) {
			if (name.contains(e)) return LzzConstString.smFileType_Video;
		}
		return LzzConstString.smFileType_Normal;
	}
	
	// a file whether exist
	public boolean exists(String name) {
		return (new File(name)).exists();
	}
}
