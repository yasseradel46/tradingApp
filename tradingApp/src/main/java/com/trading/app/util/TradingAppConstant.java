package com.trading.app.util;

public class TradingAppConstant {
	public static final String CONTROLLER_BASE_PACKAGE = "com.trading.app.controller";
	public static final String EMAIL_EXIST_MSG = "There is an account with that email address: ";
	public static final String INVAILD_CREDENTIALS_MSG = "Invalid Email or Password";
	public static final String INACTIVE_ACCOUNT_MSG = "Your Account is not active";
	public static final String EMAIL_NOT_EXIST_MSG = "There is no an account with that email address: ";
	public static final String RESPONSE_EXCEPTION_MSG = "Backend Server Error";
	public static final String USER_ACTIVATION_MSG = "Congratulations , Your Profile is Activated Successfully";
	public static final String USER_PHONE_ACTIVATION_MSG = "Congratulations , Your Phone is Activated Successfully";
	public static final String INVAILD_PHONE_CODE_MSG = "Invalid Phone Activation Code";
	public static final String USER_SEND_ACTIVATION_MSG = "Activation Link sent to Your Email Successfully, PLease check Your Email";
	public static final String USER_SEND_Phone_ACTIVATION_MSG = "Activation code sent to Your Phone Successfully, PLease check Your Phone";
	public static final String CHANGE_PASSWORD_SUCCESS_MSG = "Your Password changed Successfully";
	public static final String CHANGE_PASSWORD_Fail_MSG = "Invalid Current Password";
	public static final String SUCCESS_OPERATION_MSG = "Success Operation";
	public static final String INVALID_TOKEN_MSG = "Invalid Token Format";
	public static final Integer RESPONSE_EXCEPTION_CODE = 500;
	public static final Integer USER_ACTIVE_CODE = 1;
	public static final Integer RESPONSE_SUCCESS_CODE = 200;
	public static final Integer RESPONSE_INVALID_CODE = 205;
	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 3600_000; // 60 minutes
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/user/signup";
	public static final String SIGN_IN_URL = "/user/login";
	public static final String ACTIVATE_EMAIL_URL = "/user/activateEmail";
	public static final String SEND_EMAIL_ACTIVATION_URL = "/user/sendEmailActivation";
	public static final String SWAGGER_URL = "/swagger-ui.html";
	public static final Integer ITEM_AVAILABLE_STATUS = 6;
	public static final Integer ITEM_DELETED_STATUS = 5;
	public static final Integer ITEM_SWAPED_STATUS = 4;
	public static final Integer WISH_ITEM = 8;
	public static final Integer STAFF_ITEM = 7;

}
