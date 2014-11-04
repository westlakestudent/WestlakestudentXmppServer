/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.xmpp.push;

import java.util.Random;

import org.androidpn.server.model.NotifyMessage;
import org.androidpn.server.model.User;
import org.androidpn.server.service.ExistsException;
import org.androidpn.server.service.MsgService;
import org.androidpn.server.service.NotFoundException;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.UserService;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;

/**
 * This class is to manage sending the notifcations to the users.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationManager {

	private static final String NOTIFICATION_NAMESPACE = "androidpn:iq:notification";

	private final Log log = LogFactory.getLog(getClass());

	private static final String SENT = "SENT";

	private static final String NOT_SEND = "NOT_SEND";

	private SessionManager sessionManager;

	private MsgService msgService = null;

	private UserService userService = null;

	/**
	 * Constructor.
	 */
	public NotificationManager() {
		sessionManager = SessionManager.getInstance();
		msgService = ServiceLocator.getMsgService();
		userService = ServiceLocator.getUserService();
	}

	/**
	 * Broadcasts a newly created notification message to all connected users.
	 * 
	 * @param apiKey
	 *            the API key
	 * @param title
	 *            the title
	 * @param message
	 *            the message details
	 * @param uri
	 *            the uri
	 */
	public void sendBroadcast(String apiKey, String title, String message,
			String remark) {
		log.debug("sendBroadcast()...");
		IQ notificationIQ = createNotificationIQ(apiKey, title, message, remark);
		NotifyMessage recv_msg = new NotifyMessage();
		recv_msg.setMessage(message);
		recv_msg.setRemark(remark);
		recv_msg.setTitle(title);

		NotifyMessage unrecv_msg = new NotifyMessage();
		unrecv_msg.setMessage(message);
		unrecv_msg.setRemark(remark);
		unrecv_msg.setTitle(title);

		StringBuffer recv_buf = new StringBuffer();
		recv_buf.append(SENT + ":");

		StringBuffer unrecv_buf = new StringBuffer();
		unrecv_buf.append(NOT_SEND + ":");

		for (ClientSession session : sessionManager.getSessions()) {
			String username;
			String imei;
			User user = null;
			try {
				username = session.getUsername();
				user = userService.getUserByUsername(username);
			} catch (NotFoundException e) {
				log.error("没找到用户" + e);
			}
			if (session.getPresence().isAvailable()) {
				notificationIQ.setTo(session.getAddress());

				imei = user.getImei();
				recv_buf.append(imei + ",");
				user = null;
				session.deliver(notificationIQ);

			} else {
				imei = user.getImei();
				unrecv_buf.append(imei + ",");
				user = null;
			}
		}
		
//		for(ClientSession session : sessionManager.getClosedSessions()){
//			try {
//				String username = session.getUsername();
//				User user = userService.getUserByUsername(username);
//				String imei = user.getImei();
//				unrecv_buf.append(imei + ",");
//			} catch (NotFoundException e) {
//				log.error("closed session 没找到用户" + e);
//			}
//			
//		}
		
		try {
			msgService.insert(recv_msg);
			msgService.insert(unrecv_msg);
		} catch (ExistsException e) {
			log.equals("insert msg failed!");
		}
		
	}

	/**
	 * Sends a newly created notification message to the specific user.
	 * 
	 * @param apiKey
	 *            the API key
	 * @param title
	 *            the title
	 * @param message
	 *            the message details
	 * @param uri
	 *            the uri
	 */
	public void sendNotifcationToUser(String apiKey, String username,
			String title, String message, String remark) {
		log.debug("sendNotifcationToUser()...");
		IQ notificationIQ = createNotificationIQ(apiKey, title, message, remark);
		ClientSession session = sessionManager.getSession(username);
		if (session != null) {
			if (session.getPresence().isAvailable()) {
				notificationIQ.setTo(session.getAddress());
				session.deliver(notificationIQ);
			}
		}
	}

	/**
	 * Creates a new notification IQ and returns it.
	 */
	private IQ createNotificationIQ(String apiKey, String title,
			String message, String remark) {
		Random random = new Random();
		String id = Integer.toHexString(random.nextInt());
		// String id = String.valueOf(System.currentTimeMillis());

		Element notification = DocumentHelper.createElement(QName.get(
				"notification", NOTIFICATION_NAMESPACE));
		notification.addElement("id").setText(id);
		notification.addElement("apiKey").setText(apiKey);
		notification.addElement("title").setText(title);
		notification.addElement("message").setText(message);
		notification.addElement("remark").setText(remark);

		IQ iq = new IQ();
		iq.setType(IQ.Type.set);
		iq.setChildElement(notification);

		return iq;
	}
}
