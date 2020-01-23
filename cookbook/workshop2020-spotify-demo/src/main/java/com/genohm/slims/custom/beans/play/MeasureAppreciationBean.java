package com.genohm.slims.custom.beans.play;

import java.util.Map;

import javax.transaction.Transactional;

import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.genohm.slims.common.model.Result;
import com.genohm.slims.common.model.ResultMeta;
import com.genohm.slims.common.util.StringUtil;
import com.genohm.slims.server.dao.common.Dao;
import com.genohm.slims.server.quantity.Quantity;
import com.genohm.slims.server.quantity.Unit;
import com.genohm.slims.server.repository.queriers.ResultRecordQueries;
import com.genohm.slims.server.service.unit.UnitService;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateKeeperConstants;
import com.genohm.slimsgateclient.workflow.SlimsFlowInitParam;

@Component
public class MeasureAppreciationBean {

	@Autowired
	private ResultRecordQueries resultRecordQueries;
	
	@Autowired
	private Dao<Result> resultDao;
	
	@Autowired
	private UnitService unitService;
	
	@Handler
	@Transactional
	public void handle(@Header(SlimsGateKeeperConstants.SLIMS_WORKFLOW_INIT_PARAMETER) SlimsFlowInitParam slimsFlowInitParam) {
		Long resultPk = StringUtil.getAsLongArray(slimsFlowInitParam.getInputParameterValues().get("SLIMS_SELECT_RESULT"))[0];
		Map<String, Object> result = resultRecordQueries.findByPk(resultPk)
				.get();
		
		String starAmount = StringUtil.getAsString(slimsFlowInitParam.getInputParameterValues()
				.get("stars"));
		
		Unit starUnit = unitService.convertToUnit("Stars");
		
		result.put(ResultMeta.VALUE, new Quantity(starAmount, starUnit));
		resultDao.update(result);
	}
	
}
