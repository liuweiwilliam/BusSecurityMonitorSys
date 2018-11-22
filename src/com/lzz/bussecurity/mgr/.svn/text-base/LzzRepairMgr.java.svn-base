package com.lzz.bussecurity.mgr;

import com.lzz.bussecurity.pojo.LzzRepair;
import com.lzz.bussecurity.service.LzzRepairService;

public class LzzRepairMgr {
	/**
	 * 判断车辆是否有相同的故障信息存在
	 * @param bus_id
	 * @param comment
	 * @return
	 */
	public static boolean hasSameRepair(String bus_id, String comment){
		LzzRepair repair = LzzRepairService.self().getLzzRepairByBusId(bus_id);
		if(isSameRepair(repair, comment)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 获取指定车辆的故障记录
	 * @param bus_id
	 * @return
	 */
	public static LzzRepair getBusRepair(String bus_id){
		return LzzRepairService.self().getLzzRepairByBusId(bus_id);
	}
	
	/**
	 * 判断故障信息是否相同
	 * @param repair
	 * @param comment
	 * @return
	 */
	private static boolean isSameRepair(LzzRepair repair, String comment) {
		if(null==repair && null!=comment && !"".equals(comment)) return false;
		return repair.getComment().equals(comment);
	}
	
	/**
	 * 创建新的故障记录
	 * @param bus_id
	 * @param comment
	 * @return
	 */
	public static String createNewRepair(String bus_id, String comment){
		if(hasSameRepair(bus_id, comment)){//有相同的报警记录，直接返回
			LzzRepair repair = LzzRepairService.self().getLzzRepairByBusId(bus_id);
			return repair.getId();
		}
		
		LzzRepair repair = getBusRepair(bus_id);
		if(null==repair){
			repair = new LzzRepair();
			repair.setBusId(bus_id);
			repair.setComment(comment);
			LzzRepairService.self().saveLzzRepair(repair);
		}else{
			repair.setComment(comment);
			LzzRepairService.self().updateLzzRepair(repair);
		}
		
		return repair.getId();
	}
	
	/**
	 * 删除某车辆的故障记录
	 * @param bus_id
	 */
	public static void delBusRepair(String bus_id){
		LzzRepair repair = getBusRepair(bus_id);
		if(null!=repair){
			repair.setDr("1");
			LzzRepairService.self().updateLzzRepair(repair);
		}
	}
}
