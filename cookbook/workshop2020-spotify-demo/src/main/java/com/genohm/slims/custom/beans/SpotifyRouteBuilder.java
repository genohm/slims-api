package com.genohm.slims.custom.beans;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SpotifyRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("restlet:http://0.0.0.0:55555/code")
			.to("bean:accessTokenBean")
			.routeId("receive-spotify-code");
		
		from("direct:get-user-playlists")
			.to("bean:getPlaylistsBean")
			.routeId("get-user-playlists");
		
		from("direct:import-playlist")
			.to("bean:importPlaylistBean")
			.routeId("import-playlist");
		
		from("quartz://spotify/importDevices?cron=0+*+*+*+*+?")
			.to("bean:importDeviceBean")
			.routeId("import-devices");
		
		from("direct:play-track")
			.to("bean:playTrackBean")
			.routeId("play-track");
		
		from("direct:measure-appreciation")
			.to("bean:measureAppreciationBean")
			.routeId("measure-appreciation");
	}

}
