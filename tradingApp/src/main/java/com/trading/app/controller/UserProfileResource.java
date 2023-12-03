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

import com.trading.app.dto.ReportedUserDTO;
import com.trading.app.dto.ResponseDTO;
import com.trading.app.dto.UserProfileDTO;
import com.trading.app.dto.UserSavedItemDTO;
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
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_FOLLOWING_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_FOLLOWERS_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_FOLLOWING_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_FOLLOWERS_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_FOLLOW_USER_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_UNFOLLOW_USER_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_UNFOLLOW_USER_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_FOLLOW_USER_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_ADD_SAVED_ITEM_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_DELETE_SAVED_ITEM_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_DELETE_SAVED_ITEMS_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_ADD_SAVED_ITEMS_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_SAVED_ITEMS_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_WISH_ITEMS_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_WISH_ITEMS_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_STAFF_ITEMS_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_STAFF_ITEMS_PARAM;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_SENT_OFFERS_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_RECEIVED_OFFERS_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_GET_DEALS_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_REPORT_USER_INFO;
import static com.trading.app.util.SwaggerConstant.USER_PROFILE_REPORT_USER_PARAM;

@RestController
@RequestMapping(path = "/user")
@Api(tags = { USER_PROFILE_RESOURCE_INFO })
public class UserProfileResource {
// commit
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
		logger.info(
				"VerifyPhone Api received Request with payload " + tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.verifyUserPhone(userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/getFollowing", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_GET_FOLLOWING_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> getFollowing(
			@ApiParam(value = USER_PROFILE_GET_FOLLOWING_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info(
				"GetFollowing Api received Request with payload " + tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.getRelatedUsers(true, userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/getFollowers", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_GET_FOLLOWERS_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> getFollowers(
			@ApiParam(value = USER_PROFILE_GET_FOLLOWERS_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info(
				"GetFollowers Api received Request with payload " + tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.getRelatedUsers(false, userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/followUser", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_FOLLOW_USER_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> followUser(
			@ApiParam(value = USER_PROFILE_FOLLOW_USER_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info("FollowUser Api received Request payload" + tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.followUser(userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/unFollowUser", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_UNFOLLOW_USER_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> unFollowUser(
			@ApiParam(value = USER_PROFILE_UNFOLLOW_USER_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info("unFollowUser Api received Request payload" + tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.unFollowUser(userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/getSavedItems", method = RequestMethod.GET)
	@ApiOperation(value = USER_PROFILE_GET_SAVED_ITEMS_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> getSavedItems() {
		logger.info("getSavedItems Api received Request ");
		ResponseDTO<?> response = userProfileService.getSavedItems();
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/getStaffItems", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_GET_STAFF_ITEMS_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> getStaffItems(
			@ApiParam(value = USER_PROFILE_GET_STAFF_ITEMS_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info("getStaffItems Api received Request payload" + tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.getUserItems(true, userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/getWishItems", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_GET_WISH_ITEMS_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> getWishItems(
			@ApiParam(value = USER_PROFILE_GET_WISH_ITEMS_PARAM, required = true) @RequestBody UserProfileDTO userProfileDTO) {
		logger.info("getWishItems Api received Request payload" + tradingUtil.convertObjectToString(userProfileDTO));
		ResponseDTO<?> response = userProfileService.getUserItems(false, userProfileDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/getSentOffers", method = RequestMethod.GET)
	@ApiOperation(value = USER_PROFILE_GET_SENT_OFFERS_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> getSentOffers() {
		logger.info("getSentOffers Api received Request");
		ResponseDTO<?> response = userProfileService.getUserOffers(1);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/getRecievedOffers", method = RequestMethod.GET)
	@ApiOperation(value = USER_PROFILE_GET_RECEIVED_OFFERS_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> getRecievedOffers() {
		logger.info("getRecievedOffers Api received Request");
		ResponseDTO<?> response = userProfileService.getUserOffers(2);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/getDeals", method = RequestMethod.GET)
	@ApiOperation(value = USER_PROFILE_GET_DEALS_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> getDeals() {
		logger.info("getDeals Api received Request");
		ResponseDTO<?> response = userProfileService.getUserOffers(3);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/addSavedItem", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_ADD_SAVED_ITEMS_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> addSavedItem(
			@ApiParam(value = USER_PROFILE_ADD_SAVED_ITEM_PARAM, required = true) @RequestBody UserSavedItemDTO userSavedItemDTO) {
		logger.info("addSavedItem Api received Request payload" + tradingUtil.convertObjectToString(userSavedItemDTO));
		ResponseDTO<?> response = userProfileService.addSavedItem(userSavedItemDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/deleteSavedItem", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_DELETE_SAVED_ITEMS_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> deleteSavedItem(
			@ApiParam(value = USER_PROFILE_DELETE_SAVED_ITEM_PARAM, required = true) @RequestBody UserSavedItemDTO userSavedItemDTO) {
		logger.info(
				"deleteSavedItem Api received Request payload" + tradingUtil.convertObjectToString(userSavedItemDTO));
		ResponseDTO<?> response = userProfileService.removeSavedItem(userSavedItemDTO);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(path = "/reportUser", method = RequestMethod.POST)
	@ApiOperation(value = USER_PROFILE_REPORT_USER_INFO)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS_OPERATION),
			@ApiResponse(code = 401, message = NOT_AUTHORIZED_OPERATION),
			@ApiResponse(code = 403, message = FORBIDDEN_OPERATION),
			@ApiResponse(code = 404, message = NOT_FOUND_OPERATION) })
	public ResponseEntity<ResponseDTO<?>> reportUser(
			@ApiParam(value = USER_PROFILE_REPORT_USER_PARAM, required = true) @RequestBody ReportedUserDTO reportedUserDTO) {
		logger.info("reportUser Api received Request payload" + tradingUtil.convertObjectToString(reportedUserDTO));
		ResponseDTO<?> response = userProfileService.reportUser(reportedUserDTO);
		return ResponseEntity.ok().body(response);
	}
}
