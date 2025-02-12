/*
 * Copyright 2021 Agilent Technologies Inc.
 */

package com.genohm.slims.custom.beans;

import com.genohm.slims.api.AddRequestService;
import com.genohm.slims.common.model.Content;
import com.genohm.slims.common.model.Order;
import com.genohm.slims.common.model.OrderContent;
import com.genohm.slims.common.model.OrderContentMeta;
import com.genohm.slims.common.model.OrderMeta;
import com.genohm.slims.common.model.OrderType;
import com.genohm.slims.common.model.Requestable;
import com.genohm.slims.common.model.User;
import com.genohm.slims.common.slimsgate.SlimsgateParameter;
import com.genohm.slims.common.util.StringUtil;
import com.genohm.slims.custom.CreateOrderCustomConfiguration;
import com.genohm.slims.server.dao.common.ActiveUser;
import com.genohm.slims.server.dao.common.Dao;
import com.genohm.slims.server.repository.queriers.ContentQueries;
import com.genohm.slims.server.repository.queriers.OrderTypeQueries;
import com.genohm.slims.server.repository.queriers.RequestableQueries;
import com.genohm.slims.server.service.order.ScheduleOrderService;
import com.genohm.slims.server.util.EntityFactoryUtil;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateErrorException;
import com.genohm.slimsgate.camel.gatekeeper.SlimsGateKeeperConstants;
import com.genohm.slimsgate.camel.gatekeeper.SlimsProxy;
import com.genohm.slimsgateclient.workflow.SlimsFlowInitParam;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CreateOrderAndSchedule {

  @Autowired private CreateOrderCustomConfiguration createOrderCustomConfiguration;

  @Autowired private OrderTypeQueries orderTypeQueries;
  @Autowired private ContentQueries contentQueries;
  @Autowired private RequestableQueries requestableQueries;

  @Autowired private Dao<Order> orderDao;
  @Autowired private Dao<OrderContent> orderContentDao;
  @Autowired private ScheduleOrderService scheduleOrderService;
  @Autowired private AddRequestService addRequestService;

  @Transactional
  @Handler
  public Map<String, Object> execute(
      @Header(SlimsGateKeeperConstants.SLIMS_PROXY) SlimsProxy slimsProxy,
      @Header(SlimsGateKeeperConstants.SLIMS_WORKFLOW_INIT_PARAMETER)
          SlimsFlowInitParam initParam) {
    // get logger from slimsProxy to allow logging to the UI (vs just to the server log file)
    Logger logger = slimsProxy.getLogger(getClass());

    // get the user running the plugin so the audit trail shows their user instead of the "Slimsgate" user
    User user = ActiveUser.get();

    // get content primary keys from the input parameters
    Map<String, Object> initParamValues = initParam.getInputParameterValues();
    List<Long> samplePks =
        Arrays.asList(
            StringUtil.getAsLongArray(
                initParamValues.get(SlimsgateParameter.SLIMS_SELECT_SAMPLES)));

    // query for Content records using the PKs of the selected content
    List<Content> sampleContentList = contentQueries.findByPks(samplePks);

    // get order type record using the unique identifier in the plugin configuration
    OrderType workflowOrderType =
        orderTypeQueries
            .findByUniqueIdentifier(createOrderCustomConfiguration.getOrderTypeUid())
            .toJavaUtil()
            .orElseThrow(
                () ->
                    new SlimsGateErrorException(
                        String.format(
                            "Could not find the order type with UID %s.",
                            createOrderCustomConfiguration.getOrderTypeUid())));

    // create an order by first making a map and then adding it to the database
    Map<String, Object> order = new HashMap<>();
    order.put(OrderMeta.FK_ORDER_TYPE, workflowOrderType.getRdtp_pk());  // add the order type PK to the map
    Map<String, Object> newOrderMap = orderDao.add(order, user);        // commit the order to the database

    // convert the order to a SLIMS entity in preparation for adding requests and scheduling
    Order newOrder = EntityFactoryUtil.createInstance(Order.class, newOrderMap);

    for (Content content : sampleContentList) {
      // create a new order content record and add it to the database. This is how the content is linked to the order
      Map<String, Object> newOrderContent = new HashMap<>();
      newOrderContent.put(OrderContentMeta.FK_CONTENT, content.getCntn_pk());
      newOrderContent.put(OrderContentMeta.FK_ORDER, newOrderMap.get(OrderMeta.PK));
      orderContentDao.add(newOrderContent, user);

      // query for the requestable defined in the configuration
      Requestable requestable =
              requestableQueries
                      .findByUniqueIdentifier(createOrderCustomConfiguration.getRequestableUid())
                      .toJavaUtil()
                      .orElseThrow(
                              () ->
                                      new SlimsGateErrorException(
                                              String.format(
                                                      "Could not find requestable with UID %s.",
                                                      createOrderCustomConfiguration.getRequestableUid())));

      // add the request to the content/order
      addRequestService.createWorkflowRequest(content, requestable, newOrder, new HashMap<>());
    }

    // schedule the order
    scheduleOrderService.scheduleWorkflowOrder(newOrder, user);

    // return the feedback map for display in the last step of the plugin
    return createFeedback(newOrder, sampleContentList);
  }

  private Map<String, Object> createFeedback(Order order, List<Content> samples) {
    Map<String, Object> feedback = new HashMap<>();
    StringBuilder feedbackSb = new StringBuilder();

    feedbackSb.append("<p>Created content barcodes</p>");
    feedbackSb.append("<ul>");
    samples.forEach(sample -> feedbackSb.append(String.format("<li>%s</li>", sample.getCntn_barCode())));
    feedbackSb.append("</ul>");

    feedbackSb.append("<br>");

    feedbackSb.append("<p>Order barcode</p>");
    feedbackSb.append(order.getOrdr_barCode());

    feedback.put("results", feedbackSb.toString());
    return feedback;
  }
}
