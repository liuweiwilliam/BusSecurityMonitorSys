package com.lzz.bussecurity.utils;

/**
 * @author	LiuWei
 * @date	2071-07-10
 * @describe	用于存放常量字符串
 */
public class LzzConstString {
	/**
	 * 1、变量命名以"sm" 打头(static member)
	 * 2、变量命中尽量包含模块以及变量含义（Test 模块中的 Example 含义的变量，命名为  "smTestExample"）
	 * 		并标明变量含义
	 * 3、添加新变量时请搜索此类中是否已存在相同的字符串，并判断应用场景是否相同
	 * 		如果相同，则可直接使用；
	 * 		若不相同，可以直接新建（不推荐），和历史存在的作者进行沟通
	 */
	
	//文件类型
	public final static String smFileType_Image = "图片文件";
	public final static String smFileType_Audio = "音频文件";
	public final static String smFileType_Video = "视频文件";
	public final static String smFileType_Normal = "普通文件";
		
	final public static String smSensorDataStatus = "传感器数据状态";
	final public static String smSensorDataStatus_SensorAlarm = "传感器报警";
	final public static String smSensorDataStatus_SensorFault = "传感器故障";
	final public static String smSensorDataStatus_NoSensor = "传感器未安装";
	final public static String smSensorDataStatus_DriverAlarm = "司机报警";
	final public static String smSensorDataStatus_SensorAndDriverAlarm = "司机和传感器同时报警";
	final public static String smSensorDataStatus_normal = "正常";
	final public static String smSensorDataStatus_AlarmHandling = "报警处理中";
	
}
