package com.genohm.slims.custom.beans.playlist;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genohm.slims.custom.beans.SpotifyService;
import com.genohm.slims.server.dao.common.ActiveUser;
import com.google.common.collect.Maps;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;

@Component
public class GetPlaylistsBean {

	@Autowired
	private SpotifyService spotifyService;
	
	@Handler
	public List<Map<String, Object>> handle() throws SpotifyWebApiException, IOException {

		List<PlaylistSimplified> playlists = spotifyService.getPlaylists(ActiveUser.get());
			
		return playlists.stream()
				.map(playlist -> {
					Map<String, Object> playListMap = Maps.newHashMap();
					playListMap.put("id", playlist.getId());
					playListMap.put("name", playlist.getName());
					return playListMap;
				})
				.collect(Collectors.toList());
	}
	
}
