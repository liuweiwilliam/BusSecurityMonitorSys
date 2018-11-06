package com.lzz.bussecurity.action.media;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.InputStream;  
import java.io.OutputStream;  

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;  
import org.hibernate.Transaction;

import com.lzz.bussecurity.action.LzzBaseManagerAction;
import com.lzz.bussecurity.factory.LzzFactory;
import com.lzz.bussecurity.mgr.LzzCacheMgr;
import com.lzz.bussecurity.mgr.LzzFileMgr;
import com.lzz.bussecurity.mgr.LzzIDMgr;
import com.lzz.bussecurity.pojo.LzzFileInfo;
import com.lzz.bussecurity.service.LzzFileService;
import com.lzz.bussecurity.utils.LzzConstString;
import com.lzz.bussecurity.utils.LzzDateUtil;
import com.lzz.bussecurity.utils.LzzFileUtil;
import com.lzz.bussecurity.utils.LzzProperties;
/**
 * 文件上传下载类
 * @author 	LiuWei
 * @date	2017-8-9
 */
public class LzzFileUploadAction extends LzzBaseManagerAction{  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;  
    //封装上传文件域的属性  
    private File file;  
    //封装上传文件类型的属性  
    private String fileContentType;  
    //封装上传文件名的属性  
    private String fileFileName;  
    //提示信息  
    private String json_message;  
	
    private String type;
    
	public String upload() throws Exception{
		String sepa = File.separator;
		String root_path = ServletActionContext.getServletContext().getRealPath(sepa);
		String save_path = ServletActionContext.getServletContext().getRealPath(LzzProperties.getFILE_UPLOAD_PATH());
		File saveFile=new File(save_path);
		if(!saveFile.exists()){
            saveFile.mkdirs();
        }
		String date = sepa + LzzDateUtil.getNow("d") + sepa;
		save_path += date;
		
		saveFile=new File(save_path);
		if(!saveFile.exists()){
            saveFile.mkdirs();
        }
		
		JSONObject re_rslt = new JSONObject();
		InputStream is = new FileInputStream(file);

		System.out.println("fileFileName: " + fileFileName);
		System.out.println("type: " + type);
		//获取文件后缀  
		String fileSuffix=fileFileName.substring(fileFileName.indexOf("."));
		String fileId = LzzIDMgr.self().getID();
		
		
		String new_name = fileId + fileSuffix;
		
		OutputStream os = new FileOutputStream(new File(save_path, new_name));

        byte[] buffer = new byte[2000];
        int length = 0;
        
        while(-1 != (length = is.read(buffer, 0, buffer.length)))
        {
            os.write(buffer);
        }
        
        os.close();
        is.close();

        Transaction ts = LzzFactory.currentSession().beginTransaction();
        try{
        	save_path = save_path + new_name;
        	int index = save_path.indexOf(LzzProperties.getPROJECT_NAME());
			String path_tmp = save_path.substring(index + LzzProperties.getPROJECT_NAME().length() + 1);
			String url_path = LzzProperties.getProjectUrl() + path_tmp;
        	
        	LzzFileMgr.addFileInfo(fileId, fileFileName, save_path, path_tmp);
        	
        	re_rslt.put("id", fileId);
    		re_rslt.put("filename", fileFileName);
    		
    		re_rslt.put("path", url_path);
    		re_rslt.put("type", LzzFileUtil.self().typeOf(fileFileName));
    		
        	ts.commit();
        }catch(Exception e){
        	e.printStackTrace();
        	ts.rollback();
        	LzzCacheMgr.reloadAllCache();
        }finally{
        	LzzFactory.closeSession();
        }

        HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			response.getWriter().append(re_rslt.toString());
			response.getWriter().close();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private InputStream downloadFile;
	private String fileName;
	private String fileId;
	public InputStream getDownloadFile() throws FileNotFoundException
    {
		LzzFileInfo file_info = LzzFileService.self().getLzzFileInfoById(fileId);
		fileName = file_info.getFname();
		String path = file_info.getFsavepath();
		File file = new File(path);
		InputStream inputStream = new FileInputStream(file);
        return inputStream;
    }

	public String download(){
		return SUCCESS;
	}
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public File getFile() {  
        return file;  
    }  
  
    public void setFile(File file) {  
        this.file = file;  
    }  
  
    public String getFileContentType() {  
        return fileContentType;  
    }  
  
    public String getJson_message() {  
        return json_message;  
    }  
    public void setJson_message(String jsonMessage) {  
        json_message = jsonMessage;  
    }  
    public void setFileContentType(String fileContentType) {  
        this.fileContentType = fileContentType;  
    }  
  
    public String getFileFileName() {  
        return fileFileName;  
    }  
  
    public void setFileFileName(String fileFileName) {  
        this.fileFileName = fileFileName;  
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}  
	
	public static void main(String[] args) throws Exception {
		new LzzFileUploadAction().upload();
	}
}  