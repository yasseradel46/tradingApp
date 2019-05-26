package com.trading.app.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.trading.app.dto.ResponseDTO;
import com.trading.app.dto.UserProfileDTO;
import com.trading.app.entity.UserProfile;
import com.trading.app.repository.UserProfileRepository;
import com.trading.app.util.TradingAppUtil;
import static com.trading.app.util.TradingAppConstant.EMAIL_EXIST;
import static com.trading.app.util.TradingAppConstant.RESPONSE_INVALID_CODE;
import static com.trading.app.util.TradingAppConstant.RESPONSE_SUCCESS_CODE;
import static com.trading.app.util.TradingAppConstant.USER_ACTIVATION_MSG;
import static com.trading.app.util.TradingAppConstant.EMAIL_NOT_EXIST;
import static com.trading.app.util.TradingAppConstant.USER_SEND_ACTIVATION_MSG;

@Service("userProfileService")
@Transactional
public class UserProfileService {
	@Autowired
	private UserProfileRepository userProfileRepository;
	@Autowired
	private TradingAppUtil tradingUtil;

	public ResponseDTO<?> saveUserProfile(UserProfileDTO userProfileDTO) {
		ResponseDTO<?> response = null;
		if (emailExist(userProfileDTO.getEmail())) {
			response = new ResponseDTO<String>(RESPONSE_INVALID_CODE, EMAIL_EXIST + userProfileDTO.getEmail());
		} else {
			UserProfile userEntity = new UserProfile();
			BeanUtils.copyProperties(userProfileDTO, userEntity);
			userEntity.setPassword(BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt()));
			userEntity = userProfileRepository.save(userEntity);
			BeanUtils.copyProperties(userEntity, userProfileDTO);
			tradingUtil.sendActivationEmail(userProfileDTO.getEmail());
			response = new ResponseDTO<UserProfileDTO>(RESPONSE_SUCCESS_CODE, userProfileDTO);
		}
		return response;
	}

	public ResponseDTO<?> activeUserEmail(String userEmailCode) {
		ResponseDTO<?> response = null;
		String userEmail = tradingUtil.decryptEmail(userEmailCode);
		int rowUpdated = userProfileRepository.activateUserProfile(userEmail);
		if (rowUpdated == 0) {
			response = new ResponseDTO<String>(RESPONSE_INVALID_CODE, EMAIL_NOT_EXIST + userEmail);
		} else {
			response = new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, USER_ACTIVATION_MSG);
		}
		return response;
	}

	public ResponseDTO<?> sendEmailActivation(UserProfileDTO userProfileDTO) {
		tradingUtil.sendActivationEmail(userProfileDTO.getEmail());
		return new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, USER_SEND_ACTIVATION_MSG);
	}

	private boolean emailExist(String email) {
		if (userProfileRepository.getUserProfileByEmail(email) != null)
			return true;
		return false;
	}
}
