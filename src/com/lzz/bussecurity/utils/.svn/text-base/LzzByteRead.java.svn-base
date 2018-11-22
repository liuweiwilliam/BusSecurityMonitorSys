package com.lzz.bussecurity.utils;

public class LzzByteRead {
	/**  
     * 从byte[]中读取两个字节的数字
     * @param bytes 读取的byte数组 
     * @param index 开始的位置
     * @return  
     */  
    public static int read2ByteNumFromByteArray(byte[] bytes, LzzChangeableParNum index) {
    	 int value=0;  
         //由高位到低位  
         for(int i = 0; i < 2; i++) {  
             int shift= (2-1-i) * 8;  
             value +=(bytes[index.getNum()+i] & 0x000000FF) << shift;//往高位游  
         }
         
         index.add(2);
         
         return value;  
    }
    
    /**  
     * 从byte[]中读取一个字节的数字
     * @param bytes 读取的byte数组 
     * @param index 开始的位置
     * @return  
     */  
    public static int read1ByteNumFromByteArray(byte[] bytes, LzzChangeableParNum index) {
    	 int value=0;  
         //由高位到低位  
         for(int i = 0; i < 1; i++) {  
             value +=(bytes[index.getNum()+i] & 0x000000FF);//往高位游  
         }
         
         index.add();
         
         return value;
    }
    
    /**
     * 从byte[]中读取指定长度的字符串
     * @param bytes 目标byte数组
     * @param index 开始读取位置
     * @param length 读取长度
     * @return 读取到的字符串
     */
    public static String readStringFromByteArray(byte[] bytes, LzzChangeableParNum index, int length){
    	String  rslt = "";
    	
    	for(int i=0; i<length; i++){
    		rslt += (char)bytes[i+index.getNum()];
    	}
    	
    	index.add(length);
    	
    	return rslt;
    }
}
