package com.lzz.bussecurity.utils;

public class LzzGps {
	private double lat;
	private double lng;
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public LzzGps(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
	@Override
	public String toString() {
		return "LzzGps [lat=" + lat + ", lng=" + lng + "]";
	}
	
}
