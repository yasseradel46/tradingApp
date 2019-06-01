package com.trading.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trading.app.dto.ResponseDTO;
import com.trading.app.dto.UserProfileDTO;
import com.trading.app.service.UserProfileService;
import com.trading.app.util.TradingAppUtil;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import static com.trading.app.util.SwaggerConstant.SUCCESS_OPERATION;
import static com.trading.app.util.SwaggerConstant.FORBIDDEN_OPERATION;
import static com.trading.app.util.SwaggerConstant.NOT_FOUND_OPERATION;
import static com.trading.app.util.SwaggerConstant.NOT_AUTHORIZED_OPERATION;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_RESOURCE_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_SIGNUP_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_SIGNUP_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_ACTIVATE_EMAIL_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_ACTIVATE_EMAIL_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_SEND_EMAIL_ACTIVATION_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_SEND_EMAIL_ACTIVATION_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_CHANGE_PASSWORD_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_CHANGE_PASSWORD_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_SEND_PHONE_ACTIVATION_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_SEND_PHONE_ACTIVATION_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_VERIFY_PHONE_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_VERIFY_PHONE_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_UPDATE_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_UPDATE_PARAM;

@RestController
@RequestMapping(path = "/user")
@Api(tags = { USER_PROFILE_RESOURCE_INFO })
public class UserProfileResource {

	Logger logger = LoggerFactory.getLogger(UserProfileResource.class);

	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private TradingAppUtil tradingUtil;

	@RequestMapping(path = "/signup", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_SIGNUP_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> signup(
			@ApiParam(value = USER_PROFILE_SIGNUP_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info("Signup Api received Request with payload " + tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.registerUserProfile(userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/updateUserInfo", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_UPDATE_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> updateUserInfo(
			@ApiParam(value = USER_PROFILE_UPDATE_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info("UpdateUserInfo Api received Request with payload "
				+ tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.saveUserProfile(userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/activateEmail", method = RequestMethod.GET)
	@ApiOperation(value = USER_PROFILE_ACTIVATE_EMAIL_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> activateEmail(
			@ApiParam(value = USER_PROFILE_ACTIVATE_EMAIL_PARAM, required = true) @RequestParam("code") String code) {
		logger.info("ActivateEmail Api received Request with payload " + code);
		ResponseDTO<?> response = userProfileService.activeUserEmail(code);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/sendEmailActivation", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_SEND_EMAIL_ACTIVATION_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> sendEmailActivation(
			@ApiParam(value = USER_PROFILE_SEND_EMAIL_ACTIVATION_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info("SendEmailActivation Api received Request with payload "
				+ tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.sendEmailActivation(userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/changePassword", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_CHANGE_PASSWORD_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> changePassword(
			@ApiParam(value = USER_PROFILE_CHANGE_PASSWORD_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info("ChangePassword Api received Request with payload "
				+ tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.updateUserPassword(userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/sendPhoneActivation", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_SEND_PHONE_ACTIVATION_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> sendPhoneActivation(
			@ApiParam(value = USER_PROFILE_SEND_PHONE_ACTIVATION_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info("SendPhoneActivation Api received Request with payload "
				+ tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.sendPhoneActivation(userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/verifyPhone", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_VERIFY_PHONE_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> verifyPhone(
			@ApiParam(value = USER_PROFILE_VERIFY_PHONE_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info("VerifyPhone Api received Request with payload " + userProfileDTO);
		ResponseDTO<?> response = userProfileService.verifyUserPhone(userProfileDTO);
		return ResponseEntity.ok().body(response);
	}
}
