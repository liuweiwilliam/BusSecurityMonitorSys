package com.lzz.bussecurity.utils;

public class LzzByteWrite {
	/**  
     * 向byte[]中写入两个字节的数字  
     * @param i 数字
     * @return  
     */  
    public static int write2ByteNumToByteArray(byte[] orig, int index, int i){
          //由高位到低位  
          orig[index] = (byte)((i >> 8) & 0xFF);  
          orig[index+1] = (byte)(i & 0xFF);
          
          return index+2;
    }
    
    /**  
     * 向byte[]中写入四个字节的数字  
     * @param i 数字
     * @return  
     */  
    public static int write4ByteNumToByteArray(byte[] orig, int index, int i){
          //由高位到低位  
          orig[index] = (byte)((i >> 24) & 0xFF);
          orig[index+1] = (byte)((i >> 16) & 0xFF);  
          orig[index+2] = (byte)((i >> 8) & 0xFF);  
          orig[index+3] = (byte)(i & 0xFF);
          
          return index+2;
    }
    
    /**  
     * 向byte[]中写入一个字节的数字  
     * @param i 数字
     * @return  
     */  
    public static int write1ByteNumToByteArray(byte[] orig, int index, int i){
          //由高位到低位  
          orig[index] = (byte)(i & 0xFF);
          
          return index+1;
    }
   
    /**
     * 向byte[]中写入一个字符串
     * @param rslt 目标数组
     * @param cut_index 写入的位置
     * @param data 写入的数据
     * @return
     */
    public static int writeStringToByteArray(byte[] rslt, int cur_index,
			String data) {
    	if(null==data) return cur_index;
    	
    	for(int i=0; i<data.length(); i++){
    		rslt[cur_index+i] = (byte) data.charAt(i);
		}
		
		return cur_index+data.length();
	}
    
    /**
     * 向byte[]中追加byte[]
     * @param bcd
     * @return
     */
    public static int append(byte[] rslt, byte[] bytes, int cur_index) {
    	System.arraycopy(bytes, 0, rslt, cur_index, bytes.length);
    	return cur_index + bytes.length;
    }
    
	public static void main(String[] args) {
		byte[] rslt = new byte[10];
		writeStringToByteArray(rslt, 5, "123");
		
	}
	


	
}
