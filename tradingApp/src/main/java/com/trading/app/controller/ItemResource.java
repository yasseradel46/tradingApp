package com.trading.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.app.dto.ItemCommentDTO;
import com.trading.app.dto.ItemDTO;
import com.trading.app.dto.ResponseDTO;
import com.trading.app.service.ItemService;
import com.trading.app.util.TradingAppUtil;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import static com.trading.app.util.SwaggerConstant.ITEM_RESOURCE_INFO;
import static com.trading.app.util.SwaggerConstant.SUCCESS_OPERATION;
import static com.trading.app.util.SwaggerConstant.FORBIDDEN_OPERATION;
import static com.trading.app.util.SwaggerConstant.NOT_FOUND_OPERATION;
import static com.trading.app.util.SwaggerConstant.NOT_AUTHORIZED_OPERATION;
import static com.trading.app.util.SwaggerConstant.SAVE_ITEM_INFO;
import static com.trading.app.util.SwaggerConstant.SAVE_ITEM_PARAM;
import static com.trading.app.util.SwaggerConstant.VIEW_ITEM_INFO;
import static com.trading.app.util.SwaggerConstant.VIEW_ITEM_PARAM;
import static com.trading.app.util.SwaggerConstant.DELETE_ITEM_INFO;
import static com.trading.app.util.SwaggerConstant.DELETE_ITEM_PARAM;
import static com.trading.app.util.SwaggerConstant.SAVE_ITEM_COMMENT_INFO;
import static com.trading.app.util.SwaggerConstant.SAVE_ITEM_COMMENT_PARAM;
import static com.trading.app.util.SwaggerConstant.DELETE_ITEM_COMMENT_INFO;
import static com.trading.app.util.SwaggerConstant.DELETE_ITEM_COMMENT_PARAM;

@RestController
@RequestMapping(path = "/item")
@Api(tags = { ITEM_RESOURCE_INFO })
public class ItemResource {

	Logger logger = LoggerFactory.getLogger(ItemResource.class);

	@Autowired
	private ItemService itemService;
	@Autowired
	private TradingAppUtil tradingUtil;

	@RequestMapping(path = "/saveItem", method = RequestMethod.POST)
	@ApiOperation(value = SAVE_ITEM_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> saveItem(
			@ApiParam(value = SAVE_ITEM_PARAM, required = true) @RequestBody ItemDTO itemDTO) {
		logger.info("SaveItem Api received Request with payload " + tradingUtil.convertObjectToString(itemDTO));
		ResponseDTO<?> response = itemService.saveItem(itemDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/getItemDetails", method = RequestMethod.POST)
	@ApiOperation(value = VIEW_ITEM_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> viewItemDetails(
			@ApiParam(value = VIEW_ITEM_PARAM, required = true) @RequestBody ItemDTO itemDTO) {
		logger.info("ViewItemDetails Api received Request with payload " + tradingUtil.convertObjectToString(itemDTO));
		ResponseDTO<?> response = itemService.getItemDetails(itemDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/deleteItem", method = RequestMethod.POST)
	@ApiOperation(value = DELETE_ITEM_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> deleteItem(
			@ApiParam(value = DELETE_ITEM_PARAM, required = true) @RequestBody ItemDTO itemDTO) {
		logger.info("DeleteItem Api received Request with payload " + tradingUtil.convertObjectToString(itemDTO));
		ResponseDTO<?> response = itemService.removeItem(itemDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/saveItemComment", method = RequestMethod.POST)
	@ApiOperation(value = SAVE_ITEM_COMMENT_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> saveItemComment(
			@ApiParam(value = SAVE_ITEM_COMMENT_PARAM, required = true) @RequestBody ItemCommentDTO itemCommentDTO) {
		logger.info("SaveItemComment Api received Request with payload "
				+ tradingUtil.convertObjectToString(itemCommentDTO));
		ResponseDTO<?> response = itemService.saveItemComment(itemCommentDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/deleteItemComment", method = RequestMethod.POST)
	@ApiOperation(value = DELETE_ITEM_COMMENT_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> deleteItemComment(
			@ApiParam(value = DELETE_ITEM_COMMENT_PARAM, required = true) @RequestBody ItemCommentDTO itemCommentDTO) {
		logger.info("DeleteItemComment Api received Request with payload "
				+ tradingUtil.convertObjectToString(itemCommentDTO));
		ResponseDTO<?> response = itemService.removeItemComment(itemCommentDTO);
		return ResponseEntity.ok().body(response);
	}
}
