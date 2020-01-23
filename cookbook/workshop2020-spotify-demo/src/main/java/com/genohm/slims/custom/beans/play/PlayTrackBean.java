package com.genohm.slims.custom.beans.play;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genohm.slims.common.model.InstrumentRunMeta;
import com.genohm.slims.common.model.ResultMeta;
import com.genohm.slims.common.util.StringUtil;
import com.genohm.slims.custom.beans.SpotifyService;
import com.genohm.slims.custom.beans.meta.SpotifyConstants;
import com.genohm.slims.server.dao.common.ActiveUser;
import com.genohm.slims.server.repository.queriers.ContentRecordQueries;
import com.genohm.slims.server.repository.queriers.InstrumentRecordQueries;
import com.genohm.slims.server.repository.queriers.InstrumentRunRecordQueries;
import com.genohm.slims.server.repository.queriers.ResultRecordQueries;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateErrorException;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateKeeperConstants;
import com.genohm.slimsgateclient.workflow.SlimsFlowInitParam;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;

@Component
public class PlayTrackBean {

	
	@Autowired
	private ContentRecordQueries contentRecordQueries;
	@Autowired
	private InstrumentRecordQueries instrumentRecordQueries;
	@Autowired
	private InstrumentRunRecordQueries instrumentRunRecordQueries;
	@Autowired
	private ResultRecordQueries resultRecordQueries;
	
	@Autowired
	private SpotifyService spotifyService;
	
	@Handler
	@Transactional
	public void handle(@Header(SlimsGateKeeperConstants.SLIMS_WORKFLOW_INIT_PARAMETER) SlimsFlowInitParam slimsFlowInitParam ) 
			throws SpotifyWebApiException, IOException {
		
		Long resultPk = StringUtil.getAsLongArray(slimsFlowInitParam.getInputParameterValues().get("SLIMS_SELECT_RESULT"))[0];
		Map<String, Object> result = resultRecordQueries.findByPk(resultPk)
				.get();
		
		Long contentPk = StringUtil.getAsLong(result.get(ResultMeta.FK_CONTENT));
		Long experimentRunStepPk = StringUtil.getAsLong(result.get(ResultMeta.FK_EXPERIMENT_RUN_STEP));
		
		List<Map<String, Object>> instrumentRuns = instrumentRunRecordQueries.findByExperimentRunStep(experimentRunStepPk);
		if (instrumentRuns.isEmpty()) {
			throw new SlimsGateErrorException("Select a device first!");
		}
		
		Long instrumentPk = StringUtil.getAsLong(instrumentRuns.get(0).get(InstrumentRunMeta.FK_INSTRUMENT));
		
		String trackId = StringUtil.getAsString(contentRecordQueries.findByPk(contentPk)
				.get()
				.get(SpotifyConstants.TRACK_ID));
		
		String deviceId = StringUtil.getAsString(instrumentRecordQueries.findByPk(instrumentPk)
				.get()
				.get(SpotifyConstants.DEVICE_ID));
		
		
		spotifyService.playTrack(trackId, deviceId, ActiveUser.get());
		
	}
	
}
