package org.androidpn.server.service;

import java.util.List;

import org.androidpn.server.model.NotifyMessage;

/**
 * 
 * MsgService
 * 
 * @author chendong 2014年10月31日 下午6:55:22
 * @version 1.0.0
 * 
 */
public interface MsgService {

	public String insert(NotifyMessage msg) throws ExistsException;// 返回msg.toString()

	public String update(String imeis, long id);// 返回更新后的msg.toString()

	public List<NotifyMessage> findAll();

	public List<NotifyMessage> findMsgByImei(String imei);

	public NotifyMessage findMsgById(long id) throws NotFoundException;
}
