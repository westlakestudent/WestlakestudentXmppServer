package org.androidpn.server;

import java.util.List;

import javax.servlet.ServletContextEvent;

import org.androidpn.server.model.User;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

/**
 *
 * ConfigServlert
 * @author chendong
 * 2014年11月10日 下午3:19:46
 * @version 1.0.0
 *
 */
public class ConfigContextLoader extends ContextLoaderListener {

	private static final Log log = LogFactory.getLog(ConfigContextLoader.class);
	
	private UserService userService = null;
	
	public void contextDestroyed(ServletContextEvent event) {
		userService = ServiceLocator.getUserService();
		List<User> users = userService.getUsers();
		if(users == null || users.isEmpty())
			return;
		for(User user : users){
			if(user.getAuthed() == 1){
				userService.updateAuthed(user, 0);
				log.info("user : " + user.getUsername() + "is still online,changed authed!");
			}
		}
		log.info("Tomcat destroyed");
	}

	public void contextInitialized(ServletContextEvent event) {
		log.info("Tomcat Initialized");
	}

	
}
