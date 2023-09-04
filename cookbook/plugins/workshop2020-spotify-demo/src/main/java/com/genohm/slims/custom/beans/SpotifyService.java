package com.genohm.slims.custom.beans;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genohm.slims.common.model.User;
import com.genohm.slims.custom.SpotifyConfiguration;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.miscellaneous.Device;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;

@Component
public class SpotifyService {

	@Autowired
	private SpotifyConfiguration spotifyConfiguration;
	
	private final Map<String, Long> states = Maps.newHashMap();
	private final Map<Long, AuthorizationCodeCredentials> credentials = Maps.newHashMap();
	
	public String generateAuthorizationCodeUri(User user) {
		String state = UUID.randomUUID().toString();
		states.put(state, user.getUser_pk());
		
		List<String> scopes = Lists.newArrayList(
			"playlist-read-collaborative", //Access Collaborative playlists
			"playlist-read-private", //Access private playlists
			"user-modify-playback-state", //Change active song
			"user-read-playback-state" //Get devices
		);
		
		return unauthenticated().authorizationCodeUri()
				.scope(scopes.stream().collect(Collectors.joining(",")))
				.state(state)
				.build()
				.execute()
				.toString();
	}
	
	public void registerCode(String state, String code) throws SpotifyWebApiException, IOException {
		AuthorizationCodeCredentials credentials = 
			unauthenticated().authorizationCode(code)
				.build()
				.execute();
		
		this.credentials.put(states.get(state), credentials);
	}
	
	public List<PlaylistSimplified> getPlaylists(User user) throws SpotifyWebApiException, IOException {
		return Arrays.asList(authenticated(user).getListOfCurrentUsersPlaylists()
			.limit(50)
			.build()
			.execute()
			.getItems());  
	}
	
	public Playlist getPlaylist(String playlist, User user) throws SpotifyWebApiException, IOException {
		return authenticated(user)
				.getPlaylist(playlist)
				.build()
				.execute(); 
	}
	
	public List<Device> getDevices(User user) throws SpotifyWebApiException, IOException {
		return Arrays.asList(authenticated(user)
				.getUsersAvailableDevices()
				.build()
				.execute());
	}


	public void playTrack(String trackId, String deviceId, User user) throws SpotifyWebApiException, IOException {
		JsonArray tracks = new JsonArray();
		tracks.add(trackId);
		
		authenticated(user)
			.startResumeUsersPlayback()
			.uris(tracks)
			.device_id(deviceId)
			.build()
			.execute();
	}
	
	public List<Long> getAuthenticatedUsers() {
		return Lists.newArrayList(credentials.keySet());
	}
	
	private SpotifyApi unauthenticated() {
		return new SpotifyApi.Builder()
		          .setClientId(spotifyConfiguration.getClientId())
		          .setClientSecret(spotifyConfiguration.getClientSecret())
		          .setRedirectUri(URI.create(spotifyConfiguration.getRedirectUri()))
		          .build();
	}
	
	private SpotifyApi authenticated(User user) {
		AuthorizationCodeCredentials credentials = this.credentials.get(user.getUser_pk());
		if (credentials == null) {
			throw new SpotifyServiceException("User is not authenticated");
		}
		
		SpotifyApi spotifyApi = unauthenticated();
		spotifyApi.setAccessToken(credentials.getAccessToken());
		spotifyApi.setRefreshToken(credentials.getRefreshToken());
		return spotifyApi;
	}
	
	@SuppressWarnings("serial")
	public static final class SpotifyServiceException extends RuntimeException {
		public SpotifyServiceException(String message) {
			super(message);
		}
	}
	
}
