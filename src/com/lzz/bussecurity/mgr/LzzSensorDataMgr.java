package com.lzz.bussecurity.mgr;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.lzz.bussecurity.pojo.LzzAlarmHandleRecord;
import com.lzz.bussecurity.pojo.LzzBus;
import com.lzz.bussecurity.pojo.LzzSensorAlarmData;
import com.lzz.bussecurity.pojo.LzzSensorData;
import com.lzz.bussecurity.service.LzzAlarmHandleRecordService;
import com.lzz.bussecurity.service.LzzBusService;
import com.lzz.bussecurity.service.LzzDictionaryService;
import com.lzz.bussecurity.service.LzzSensorDataService;
import com.lzz.bussecurity.utils.LzzConstString;

public class LzzSensorDataMgr {
	
	/**
	 * 设置公交报警已处理
	 * @param bus_id
	 */
	public static void setPrevAlarmHandled(String bus_id) {
		List<LzzSensorAlarmData> datas = getUnHandledAlarm(bus_id);
		for(LzzSensorAlarmData data : datas){
			LzzSensorAlarmData alarm_data = LzzSensorDataService.self().getLzzSensorAlarmDataById(data.getId());
			if(null!=alarm_data){
				alarm_data.setHandled("1");
				LzzSensorDataService.self().updateLzzSensorAlarmData(alarm_data);
			}
		}
		
		LzzBusMgr.setBusStatusToAlarmHandling(bus_id);
	}
	
	/**
	 * 获取公交未处理的报警
	 * @param bus_id
	 * @return
	 */
	public static List<LzzSensorAlarmData> getUnHandledAlarm(String bus_id){
		List<LzzSensorAlarmData> rslt = new ArrayList<LzzSensorAlarmData>();
		List<LzzSensorAlarmData> datas = LzzSensorDataService.self().getLzzSensorAlarmDataListByBusId(bus_id);
		for(int i=0; i<datas.size(); i++){
			LzzSensorAlarmData data = datas.get(i);
			if(!"1".equals(data.getHandled())){
				rslt.add(data);
			}
		}
		return rslt;
	}
	
	/**
	 * 判断某辆车是否有某个状态的报警
	 * @param bus_id
	 * @param alarm_status_id
	 * @return
	 */
	public static boolean hasSpecialUnHandledAlarm(String bus_id, String alarm_status_id){
		List<LzzSensorAlarmData> alarms = getUnHandledAlarm(bus_id);
		for(LzzSensorAlarmData alarm : alarms){
			if(alarm.getStatusId().equals(alarm_status_id)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过传感器数据构建传感器报警数据
	 * @param rslt
	 * @return
	 */
	public static LzzSensorAlarmData constructSensorAlarmData(LzzSensorData pro) {
		LzzSensorAlarmData rslt = new LzzSensorAlarmData();
		rslt.setId(pro.getId ());
		rslt.setSensorId(pro.getSensorId ());
		rslt.setBusId(pro.getBusId ());
		rslt.setStatusId(pro.getStatusId ());
		rslt.setVals(pro.getVals ());
		rslt.setHandled(pro.getHandled ());
		rslt.setDriverAlerm(pro.getDriverAlerm ());
		rslt.setStatusName(pro.getStatusName ());
		rslt.setTime(pro.getTime ());
		rslt.setCreateTime(pro.getCreateTime ());
		rslt.setModifyTime(pro.getModifyTime ());
		rslt.setDef1(pro.getDef1 ());
		rslt.setDef2(pro.getDef2 ());
		rslt.setDef3(pro.getDef3 ());
		rslt.setDef4(pro.getDef4 ());
		rslt.setDef5(pro.getDef5 ());
		rslt.setDef6(pro.getDef6 ());
		rslt.setDef7(pro.getDef7 ());
		rslt.setDef8(pro.getDef8 ());
		rslt.setDef9(pro.getDef9 ());
		rslt.setDef10(pro.getDef10 ());
		rslt.setDr(pro.getDr ());
		return rslt;
	}
	
	/**
	 * 获取报警处理记录
	 * @param alarm_id
	 * @return
	 */
	public static JSONObject getAlarmHandleRecord(String alarm_id){
		JSONObject rslt = new JSONObject();
		LzzAlarmHandleRecord record = LzzAlarmHandleRecordService.self()
				.getLzzAlarmHandleRecordByAlarmId(alarm_id);
		if(null==record) return null;
		rslt.put("id", record.getId());
		rslt.put("driverAlarmRecord", record.getDriverAlarmHandleRecord());
		rslt.put("sensorAlarmRecord", record.getSensorAlarmHandleRecord());
		return rslt;
	}
	
	/**
	 * 记录报警处理记录
	 * @param alarm_id
	 * @param driver_record
	 * @param sensor_record
	 * @param handler
	 */
	public static void recordAlarmHandleRecord(
			String alarm_id,
			String driver_valid,
			String driver_record,
			String sensor_valid,
			String sensor_record,
			String handler){
		LzzAlarmHandleRecord record = new LzzAlarmHandleRecord();
		record.setAlarmId(alarm_id);
		LzzSensorAlarmData alarm = LzzSensorDataService.self().getLzzSensorAlarmDataById(alarm_id);
		record.setBusId(alarm.getBusId());
		record.setIsDriverAlarmValid(driver_valid);
		record.setDriverAlarmHandleRecord(driver_record);
		record.setIsSensorAlarmValid(sensor_valid);
		record.setSensorAlarmHandleRecord(sensor_record);
		record.setHandler(handler);
		LzzAlarmHandleRecordService.self().saveLzzAlarmHandleRecord(record);
	}
}
