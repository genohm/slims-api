package com.genohm.slims.custom.beans.authentication;

import javax.annotation.PostConstruct;

import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genohm.slims.common.model.User;
import com.genohm.slims.custom.beans.SpotifyService;
import com.genohm.slims.server.dao.common.ActiveUser;
import com.genohm.slims.server.service.user.UserService;

@Component
public class GenerateSpotifyLoginLinkBean {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SpotifyService spotifyService;
	
	@PostConstruct
	public void postConstruct() {
		System.err.println(generateUrl(userService.findByName("admin")));
	}
	
	@Handler
  	public void handle() {
  		System.err.println(generateUrl(ActiveUser.get()));
  	}
	
	public String generateUrl(User user) {
		return spotifyService.generateAuthorizationCodeUri(user);
	}
  	
}
