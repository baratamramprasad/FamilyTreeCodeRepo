package com.familytree.beans;

import java.util.List;

public class FamilyMemberAddedResponse {
	List<String> resMsg;

	public List<String> getResMsg() {
		return resMsg;
	}

	public void setResMsg(List<String> resMsg) {
		this.resMsg = resMsg;
	}
	
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(resMsg != null ?" Nbr Of Msg:"+resMsg.size() :"resMsg is Null");
		if(resMsg != null) {
			for(String msg:resMsg) {
				buf.append("\n msg: "+msg);
			}
		}
		return buf.toString();
	}

}
