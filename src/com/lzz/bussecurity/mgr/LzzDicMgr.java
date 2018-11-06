package com.lzz.bussecurity.mgr;

import com.lzz.bussecurity.pojo.LzzDictionary;
import com.lzz.bussecurity.service.LzzDictionaryService;
import com.lzz.bussecurity.utils.LzzConstString;

public class LzzDicMgr {
	public static String getValueById(String id){
		LzzDictionary dic = LzzDictionaryService.self().getLzzDictionaryById(id);
		if(null==dic) return null;
		return dic.getValue();
	}
	
	private static String normalStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_normal).getId();
	private static String alarmHandlingStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_AlarmHandling).getId();
	private static String sensorAlarmStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_SensorAlarm).getId();
	private static String sensorFaultStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_SensorFault).getId();
	private static String driverAlarmStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_DriverAlarm).getId();
	private static String driverAndSensorAlarmStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_SensorAndDriverAlarm).getId();
	/**
	 * 判断是否是报警状态
	 * @param status
	 * @return
	 */
	public static boolean isAlarmStatus(String status){
		return isSensorAlarm(status) || isDriverAlarm(status);
	}
	/**
	 * 判断是否是报警处理中状态
	 * @param status
	 * @return
	 */
	public static boolean isAlarmHandlingStatus(String status){
		return alarmHandlingStatus.equals(status);
	}
	
	/**
	 * 判断是否传感器报警
	 * @param status
	 * @return
	 */
	public static boolean isSensorAlarm(String status){
		return sensorAlarmStatus.equals(status) || driverAndSensorAlarmStatus.equals(status) || sensorFaultStatus.equals(status);
	}
	
	/**
	 * 判断是否司机一键报警
	 * @param status
	 * @return
	 */
	public static boolean isDriverAlarm(String status){
		return driverAlarmStatus.equals(status) || driverAndSensorAlarmStatus.equals(status);
	}
}
