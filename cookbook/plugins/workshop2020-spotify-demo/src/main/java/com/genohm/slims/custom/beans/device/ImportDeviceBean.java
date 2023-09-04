package com.genohm.slims.custom.beans.device;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genohm.slims.common.model.Instrument;
import com.genohm.slims.common.model.InstrumentMeta;
import com.genohm.slims.common.model.InstrumentType;
import com.genohm.slims.common.model.Status;
import com.genohm.slims.common.model.User;
import com.genohm.slims.common.util.DaoConstants;
import com.genohm.slims.common.util.StringUtil;
import com.genohm.slims.custom.beans.SpotifyService;
import com.genohm.slims.custom.beans.meta.SpotifyConstants;
import com.genohm.slims.server.dao.common.ActiveUser;
import com.genohm.slims.server.dao.common.Dao;
import com.genohm.slims.server.repository.queriers.InstrumentRecordQueries;
import com.genohm.slims.server.repository.queriers.InstrumentTypeQueries;
import com.genohm.slims.server.repository.queriers.StatusQueries;
import com.genohm.slims.server.repository.queriers.UserQueries;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateErrorException;
import com.google.common.collect.Maps;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.miscellaneous.Device;

@Component
public class ImportDeviceBean {
	
	@Autowired
	private InstrumentTypeQueries instrumentTypeQueries;
	@Autowired
	private InstrumentRecordQueries instrumentRecordQueries;
	@Autowired
	private StatusQueries statusQueries;
	@Autowired
	private UserQueries userQueries;
	
	@Autowired
	private Dao<Instrument> instrumentDao;
	
	@Autowired
	private SpotifyService spotifyService;
	
	@Transactional
	@Handler
	public void importDevices() throws SpotifyWebApiException, IOException {
		for (Long userPk: spotifyService.getAuthenticatedUsers()) {
			User user = userQueries.findByPk(userPk).get();
			ActiveUser.set(user);
			
			Set<String> knownDevices = instrumentRecordQueries
					.findAll()
					.stream()
					.map(el -> StringUtil.getAsString(el.get(SpotifyConstants.DEVICE_ID)))
					.collect(Collectors.toSet());
			
			InstrumentType deviceType = instrumentTypeQueries.findByName("Device")
					.toJavaUtil()
					.orElseThrow(() -> new SlimsGateErrorException("Device instrument type not found"));
			
			Status available = statusQueries.findStatusBySeqNo(10, DaoConstants.INSTRUMENT)
					.toJavaUtil()
					.orElseThrow(() -> new SlimsGateErrorException("Available status not found"));

			
			List<Device> devices = spotifyService.getDevices(user);
			for (Device device: devices) {
				if (!knownDevices.contains(device.getId())) {
					Map<String, Object> newInstrument = Maps.newHashMap();
					newInstrument.put(InstrumentMeta.NAME, device.getName());
					newInstrument.put(InstrumentMeta.FK_INSTRUMENT_TYPE, deviceType.getNstp_pk());
					newInstrument.put(InstrumentMeta.CALIBRATED, false);
					newInstrument.put(InstrumentMeta.FK_STATUS, available.getStts_pk());

					newInstrument.put(SpotifyConstants.DEVICE_ID, device.getId());
					newInstrument.put(SpotifyConstants.DEVICE_TYPE, device.getType());
					newInstrument.put(SpotifyConstants.DEVICE_OWNER, user.getUser_pk());
					
					instrumentDao.add(newInstrument, user);
				}
			}
		}
	}
	
}
