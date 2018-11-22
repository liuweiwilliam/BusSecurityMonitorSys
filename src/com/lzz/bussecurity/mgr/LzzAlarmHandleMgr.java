package com.lzz.bussecurity.mgr;

import com.lzz.bussecurity.pojo.LzzAlarmHandleRecord;

public class LzzAlarmHandleMgr {
	/**
	 * 获取处理记录说明
	 * @param record
	 * @return
	 */
	public static String getHandleRecordComment(LzzAlarmHandleRecord record){
		String sensor_handle = record.getSensorAlarmHandleRecord();
		if("1".equals(record.getIsSensorAlarmValid())){
			sensor_handle += "(传感器报警有效)"; 
		}else if("0".equals(record.getIsSensorAlarmValid())){
			sensor_handle += "(传感器报警无效)"; 
		}
		String driver_handle = record.getDriverAlarmHandleRecord();
		if("1".equals(record.getIsDriverAlarmValid())){
			driver_handle += "(一键报警有效)"; 
		}else if("0".equals(record.getIsDriverAlarmValid())){
			driver_handle += "(一键报警无效)"; 
		}
		
		String comment = sensor_handle + "|" + driver_handle;
		
		return comment;
	}
}
