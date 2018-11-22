package com.lzz.bussecurity.listener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.lzz.bussecurity.thread.LzzSocketHandler;

public class LzzSocketListener {
	private static int port = 8888;
	private static ServerSocket serverSocket;
	
	public static void startListen(){
		try {
			serverSocket = new ServerSocket(port, 10);
			System.out.println("TCP监听服务启动成功！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("TCP监听服务启动失败！！！！！！！！！！！！！！！");
			e.printStackTrace();
			return;
		}
		int count=0;
		while(true){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				//创建一个新的线程
                LzzSocketHandler serverThread=new LzzSocketHandler(socket);
                //启动线程
                serverThread.start();

                count++;//统计客户端的数量
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
