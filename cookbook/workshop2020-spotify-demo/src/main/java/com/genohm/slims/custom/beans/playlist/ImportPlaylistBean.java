package com.genohm.slims.custom.beans.playlist;

import java.io.IOException;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genohm.slims.common.model.Content;
import com.genohm.slims.common.model.ContentMeta;
import com.genohm.slims.common.model.ContentType;
import com.genohm.slims.common.model.Order;
import com.genohm.slims.common.model.OrderContent;
import com.genohm.slims.common.model.OrderContentMeta;
import com.genohm.slims.common.model.OrderMeta;
import com.genohm.slims.common.model.Status;
import com.genohm.slims.common.util.DaoConstants;
import com.genohm.slims.common.util.StringUtil;
import com.genohm.slims.custom.beans.SpotifyService;
import com.genohm.slims.custom.beans.meta.SpotifyConstants;
import com.genohm.slims.server.dao.common.ActiveUser;
import com.genohm.slims.server.dao.common.Dao;
import com.genohm.slims.server.repository.queriers.ContentTypeQueries;
import com.genohm.slims.server.repository.queriers.StatusQueries;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateErrorException;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateKeeperConstants;
import com.genohm.slimsgate.camel.gatekeeper.SlimsProxy;
import com.genohm.slimsgateclient.workflow.SlimsFlowInitParam;
import com.google.common.collect.Maps;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;

@Component
public class ImportPlaylistBean {

	@Autowired
	private Dao<Content> contentDao;
	@Autowired
	private Dao<Order> orderDao;
	@Autowired
	private Dao<OrderContent> orderContentDao;
	
	@Autowired
	private ContentTypeQueries contentTypeQueries;
	@Autowired
	private StatusQueries statusQueries;
	
	@Autowired
	private SpotifyService spotifyService;
	
	@Handler
	@Transactional
	public void handle(
			@Header(SlimsGateKeeperConstants.SLIMS_WORKFLOW_INIT_PARAMETER) SlimsFlowInitParam slimsFlowInitParam,
			@Header(SlimsGateKeeperConstants.SLIMS_PROXY) SlimsProxy slimsProxy) 
	
			throws SpotifyWebApiException, IOException {
		String playlistId = StringUtil.getAsString(slimsFlowInitParam.getInputParameterValues().get("playlist"));
		Playlist playlist = spotifyService.getPlaylist(playlistId, ActiveUser.get());
		
		slimsProxy.setProgressMessage("Importing playlist");
		Long order = createOrder(playlist);
		
		slimsProxy.setProgressMessage("Importing tracks");
		slimsProxy.setProgressTotalUnits(playlist.getTracks().getItems().length);
		int i = 0;
		for (PlaylistTrack track: playlist.getTracks().getItems()) {
			slimsProxy.setProgressCurrentUnit(i++);
			createAndLinkContent(track, order);
		}
	}

	private Long createOrder(Playlist playlist) {
		Map<String, Object> order = Maps.newHashMap();
		order.put(OrderMeta.FK_ORDER_TYPE, 1L);
		order.put(SpotifyConstants.PLAYLIST, playlist.getName());
		
		Map<String, Object> createdOrder = orderDao.add(order);
		return StringUtil.getAsLong(createdOrder.get(OrderMeta.PK));
	}
	


	private void createAndLinkContent(PlaylistTrack track, Long orderPk) {
		ContentType song = contentTypeQueries.findByName("Song")
				.toJavaUtil()
				.orElseThrow(() -> new SlimsGateErrorException("No song content type"));
		
		Status pending = statusQueries.findStatusBySeqNo(10, DaoConstants.CONTENT)
				.toJavaUtil()
				.orElseThrow(() -> new SlimsGateErrorException("No pending status"));
		
		Map<String, Object> content = Maps.newHashMap();
		content.put(ContentMeta.ID, track.getTrack().getName());
		content.put(ContentMeta.FK_CONTENT_TYPE, song.getCntp_pk());
		content.put(ContentMeta.FK_STATUS, pending.getStts_pk());
		content.put(SpotifyConstants.TRACK_ALBUM, track.getTrack().getAlbum().getName());
		content.put(SpotifyConstants.TRACK_ARTIST, track.getTrack().getArtists()[0].getName());
		content.put(SpotifyConstants.TRACK_ID, track.getTrack().getUri());
		content.put(ContentMeta.FK_STATUS, pending.getStts_pk());
		Map<String, Object> createdContent = contentDao.add(content);
		
		Long contentPk = StringUtil.getAsLong(createdContent.get(ContentMeta.PK));
		
		Map<String, Object> orderContent = Maps.newHashMap();
		orderContent.put(OrderContentMeta.FK_CONTENT, contentPk);
		orderContent.put(OrderContentMeta.FK_ORDER, orderPk);
		orderContentDao.add(orderContent);
	}
	
}
