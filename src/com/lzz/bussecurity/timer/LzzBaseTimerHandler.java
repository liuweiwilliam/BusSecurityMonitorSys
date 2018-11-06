package com.lzz.bussecurity.timer;

import java.util.TimerTask;

public class LzzBaseTimerHandler extends TimerTask {
	private static boolean isRunning = false;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (!isRunning) {    
            isRunning = true;
            
            //handle your task here
            System.out.println("this is timer task");
            
            isRunning = false;    
		  } else {    
			  System.out.println("your task is running...");
		  }
	}

}
