package org.androidpn.server.dao;

import java.util.List;

import org.androidpn.server.model.NotifyMessage;

/**
 *
 * NotifyMsgDao
 * @author chendong
 * 2014年10月31日 下午3:47:35
 * @version 1.0.0
 *
 */
public interface NotifyMsgDao {
	
	
	public String insert(NotifyMessage msg);//返回msg.toString()
	
	public String update(String imeis,long id);//返回更新后的msg.toString()
	
	public List<NotifyMessage> findAll();
	
	public List<NotifyMessage> findMsgByImei(String imei);
	
	public NotifyMessage findMsgById(long id);
	

}
