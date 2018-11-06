
package com.lzz.bussecurity.pojo;

/*
id     id

nextid     下次开始ID

createTime     创建时间

modifyTime     修改时间

dr     删除标志

*/
public class LzzID {

	private String id = "";

	private String nextid = "";

	private String createTime = "";

	private String modifyTime = "";

	private String dr = "0";

	
	public LzzID(){};
	
	public LzzID(LzzID obj){
	    id = obj.getId();

	    nextid = obj.getNextid();

	    createTime = obj.getCreateTime();

	    modifyTime = obj.getModifyTime();

	    dr = obj.getDr();

	}

	public String getId() {
	    return id;
	}
	public void setId(String id) {
	    this.id = id;
	}

	public String getNextid() {
	    return nextid;
	}
	public void setNextid(String nextid) {
	    this.nextid = nextid;
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

	public String getDr() {
	    return dr;
	}
	public void setDr(String dr) {
	    this.dr = dr;
	}


	public LzzID clone(){
		LzzID rslt = new LzzID();
		rslt.id = this.getId();

		rslt.nextid = this.getNextid();

		rslt.createTime = this.getCreateTime();

		rslt.modifyTime = this.getModifyTime();

		rslt.dr = this.getDr();

		return rslt;
	}
	public void constructWith(LzzID pro) {
		// TODO Auto-generated method stub
		this.id  = pro.getId ();

		this.nextid  = pro.getNextid ();

		this.createTime  = pro.getCreateTime ();

		this.modifyTime  = pro.getModifyTime ();

		this.dr  = pro.getDr ();

	}
}
