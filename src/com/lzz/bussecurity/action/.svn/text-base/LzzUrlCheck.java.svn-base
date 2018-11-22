package com.lzz.bussecurity.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import net.sf.json.JSONObject;

public class LzzUrlCheck extends LzzBaseManagerAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8770045686814143327L;

	public String urlCheck(){
		JSONObject rsl = new JSONObject();
		try {
			rsl.put("success", true);
		} catch (Exception e) {
			rsl.put("success", false);
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			rsl.put("errormsg", sw.toString());
		} finally {
			PrintWriter writer;
			try {
				response.setCharacterEncoding("UTF-8");
				writer = response.getWriter();
				writer.append(rsl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
