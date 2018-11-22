
package com.lzz.bussecurity.pojo;

import com.lzz.bussecurity.mgr.LzzIDMgr;

/*
id     id

lineId     线路ID

carNum     车牌号

lat     所在纬度

lng     所在经度

statusId     状态ID

ignoreAlerm     是否忽略警告

sensorStatus     传感器状态

sensorCount     传感器数量

driverFlag     一键报警开关状态

lastSensorDataTime     上一次收到传感器数据时间

createTime     创建时间

modifyTime     修改时间

def1     备用字段1-10

def2      

def3      

def4      

def5      

def6      

def7      

def8      

def9      

def10      

dr     删除标志

*/
public class LzzBus {

	private String id = "";

	private String lineId = "";

	private String carNum = "";

	private String lat = "";

	private String lng = "";

	private String statusId = "";

	private String ignoreAlerm = "";

	private String sensorStatus = "";

	private String sensorCount = "";

	private String driverFlag = "";

	private String lastSensorDataTime = "";

	private String createTime = "";

	private String modifyTime = "";

	private String def1 = "";

	private String def2 = "";

	private String def3 = "";

	private String def4 = "";

	private String def5 = "";

	private String def6 = "";

	private String def7 = "";

	private String def8 = "";

	private String def9 = "";

	private String def10 = "";

	private String dr = "0";

	
	public LzzBus(){
		this.id = LzzIDMgr.self().getID();
	}
	
	public LzzBus(boolean need_id){
		if(need_id){
			this.id = LzzIDMgr.self().getID();
		}
	}
	
	public LzzBus(LzzBus obj){
	    id = obj.getId();

	    lineId = obj.getLineId();

	    carNum = obj.getCarNum();

	    lat = obj.getLat();

	    lng = obj.getLng();

	    statusId = obj.getStatusId();

	    ignoreAlerm = obj.getIgnoreAlerm();

	    sensorStatus = obj.getSensorStatus();

	    sensorCount = obj.getSensorCount();

	    driverFlag = obj.getDriverFlag();

	    lastSensorDataTime = obj.getLastSensorDataTime();

	    createTime = obj.getCreateTime();

	    modifyTime = obj.getModifyTime();

	    def1 = obj.getDef1();

	    def2 = obj.getDef2();

	    def3 = obj.getDef3();

	    def4 = obj.getDef4();

	    def5 = obj.getDef5();

	    def6 = obj.getDef6();

	    def7 = obj.getDef7();

	    def8 = obj.getDef8();

	    def9 = obj.getDef9();

	    def10 = obj.getDef10();

	    dr = obj.getDr();

	}

	public String getId() {
	    return id;
	}
	public void setId(String id) {
	    this.id = id;
	}

	public String getLineId() {
	    return lineId;
	}
	public void setLineId(String lineId) {
	    this.lineId = lineId;
	}

	public String getCarNum() {
	    return carNum;
	}
	public void setCarNum(String carNum) {
	    this.carNum = carNum;
	}

	public String getLat() {
	    return lat;
	}
	public void setLat(String lat) {
	    this.lat = lat;
	}

	public String getLng() {
	    return lng;
	}
	public void setLng(String lng) {
	    this.lng = lng;
	}

	public String getStatusId() {
	    return statusId;
	}
	public void setStatusId(String statusId) {
	    this.statusId = statusId;
	}

	public String getIgnoreAlerm() {
	    return ignoreAlerm;
	}
	public void setIgnoreAlerm(String ignoreAlerm) {
	    this.ignoreAlerm = ignoreAlerm;
	}

	public String getSensorStatus() {
	    return sensorStatus;
	}
	public void setSensorStatus(String sensorStatus) {
	    this.sensorStatus = sensorStatus;
	}

	public String getSensorCount() {
	    return sensorCount;
	}
	public void setSensorCount(String sensorCount) {
	    this.sensorCount = sensorCount;
	}

	public String getDriverFlag() {
	    return driverFlag;
	}
	public void setDriverFlag(String driverFlag) {
	    this.driverFlag = driverFlag;
	}

	public String getLastSensorDataTime() {
	    return lastSensorDataTime;
	}
	public void setLastSensorDataTime(String lastSensorDataTime) {
	    this.lastSensorDataTime = lastSensorDataTime;
	}

	public String getCreateTime() {
	    return createTime;
	}
	public void setCreateTime(String createTime) {
	    this.createTime = createTime;
	}

	public String getModifyTime() {
	    return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
	    this.modifyTime = modifyTime;
	}

	public String getDef1() {
	    return def1;
	}
	public void setDef1(String def1) {
	    this.def1 = def1;
	}

	public String getDef2() {
	    return def2;
	}
	public void setDef2(String def2) {
	    this.def2 = def2;
	}

	public String getDef3() {
	    return def3;
	}
	public void setDef3(String def3) {
	    this.def3 = def3;
	}

	public String getDef4() {
	    return def4;
	}
	public void setDef4(String def4) {
	    this.def4 = def4;
	}

	public String getDef5() {
	    return def5;
	}
	public void setDef5(String def5) {
	    this.def5 = def5;
	}

	public String getDef6() {
	    return def6;
	}
	public void setDef6(String def6) {
	    this.def6 = def6;
	}

	public String getDef7() {
	    return def7;
	}
	public void setDef7(String def7) {
	    this.def7 = def7;
	}

	public String getDef8() {
	    return def8;
	}
	public void setDef8(String def8) {
	    this.def8 = def8;
	}

	public String getDef9() {
	    return def9;
	}
	public void setDef9(String def9) {
	    this.def9 = def9;
	}

	public String getDef10() {
	    return def10;
	}
	public void setDef10(String def10) {
	    this.def10 = def10;
	}

	public String getDr() {
	    return dr;
	}
	public void setDr(String dr) {
	    this.dr = dr;
	}


	public LzzBus clone(){
		LzzBus rslt = new LzzBus(false);
		rslt.id = this.getId();

		rslt.lineId = this.getLineId();

		rslt.carNum = this.getCarNum();

		rslt.lat = this.getLat();

		rslt.lng = this.getLng();

		rslt.statusId = this.getStatusId();

		rslt.ignoreAlerm = this.getIgnoreAlerm();

		rslt.sensorStatus = this.getSensorStatus();

		rslt.sensorCount = this.getSensorCount();

		rslt.driverFlag = this.getDriverFlag();

		rslt.lastSensorDataTime = this.getLastSensorDataTime();

		rslt.createTime = this.getCreateTime();

		rslt.modifyTime = this.getModifyTime();

		rslt.def1 = this.getDef1();

		rslt.def2 = this.getDef2();

		rslt.def3 = this.getDef3();

		rslt.def4 = this.getDef4();

		rslt.def5 = this.getDef5();

		rslt.def6 = this.getDef6();

		rslt.def7 = this.getDef7();

		rslt.def8 = this.getDef8();

		rslt.def9 = this.getDef9();

		rslt.def10 = this.getDef10();

		rslt.dr = this.getDr();

		return rslt;
	}
	public void constructWith(LzzBus pro) {
		// TODO Auto-generated method stub
		this.id  = pro.getId ();

		this.lineId  = pro.getLineId ();

		this.carNum  = pro.getCarNum ();

		this.lat  = pro.getLat ();

		this.lng  = pro.getLng ();

		this.statusId  = pro.getStatusId ();

		this.ignoreAlerm  = pro.getIgnoreAlerm ();

		this.sensorStatus  = pro.getSensorStatus ();

		this.sensorCount  = pro.getSensorCount ();

		this.driverFlag  = pro.getDriverFlag ();

		this.lastSensorDataTime  = pro.getLastSensorDataTime ();

		this.createTime  = pro.getCreateTime ();

		this.modifyTime  = pro.getModifyTime ();

		this.def1  = pro.getDef1 ();

		this.def2  = pro.getDef2 ();

		this.def3  = pro.getDef3 ();

		this.def4  = pro.getDef4 ();

		this.def5  = pro.getDef5 ();

		this.def6  = pro.getDef6 ();

		this.def7  = pro.getDef7 ();

		this.def8  = pro.getDef8 ();

		this.def9  = pro.getDef9 ();

		this.def10  = pro.getDef10 ();

		this.dr  = pro.getDr ();

	}
}
