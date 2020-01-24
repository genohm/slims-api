package com.genohm.slims.custom.beans.authentication;

import java.io.IOException;
import java.util.Map;

import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genohm.slims.custom.beans.SpotifyService;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;

@Component
public class AccessTokenBean {
	
	@Autowired
	private SpotifyService spotifyService;
	
	@Handler
	public String handle(Message message) throws SpotifyWebApiException, IOException {
		Map<String, String> parameters = Maps.newHashMap();
		
		Splitter.on("&").split(
			message.getHeader("CamelHttpQuery", String.class)).forEach(el -> 
				parameters.put(el.split("=")[0], el.split("=")[1]));
		
		String state = parameters.get("state");
		String code = parameters.get("code");
		
		spotifyService.registerCode(state, code);
		return "authenticated!";
	}

}
