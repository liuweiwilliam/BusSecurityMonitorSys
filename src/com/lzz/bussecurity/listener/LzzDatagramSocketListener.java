package com.lzz.bussecurity.listener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.lzz.bussecurity.thread.LzzDatagramSocketHandler;
import com.lzz.bussecurity.thread.LzzSocketHandler;

public class LzzDatagramSocketListener {
	private static int port = 11122;
	
	public static void startListen(){
		try{
			int count=0;
			//1.创建数据报套接字
			DatagramSocket socket = null;
			try {
				socket = new DatagramSocket(port);
				System.out.println("创建UDP监听服务成功！");
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				System.out.println("创建UDP监听服务失败！！！！！！！！！！！！！！");
				e.printStackTrace();
			}
			
			while(true){
	            //2.创建一个数据报包
	            byte[] content = new byte[1024];
	            DatagramPacket datagramPacket = new DatagramPacket(content,content.length);
	            //3.调用receive方法接收数据包
				socket.receive(datagramPacket);
				//创建一个新的线程
				LzzDatagramSocketHandler serverThread = new LzzDatagramSocketHandler(socket, datagramPacket);
	            //启动线程
	            serverThread.start();
	
	            count++;//统计客户端的数量
	            System.out.println("收到包数量：" + count);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
