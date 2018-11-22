package com.lzz.bussecurity.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import com.lzz.bussecurity.listener.LzzPackageAnalysis;
import com.lzz.bussecurity.utils.LzzDateUtil;

/*
 * 服务器线程处理类
 */
public class LzzDatagramSocketHandler extends Thread {
    // 和本线程相关的Socket
	DatagramSocket socket = null;
	DatagramPacket datagramPacket = null;

    public LzzDatagramSocketHandler(DatagramSocket socket, DatagramPacket datagramPacket) {
        this.socket = socket;
        this.datagramPacket = datagramPacket;
    }

    //线程执行的操作，响应客户端的请求
    public void run(){
        //从数据报包中获取数据
        byte[] data=  datagramPacket.getData();//获取数据报包中的数据
        int length = datagramPacket.getLength();//
        InetAddress ip = datagramPacket.getAddress();
        int port = datagramPacket.getPort();
        //System.out.println("UDP内容:"+new String(data,0,length));
        //System.out.println("UDP数据长度:"+length);
        //System.out.println("UDP发送方的IP地址:"+ip);
        //System.out.println("UDP发送方的端口号:"+port);
        
        LzzPackageAnalysis.analysis(data);
        
        byte[] resp_data = LzzDateUtil.getNow("s").getBytes();
        DatagramPacket response = new DatagramPacket(resp_data, resp_data.length, datagramPacket.getAddress(), datagramPacket.getPort());
        try {
			socket.send(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //释放资源
        //socket.close();
    }
}