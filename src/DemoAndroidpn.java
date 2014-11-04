import org.androidpn.server.dao.hibernate.NotifyMsgDaoHiberante;
import org.androidpn.server.model.NotifyMessage;
import org.androidpn.server.xmpp.push.NotificationManager;

//
//  DemoAndroidpn.java
//  FeOA
//
//  Created by LuTH on 2012-3-26.
//  Copyright 2012 flyrise. All rights reserved.
//

public class DemoAndroidpn {

	public static void main(String[] args) {

		NotifyMessage msg = new NotifyMessage();
		msg.setMessage("fuck");
		msg.setRemark("remark");
		msg.setTitle("title");
		msg.setImeis("1241328943");
		NotifyMsgDaoHiberante dao = new NotifyMsgDaoHiberante();
		
		dao.insert(msg);
//		String apiKey = "1234567890";
//		String title = "feoa";
//		String message = "Hello World!";
//		String uri = "http://www.baidu.com";
//
//		NotificationManager notificationManager = new NotificationManager();
//		notificationManager.sendBroadcast(apiKey, title, message, uri);
		// notificationManager.sendNotifcationToUser(apiKey, username, title,
		// message, uri);

	}
}
