// 创建地图实例  
var map = new BMap.Map("container");
// 创建点坐标  
var point = new BMap.Point(117.12420, 31.83068);
// 初始化地图，设置中心点坐标和地图级别  
map.centerAndZoom(point, 13);
//开启鼠标滚轮缩放
//map.enableScrollWheelZoom(true);

var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮

/*缩放控件type有四种类型:
BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/
add_control();

/**
 * 添加控件和比例尺
 */
function add_control(){
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);     
}

/**
 * 移除控件和比例尺
 */
function delete_control(){
	map.removeControl(top_left_control);   
	map.removeControl(top_left_navigation);
	map.removeControl(top_right_navigation);
}

/**
 * 给标注添加文字label
 * @param marker
 * @param text
 */
function addLabelForMarker(marker, text){
	var label = new BMap.Label(text, {offset:new BMap.Size(0, -20)});
	marker.setLabel(label);
}

/**
 * 设置marker的位置
 * @param marker
 * @param point_tmp
 */
function setMarkerPos(marker, point_tmp){
	console.log("set position");
	marker.setPosition(point_tmp);
}

/**
 * 创建一个marker
 * @param pos marker的位置
 */
function createMarker(pos){
	var marker = new BMap.Marker(pos);  // 创建标注
	return marker;
}

/**
 * 创建一个带label的marker
 * @param pos marker的位置
 * @param label 文字标注
 */
function createMarkerWithLabel(pos, label){
	var marker = new BMap.Marker(pos);  // 创建标注
	var label = new BMap.Label(text, {offset:new BMap.Size(0, -20)});
	marker.setLabel(label);
	return marker;
}

/**
 * 创建一个带label和自定义图片的的marker
 * @param pos marker的位置
 * @param label 文字标注
 * @param img_url 图标地址
 */
function createMarkerWithLabelAndIcon(pos, text, img_url){
	var icon = new BMap.Icon(img_url, new BMap.Size(30,30));
	var marker = new BMap.Marker(pos, {icon:icon});
	var label = new BMap.Label(text, {offset:new BMap.Size(0, -20)});
	marker.setLabel(label);
	return marker;
}

//地图添加覆盖物
function addOverLayer(id, lay){
	if(overlays[id]!=undefined){
		//避免重复绘制
		return false;
	}
	map.addOverlay(lay);
	overlays[id] = lay;
	return true;
}

//删除覆盖物
function removeOverLay(id){
	if(overlays[id]==undefined){
		return;
	}
	map.removeOverlay(overlays[id]);
	delete overlays[id];
}

//获取覆盖物
function getOverLayer(id){
	return overlays[id];
}

//清除覆盖物
function clearOverLayer(){
	for(var item in overlays){
		delete overlays[item];
	}
	map.clearOverlayers();
}

var overlays = {};
/**
 * 添加正常状态公交图标
 * @param id
 * @param busNum
 * @param lat
 * @param lng
 */
function addNormalBus(id, busNum, lng, lat){
	var marker = createMarkerWithLabelAndIcon(new BMap.Point(lng, lat), busNum, "./images/car-normal.jpg");
	addOverLayer(id, marker);
}

/**
 * 添加报警状态公交图标
 * @param id
 * @param busNum
 * @param lat
 * @param lng
 */
function addAlarmBus(id, busNum, lng, lat){
	var marker = createMarkerWithLabelAndIcon(new BMap.Point(lng, lat), busNum, "./images/car-alert.jpg");
	addOverLayer(id, marker);
}