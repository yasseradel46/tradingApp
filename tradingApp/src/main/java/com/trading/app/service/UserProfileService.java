package com.trading.app.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.trading.app.dto.ResponseDTO;
import com.trading.app.dto.UserProfileDTO;
import com.trading.app.entity.UserProfile;
import com.trading.app.repository.UserProfileRepository;
import com.trading.app.security.TradingAppAuthenticationException;
import com.trading.app.util.TradingAppUtil;
import static com.trading.app.util.TradingAppConstant.EMAIL_EXIST_MSG;
import static com.trading.app.util.TradingAppConstant.RESPONSE_INVALID_CODE;
import static com.trading.app.util.TradingAppConstant.RESPONSE_SUCCESS_CODE;
import static com.trading.app.util.TradingAppConstant.USER_ACTIVATION_MSG;
import static com.trading.app.util.TradingAppConstant.EMAIL_NOT_EXIST_MSG;
import static com.trading.app.util.TradingAppConstant.USER_SEND_ACTIVATION_MSG;
import static com.trading.app.util.TradingAppConstant.INACTIVE_ACCOUNT_MSG;
import static com.trading.app.util.TradingAppConstant.CHANGE_PASSWORD_Fail_MSG;
import static com.trading.app.util.TradingAppConstant.CHANGE_PASSWORD_SUCCESS_MSG;
import static com.trading.app.util.TradingAppConstant.USER_SEND_Phone_ACTIVATION_MSG;
import static java.util.Collections.emptyList;
import java.util.Date;
import static com.trading.app.util.TradingAppConstant.INVAILD_PHONE_CODE_MSG;
import static com.trading.app.util.TradingAppConstant.USER_PHONE_ACTIVATION_MSG;
import static com.trading.app.util.TradingAppConstant.USER_ACTIVE_CODE;

@Service("userProfileService")
@Transactional
public class UserProfileService implements UserDetailsService {
	@Autowired
	private UserProfileRepository userProfileRepository;
	@Autowired
	private TradingAppUtil tradingUtil;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public ResponseDTO<?> registerUserProfile(UserProfileDTO userProfileDTO) {
		ResponseDTO<?> response = null;
		UserProfile userProfile = userProfileRepository.findByEmail(userProfileDTO.getEmail());
		if (userProfile == null) {
			UserProfile userEntity = new UserProfile();
			userProfileDTO.setCreationDate(new Date());
			BeanUtils.copyProperties(userProfileDTO, userEntity);
			userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
			userEntity = userProfileRepository.save(userEntity);
			tradingUtil.sendActivationEmail(userProfileDTO.getEmail());
			BeanUtils.copyProperties(userEntity, userProfileDTO);
			response = new ResponseDTO<UserProfileDTO>(RESPONSE_SUCCESS_CODE, userProfileDTO);
		} else {
			response = new ResponseDTO<String>(RESPONSE_INVALID_CODE, EMAIL_EXIST_MSG + userProfileDTO.getEmail());
		}
		return response;
	}

	public ResponseDTO<?> saveUserProfile(UserProfileDTO userProfileDTO) {
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		userProfileRepository.updateUserInfo(userProfileDTO.getUserName(), userProfileDTO.getLocation(),
				userProfileDTO.getPhoto(), new Date(), loggedInUserEmail);
		BeanUtils.copyProperties(userProfileRepository.findByEmail(loggedInUserEmail), userProfileDTO);
		return new ResponseDTO<UserProfileDTO>(RESPONSE_SUCCESS_CODE, userProfileDTO);
	}

	public ResponseDTO<?> activeUserEmail(String userEmailCode) {
		ResponseDTO<?> response = null;
		String userEmail = tradingUtil.decryptEmail(userEmailCode);
		int rowUpdated = userProfileRepository.activateUserProfile(USER_ACTIVE_CODE, new Date(), userEmail);
		if (rowUpdated == 0) {
			response = new ResponseDTO<String>(RESPONSE_INVALID_CODE, EMAIL_NOT_EXIST_MSG + userEmail);
		} else {
			response = new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, USER_ACTIVATION_MSG);
		}
		return response;
	}

	public ResponseDTO<?> sendEmailActivation(UserProfileDTO userProfileDTO) {
		tradingUtil.sendActivationEmail(userProfileDTO.getEmail());
		return new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, USER_SEND_ACTIVATION_MSG);
	}

	public ResponseDTO<?> verifyUserPhone(UserProfileDTO userProfileDTO) {
		ResponseDTO<?> response = null;
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		UserProfile userProfile = userProfileRepository.verifyUserPhoneActivation(loggedInUserEmail,
				userProfileDTO.getPhoneActivationCode());
		if (userProfile == null) {
			response = new ResponseDTO<String>(RESPONSE_INVALID_CODE, INVAILD_PHONE_CODE_MSG);
		} else {
			userProfileRepository.setUserPhone(userProfileDTO.getPhone(), new Date(), loggedInUserEmail);
			response = new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, USER_PHONE_ACTIVATION_MSG);
		}
		return response;
	}

	public ResponseDTO<?> sendPhoneActivation(UserProfileDTO userProfileDTO) {
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		String activationPhoneCode = tradingUtil.generateRandomPhoneActivation();
		userProfileRepository.setUserPhoneActivationCode(activationPhoneCode, new Date(), loggedInUserEmail);
		tradingUtil.sendActivationSMS(userProfileDTO.getPhone(), activationPhoneCode);
		return new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, USER_SEND_Phone_ACTIVATION_MSG);
	}

	public ResponseDTO<?> updateUserPassword(UserProfileDTO userProfileDTO) {
		ResponseDTO<?> response = null;
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		UserProfile userProfile = userProfileRepository.findByEmail(loggedInUserEmail);
		if (userProfile != null
				&& bCryptPasswordEncoder.matches(userProfileDTO.getPassword(), userProfile.getPassword())) {
			userProfileRepository.updateUserPassword(bCryptPasswordEncoder.encode(userProfileDTO.getNewPassword()),
					new Date(), loggedInUserEmail);
			response = new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, CHANGE_PASSWORD_SUCCESS_MSG);
		} else {
			response = new ResponseDTO<String>(RESPONSE_INVALID_CODE, CHANGE_PASSWORD_Fail_MSG);
		}
		return response;
	}

	public UserProfile getUserProfileInfo(String email) {
		return userProfileRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserProfile applicationUser = userProfileRepository.findByEmail(userName);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(userName);
		} else if (applicationUser != null && applicationUser.getIsActive() != USER_ACTIVE_CODE) {
			throw new TradingAppAuthenticationException(INACTIVE_ACCOUNT_MSG);
		} else
			return new User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList());
	}
}
