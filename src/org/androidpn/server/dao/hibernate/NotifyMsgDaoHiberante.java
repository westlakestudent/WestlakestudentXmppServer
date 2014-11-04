package org.androidpn.server.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.androidpn.server.dao.NotifyMsgDao;
import org.androidpn.server.model.NotifyMessage;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * NotifyMsgDaoHiberante
 * @author chendong
 * 2014年10月31日 下午4:00:18
 * @version 1.0.0
 *
 */
public class NotifyMsgDaoHiberante extends HibernateDaoSupport implements
		NotifyMsgDao {

	public String insert(NotifyMessage msg) {
		getHibernateTemplate().save(msg);
		getHibernateTemplate().flush();
		return msg.toString();
	}

	public String update(String imeis,long id) {
		NotifyMessage msg = findMsgById(id);
		String result = "";
		if(msg != null){
			msg.setImeis(imeis);
			getHibernateTemplate().update(msg);
			result = msg.toString();
		}
		return result; 
	}

	@SuppressWarnings("unchecked")
	public List<NotifyMessage> findAll() {
		return getHibernateTemplate().find("from NotifyMessage msg order by msg.createDate desc");
	}

	public List<NotifyMessage> findMsgByImei(String imei) {
		List<NotifyMessage> msgs = findAll();
		List<NotifyMessage> results = new ArrayList<NotifyMessage>();
		if(msgs == null)
			return null;
		for(NotifyMessage msg: msgs){
			if(msg.getImeis().contains(imei))
				results.add(msg);
		}
		return results;
	}

	public NotifyMessage findMsgById(long id) {
		return (NotifyMessage) getHibernateTemplate().get(NotifyMessage.class, id);
	}

}
