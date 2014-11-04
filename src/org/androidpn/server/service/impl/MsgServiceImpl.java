package org.androidpn.server.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.androidpn.server.dao.NotifyMsgDao;
import org.androidpn.server.model.NotifyMessage;
import org.androidpn.server.service.ExistsException;
import org.androidpn.server.service.MsgService;
import org.androidpn.server.service.NotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * MsgServiceImpl
 * @author chendong
 * 2014年10月31日 下午6:57:50
 * @version 1.0.0
 *
 */
public class MsgServiceImpl implements MsgService {

	protected final Log log = LogFactory.getLog(getClass());
	
	private NotifyMsgDao notifyMsgDao = null;
	
	
	//Spring 中配置

	public String insert(NotifyMessage msg) throws ExistsException {
		try{
			return notifyMsgDao.insert(msg);
		}catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ExistsException("msg '" + msg.getId()
                    + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new ExistsException("msg '" + msg.getId()
                    + "' already exists!");
        }
	}

	public void setNotifyMsgDao(NotifyMsgDao notifyMsgDao) {
		this.notifyMsgDao = notifyMsgDao;
	}

	public String update(String imeis, long id) {
		return notifyMsgDao.update(imeis, id);
	}

	public List<NotifyMessage> findAll() {
		return notifyMsgDao.findAll();
	}

	public List<NotifyMessage> findMsgByImei(String imei) {
		return notifyMsgDao.findMsgByImei(imei);
	}

	public NotifyMessage findMsgById(long id) throws NotFoundException {
		return notifyMsgDao.findMsgById(id);
	}

}
