package com.trading.app.util;

public class SwaggerConstant {
	public static final String SUCCESS_OPERATION = "Successfully Operation";
	public static final String NOT_AUTHORIZED_OPERATION = "You are not authorized to view the resource";
	public static final String FORBIDDEN_OPERATION = "Accessing the resource you were trying to reach is forbidden";
	public static final String NOT_FOUND_OPERATION = "The resource you were trying to reach is not found";
	public static final String USER_PROFILE_RESOURCE_INFO = "Manage All Operations related with Trading User";
	public static final String ITEM_RESOURCE_INFO = "Manage All Operations related with Items";
	public static final String SAVE_ITEM_INFO = "Save Item Api used to save staff or wish Items";
	public static final String SAVE_ITEM_PARAM = "ItemDTO object with Item name,description,location,category,quality,type,Images and SwapTypes";

	public static final String USER_PROFILE_SIGNUP_INFO = "signup User Profile Info";
	public static final String USER_PROFILE_UPDATE_INFO = "Update User Profile Info";
	public static final String USER_PROFILE_UPDATE_PARAM = "UserProfileDTO object with Updated Info UserName,Location and Photo";
	public static final String USER_PROFILE_SIGNUP_PARAM = "UserProfileDTO object with at least UserName,Email and Password";
	public static final String USER_PROFILE_ACTIVATE_EMAIL_INFO = "Activate User Email ";
	public static final String USER_PROFILE_ACTIVATE_EMAIL_PARAM = "Encrypted User Email";
	public static final String USER_PROFILE_SEND_EMAIL_ACTIVATION_INFO = "Send Email to User with Activation Code of his/here Email";
	public static final String USER_PROFILE_SEND_PHONE_ACTIVATION_INFO = "Send SMS to User Phone with Activation Code of his/here Phone";
	public static final String USER_PROFILE_SEND_PHONE_ACTIVATION_PARAM = "UserProfileDTO object with at least  Phone number";
	public static final String USER_PROFILE_LOGIN_INFO = "Login Api with Email and Password";
	public static final String USER_PROFILE_CHANGE_PASSWORD_INFO = "Change Password Api";
	public static final String USER_PROFILE_CHANGE_PASSWORD_PARAM = "UserProfileDTO object with at least  Current password and new Password";
	public static final String USER_PROFILE_SEND_EMAIL_ACTIVATION_PARAM = "UserProfileDTO object with at least Email For sending Activation Code";
	public static final String USER_PROFILE_LOGIN_PARAM = "UserProfileDTO object with  Email And Password";
	public static final String USER_PROFILE_VERIFY_PHONE_INFO = "Verify User Phone by sending The Received SMS code ";
	public static final String USER_PROFILE_GET_FOLLOWING_INFO = "get Following Users ";
	public static final String USER_PROFILE_GET_FOLLOWERS_INFO = "get Followers Users ";
	public static final String USER_PROFILE_FOLLOW_USER_INFO = "Follow User ";
	public static final String USER_PROFILE_UNFOLLOW_USER_INFO = "Unfollow User ";
	public static final String USER_PROFILE_FOLLOW_USER_PARAM = "UserProfileDTO object with at least followed userEmail";
	public static final String USER_PROFILE_UNFOLLOW_USER_PARAM = "UserProfileDTO object with at least un followed userEmail";

	public static final String USER_PROFILE_GET_FOLLOWING_PARAM = "UserProfileDTO object with at least userEmail";
	public static final String USER_PROFILE_GET_FOLLOWERS_PARAM = "UserProfileDTO object with at least userEmail";
	public static final String USER_PROFILE_VERIFY_PHONE_PARAM = "UserProfileDTO object with at least phone Number and SMS Activation Code";

}
