package com.trading.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.trading.app.dto.ResponseDTO;
import com.trading.app.dto.UserOfferDTO;
import com.trading.app.service.OfferService;
import com.trading.app.util.TradingAppUtil;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import static com.trading.app.util.SwaggerConstant.OFFER_RESOURCE_INFO;
import static com.trading.app.util.SwaggerConstant.SUCCESS_OPERATION;
import static com.trading.app.util.SwaggerConstant.FORBIDDEN_OPERATION;
import static com.trading.app.util.SwaggerConstant.NOT_FOUND_OPERATION;
import static com.trading.app.util.SwaggerConstant.NOT_AUTHORIZED_OPERATION;
import static com.trading.app.util.SwaggerConstant.SAVE_OFFER_PARAM;
import static com.trading.app.util.SwaggerConstant.SAVE_OFFER_INFO;
import static com.trading.app.util.SwaggerConstant.VIEW_OFFER_PARAM;
import static com.trading.app.util.SwaggerConstant.VIEW_OFFER_INFO;
import static com.trading.app.util.SwaggerConstant.ACCEPT_OFFER_PARAM;
import static com.trading.app.util.SwaggerConstant.ACCEPT_OFFER_INFO;
import static com.trading.app.util.SwaggerConstant.CANCEL_OFFER_INFO;
import static com.trading.app.util.SwaggerConstant.CANCEL_OFFER_PARAM;
import static com.trading.app.util.TradingAppConstant.OFFER_CANCELLED_STATUS;
import static com.trading.app.util.TradingAppConstant.OFFER_ACCEPTED_STATUS;

@RestController
@RequestMapping(path = "/offer")
@Api(tags = { OFFER_RESOURCE_INFO })
public class OfferResource {

	Logger logger = LoggerFactory.getLogger(OfferResource.class);

	@Autowired
	private OfferService offerService;
	@Autowired
	private TradingAppUtil tradingUtil;

	@RequestMapping(path = "/saveOffer", method = RequestMethod.POST)
	@ApiOperation(value = SAVE_OFFER_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> saveOffer(
			@ApiParam(value = SAVE_OFFER_PARAM, required = true) @RequestBody UserOfferDTO userOfferDTO) {
		logger.info("SaveOffer Api received Request with payload " + tradingUtil.convertObjectToString(userOfferDTO));
		ResponseDTO<?> response = offerService.saveOffer(userOfferDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/getOfferDetails", method = RequestMethod.POST)
	@ApiOperation(value = VIEW_OFFER_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> viewOfferDetails(
			@ApiParam(value = VIEW_OFFER_PARAM, required = true) @RequestBody UserOfferDTO userOfferDTO) {
		logger.info("viewOfferDetails Api received Request with payload "
				+ tradingUtil.convertObjectToString(userOfferDTO));
		ResponseDTO<?> response = offerService.getOfferDetails(userOfferDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/cancelOffer", method = RequestMethod.POST)
	@ApiOperation(value = CANCEL_OFFER_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> cancelOffer(
			@ApiParam(value = CANCEL_OFFER_PARAM, required = true) @RequestBody UserOfferDTO userOfferDTO) {
		logger.info("CancelOffer Api received Request with payload " + tradingUtil.convertObjectToString(userOfferDTO));
		ResponseDTO<?> response = offerService.changeOfferStatus(userOfferDTO, OFFER_CANCELLED_STATUS);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/acceptOffer", method = RequestMethod.POST)
	@ApiOperation(value = ACCEPT_OFFER_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> acceptOffer(
			@ApiParam(value = ACCEPT_OFFER_PARAM, required = true) @RequestBody UserOfferDTO userOfferDTO) {
		logger.info("acceptOffer Api received Request with payload " + tradingUtil.convertObjectToString(userOfferDTO));
		ResponseDTO<?> response = offerService.changeOfferStatus(userOfferDTO, OFFER_ACCEPTED_STATUS);
		return ResponseEntity.ok().body(response);
	}

}
