package com.genohm.slims.custom;

public class SpotifyConfiguration {

	private String clientId;
	private String clientSecret;
	private String redirectUri;

	public SpotifyConfiguration() {

	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}
	
	public String getRedirectUri() {
		return redirectUri;
	}

	public static SpotifyConfiguration getDefault() {
		SpotifyConfiguration customConfiguration = new SpotifyConfiguration();
		customConfiguration.clientId = "<fill>";
		customConfiguration.clientSecret = "<fill>";
		customConfiguration.redirectUri = "<fill>";
		return customConfiguration;
	}

}
