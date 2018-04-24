package org.jboss.fuse.fis.workshop.beans;

import org.jboss.fuse.fis.workshop.config.RouteDslConfiguration;
import org.jboss.fuse.fis.workshop.config.RouteXmlConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Lab 02: ConfigMaps
 */
@Component
public class MessageBean {
	
	@Autowired
	private RouteDslConfiguration routeDslConfiguration;

	@Autowired
	private RouteXmlConfiguration routeXmlConfiguration;
	
	public String getGreetingMessage() {
		return routeDslConfiguration.getBody();
	}
	
	public String getGeneratedMessage() {
		return routeXmlConfiguration.getBody();
	}

}
