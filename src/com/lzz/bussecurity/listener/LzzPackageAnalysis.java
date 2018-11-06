package com.lzz.bussecurity.listener;

import org.hibernate.Transaction;

import com.lzz.bussecurity.cachemgr.LzzBusCacheMgr;
import com.lzz.bussecurity.cachemgr.LzzSensorDataCacheMgr;
import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.mgr.LzzBusMgr;
import com.lzz.bussecurity.mgr.LzzDicMgr;
import com.lzz.bussecurity.mgr.LzzIDMgr;
import com.lzz.bussecurity.pojo.LzzBus;
import com.lzz.bussecurity.pojo.LzzSensorData;
import com.lzz.bussecurity.service.LzzBusService;
import com.lzz.bussecurity.service.LzzDictionaryService;
import com.lzz.bussecurity.service.LzzSensorDataService;
import com.lzz.bussecurity.utils.LzzByteRead;
import com.lzz.bussecurity.utils.LzzChangeableParNum;
import com.lzz.bussecurity.utils.LzzConstString;
import com.lzz.bussecurity.utils.LzzStringUtils;

public class LzzPackageAnalysis {
	private static boolean isTest = false;
	
	public static void analysis(byte[] orig){
		/*if(orig[81]==(byte)0
				&& orig[82]==(byte)0){
			System.out.println("81:" + orig[81] + ",82:" + orig[82] + "." + new String(orig));
			return;
		}*/
		if(new String(orig).indexOf(",,")>=0){
			return;
		}
		
		Transaction ts = LzzFactory.currentSession().beginTransaction();
		try{
			LzzChangeableParNum cur_index = new LzzChangeableParNum(0);
			LzzSensorData rslt = new LzzSensorData();
			rslt.setId(LzzIDMgr.self().getID());
			
			//获取地区编号
			String area_code = LzzByteRead.readStringFromByteArray(orig, cur_index, 1);
			if(area_code.contains(",")){
				throw new Exception("数据解析错误");
			}
			//TODO 翻译地区编号
			if(isTest) System.out.println("area_code : " + area_code);
			String area_name = translateAreaCode(area_code);
			
			//获取车牌号
			String car_num = LzzByteRead.readStringFromByteArray(orig, cur_index, 7);
			if(car_num.contains(",")){
				throw new Exception("数据解析错误");
			}
			car_num = car_num.replaceAll("%", "");
			if(isTest) System.out.println("car_num : " + car_num);
			LzzBus bus = LzzBusService.self().getLzzBusByCarNum(area_name + car_num);
			if(null!=bus){
				rslt.setBusId(bus.getId());
			}
	
			//跳过逗号
			cur_index.add();
			
			//获取时间戳
			String time_stamp = LzzByteRead.readStringFromByteArray(orig, cur_index, isTest?18:19);
			if(time_stamp.contains(",")){
				throw new Exception("数据解析错误");
			}
			if(isTest) System.out.println("time_stamp : " + time_stamp);
			rslt.setTime(time_stamp);
			//跳过逗号
			cur_index.add();
			
			//获取纬度
			String lat = LzzByteRead.readStringFromByteArray(orig, cur_index, 8);
			if(lat.contains(",")){
				throw new Exception("数据解析错误");
			}
			if(isTest) System.out.println("latitude : " + lat);
			if(bus!=null){
				bus.setLat(lat);
			}
			//跳过逗号
			cur_index.add();
					
			//获取是南纬还是北纬
			String n_or_s = LzzByteRead.readStringFromByteArray(orig, cur_index, 1);
			if(n_or_s.contains(",")){
				throw new Exception("数据解析错误");
			}
			if(isTest) System.out.println("n_or_s : " + n_or_s);
			//跳过逗号
			cur_index.add();
					
			//获取经度
			String lng = LzzByteRead.readStringFromByteArray(orig, cur_index, 9);
			if(lng.contains(",")){
				throw new Exception("数据解析错误");
			}
			if(isTest) System.out.println("longitude : " + lng);
			if(bus!=null){
				bus.setLng(lng);
			}
			//跳过逗号
			cur_index.add();
			
			//获取是东经还是西经
			String e_or_w = LzzByteRead.readStringFromByteArray(orig, cur_index, 1);
			if(e_or_w.contains(",")){
				throw new Exception("数据解析错误");
			}
			if(isTest) System.out.println("e_or_w : " + e_or_w);
			//跳过逗号
			cur_index.add();
			
			//获取传感器数量
			String sensor_num = LzzByteRead.readStringFromByteArray(orig, cur_index, 1);
			if(sensor_num.contains(",")){
				throw new Exception("数据解析错误");
			}
			if(isTest) System.out.println("sensor_num : " + sensor_num);
			//跳过逗号
			cur_index.add();
			
			//获取传感器状态
			String sensor_status = LzzByteRead.readStringFromByteArray(orig, cur_index, 15);
			if(sensor_status.contains(",")){
				throw new Exception("数据解析错误");
			}
			if(isTest) System.out.println("sensor_status : " + sensor_status);
			//跳过逗号
			cur_index.add();
			
			//获取车辆报警标志
			String car_alarmed = LzzByteRead.readStringFromByteArray(orig, cur_index, 1);
			if(car_alarmed.contains(",")){
				throw new Exception("数据解析错误");
			}
			if(isTest) System.out.println("car_alarmed : " + car_alarmed);
			rslt.setDriverAlerm(car_alarmed);
			String status_id = judgeStatus(sensor_status, car_alarmed);
			//跳过逗号
			cur_index.add();
			
			//获取预留字段
			String def = LzzByteRead.readStringFromByteArray(orig, cur_index, 8);
			if(isTest) System.out.println("def : " + def);
			//跳过逗号
			cur_index.add();
			
			//获取校验和
			String check_sum = LzzByteRead.readStringFromByteArray(orig, cur_index, 1);
			if(isTest) System.out.println("check_sum : " + check_sum);
			
			//初始默认未处理
			rslt.setHandled("0");
			
			rslt.setStatusId(status_id);
			rslt.setStatusName(LzzDictionaryService.self().getLzzDictionaryById(status_id).getValue());
			bus.setStatusId(status_id);
			
			/**
			 * 如果不是警告，直接设置已处理
			 */
			if(!LzzDicMgr.isAlarmStatus(rslt.getStatusId())){
				rslt.setHandled("1");
			}
			/**
			 * 如果是报警数据，且公交设置了忽略警告，设置为已处理
			 */
			if(LzzBusMgr.getBusAlarmIgnoreFlag(rslt.getBusId())
					&& LzzDicMgr.isAlarmStatus(rslt.getStatusId())){
				rslt.setHandled("1");
				bus.setStatusId(normalStatus);
			}
			
			/**
			 * 如果是报警数据，且公交处于报警处理中状态，设置为已处理
			 */
			if(LzzBusMgr.isAlarmHandling(rslt.getBusId())
					&& LzzDicMgr.isAlarmStatus(rslt.getStatusId())){
				rslt.setHandled("1");
				bus.setStatusId(alarmHandlingStatus);
			}
			
			rslt.setVals(new String(orig));
			LzzSensorDataService.self().saveLzzSensorData(rslt);
			LzzBusService.self().updateLzzBus(bus);
			ts.commit();
		}catch(Exception e){
			e.printStackTrace();
			LzzBusCacheMgr.self().reloadCache();
			LzzSensorDataCacheMgr.self().reloadCache();
		}finally{
			LzzFactory.closeSession();
		}
	}

	private static String translateAreaCode(String area_code) {
		switch(area_code){
		case "A":
			return "京";
		case "B":
			return "津";
		case "C":
			return "沪";
		case "D":
			return "渝";
		case "E":
			return "冀";
		case "F":
			return "豫";
		case "G":
			return "云";
		case "H":
			return "辽";
		case "I":
			return "黑";
		case "J":
			return "湘";
		case "K":
			return "皖";
		case "L":
			return "鲁";
		case "M":
			return "新";
		case "N":
			return "苏";
		case "O":
			return "浙";
		case "P":
			return "赣";
		case "Q":
			return "鄂";
		case "R":
			return "桂";
		case "S":
			return "甘";
		case "T":
			return "晋";
		case "U":
			return "蒙";
		case "V":
			return "陕";
		case "W":
			return "吉";
		case "X":
			return "闽";
		case "Y":
			return "贵";
		case "Z":
			return "粤";
		case "a":
			return "青";
		case "b":
			return "藏";
		case "c":
			return "川";
		case "d":
			return "宁";
		case "e":
			return "琼";
		}
		return "";
	}

	static String driverAlarmStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_DriverAlarm).getId();
	static String sensorAlarmStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_SensorAlarm).getId();
	static String sensorAndDriverAlarmStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_SensorAndDriverAlarm).getId();
	static String sensorFaultStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_SensorFault).getId();
	static String noSensorStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_NoSensor).getId();
	static String normalStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_normal).getId();
	static String alarmHandlingStatus = LzzDictionaryService.self().getLzzDictionaryByCategoryAndValue(LzzConstString.smSensorDataStatus, LzzConstString.smSensorDataStatus_AlarmHandling).getId();
	
	/**
	 * 判断传感器状态
	 * @param sensor_status
	 * @return
	 */
	private static String judgeStatus(String sensor_status, String flag) {
		if(isSensorAndDriverAlarm(sensor_status, flag)){
			return sensorAndDriverAlarmStatus;
		}
		
		if(isDriverAlarm(flag)){
			return driverAlarmStatus;
		}
		
		if(isSensorAlarm(sensor_status)){
			return sensorAlarmStatus;
		}
		
		if(isSensorFault(sensor_status)){
			return sensorFaultStatus;
		}
		
		if(isNoSensor(sensor_status)){
			return noSensorStatus;
		}
		
		return normalStatus;
	}

	/**
	 * 判断是否是司机一键报警
	 * @param flag
	 * @return
	 */
	private static boolean isDriverAlarm(String flag) {
		// TODO Auto-generated method stub
		return "1".equals(flag);
	}

	/**
	 * 判断是否是传感器报警和一键报警同时存在
	 * @param sensor_status
	 * @param flag
	 * @return
	 */
	private static boolean isSensorAndDriverAlarm(String sensor_status, String flag) {
		return isSensorAlarm(sensor_status) && "1".equals(flag);
	}

	/**
	 * 判断是否是传感器报警
	 * @param sensor_status
	 * @return
	 */
	private static boolean isSensorAlarm(String sensor_status) {
		//2的个数大于2
		int count = LzzStringUtils.pattern(sensor_status, "2");
		return count>=2;
	}
	/**
	 * 判断是否是传感器故障
	 * @param sensor_status
	 * @return
	 */
	private static boolean isSensorFault(String sensor_status) {
		//3的个数大于2
		int count = LzzStringUtils.pattern(sensor_status, "3");
		return count>=1;
	}
	/**
	 * 判断是否是传感器未安装
	 * @param sensor_status
	 * @return
	 */
	private static boolean isNoSensor(String sensor_status) {
		//4的个数大于2
		int count = LzzStringUtils.pattern(sensor_status, "4");
		return count>=1;
	}
}
