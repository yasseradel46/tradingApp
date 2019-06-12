package com.trading.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trading.app.dto.ItemDTO;
import com.trading.app.dto.ResponseDTO;
import com.trading.app.dto.UserProfileDTO;
import com.trading.app.dto.UserSavedItemDTO;
import com.trading.app.entity.FollowerUser;
import com.trading.app.entity.Item;
import com.trading.app.entity.UserProfile;
import com.trading.app.entity.UserSavedItem;
import com.trading.app.repository.FollowerUserRepository;
import com.trading.app.repository.UserProfileRepository;
import com.trading.app.repository.UserSavedItemRepository;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import static com.trading.app.util.TradingAppConstant.INVAILD_PHONE_CODE_MSG;
import static com.trading.app.util.TradingAppConstant.USER_PHONE_ACTIVATION_MSG;
import static com.trading.app.util.TradingAppConstant.USER_ACTIVE_CODE;
import static com.trading.app.util.TradingAppConstant.SUCCESS_OPERATION_MSG;

@Service("userProfileService")
@Transactional
public class UserProfileService implements UserDetailsService {
	@Autowired
	private UserProfileRepository userProfileRepository;
	@Autowired
	private FollowerUserRepository followerUserRepository;
	@Autowired
	UserSavedItemRepository userSavedItemRepository;
	@Autowired
	private TradingAppUtil tradingUtil;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private ModelMapper mapper;

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
		byte[] decodedImage = null;
		if (userProfileDTO.getPhoto() != null)
			decodedImage = DatatypeConverter.parseHexBinary(userProfileDTO.getPhoto());
		userProfileRepository.updateUserInfo(userProfileDTO.getUserName(), userProfileDTO.getLocation(), decodedImage,
				new Date(), loggedInUserEmail);
		UserProfile userProfile = userProfileRepository.findByEmail(loggedInUserEmail);
		if (userProfile.getPhoto() != null)
			userProfileDTO.setPhoto(DatatypeConverter.printHexBinary(userProfile.getPhoto()));
		BeanUtils.copyProperties(userProfile, userProfileDTO);
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

	public ResponseDTO<?> getRelatedUsers(boolean isFollowing, UserProfileDTO userProfileDTO) {
		ResponseDTO<List<UserProfileDTO>> response = new ResponseDTO<List<UserProfileDTO>>(RESPONSE_SUCCESS_CODE);
		List<UserProfileDTO> relatedUsersDTO = new ArrayList<UserProfileDTO>();
		List<FollowerUser> users = new ArrayList<FollowerUser>();
		UserProfile userProfile = getUserProfileInfo(userProfileDTO.getEmail());
		if (userProfile != null) {
			if (isFollowing) {
				users = userProfile.getFollowerUsers();
				if (users != null && !users.isEmpty()) {
					users.forEach(user -> {
						UserProfileDTO userDTO = new UserProfileDTO();
						if (user.getFollowingUser() != null && user.getFollowingUser().getPhoto() != null)
							userDTO.setPhoto(DatatypeConverter.printHexBinary(user.getFollowingUser().getPhoto()));
						BeanUtils.copyProperties(user.getFollowingUser(), userDTO);
						relatedUsersDTO.add(userDTO);
					});
				}
			} else {
				users = userProfile.getFollowingUsers();
				if (users != null && !users.isEmpty()) {
					users.forEach(user -> {
						UserProfileDTO userDTO = new UserProfileDTO();
						if (user.getFollower() != null && user.getFollower().getPhoto() != null)
							userDTO.setPhoto(DatatypeConverter.printHexBinary(user.getFollower().getPhoto()));
						BeanUtils.copyProperties(user.getFollower(), userDTO);
						relatedUsersDTO.add(userDTO);
					});
				}
			}
		}
		response.setPayload(relatedUsersDTO);
		return response;
	}

	public ResponseDTO<?> followUser(UserProfileDTO userProfileDTO) {
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		FollowerUser followerUser = new FollowerUser();
		followerUser.setFollowingUser(getUserProfileInfo(userProfileDTO.getEmail()));
		followerUser.setFollower(getUserProfileInfo(loggedInUserEmail));
		followerUser.setCreationDate(new Date());
		followerUserRepository.save(followerUser);
		return new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, SUCCESS_OPERATION_MSG);
	}

	public ResponseDTO<?> unFollowUser(UserProfileDTO userProfileDTO) {
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		FollowerUser followerUser = followerUserRepository.findByFollowerAndFollowingUser(
				getUserProfileInfo(loggedInUserEmail), getUserProfileInfo(userProfileDTO.getEmail()));
		followerUserRepository.delete(followerUser);
		return new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, SUCCESS_OPERATION_MSG);
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

	public ResponseDTO<?> getSavedItems() {
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		UserProfile userProfile = userProfileRepository.findByEmail(loggedInUserEmail);
		List<UserSavedItem> savedItems = userProfile.getUserSavedItems();
		List<UserSavedItemDTO> savedItemsDTO = new ArrayList<UserSavedItemDTO>();
		if (savedItems != null) {
			savedItems.forEach(savedItem -> {
				UserSavedItemDTO itemDto = mapper.map(savedItem, UserSavedItemDTO.class);
				savedItemsDTO.add(itemDto);
			});
		}
		return new ResponseDTO<List<UserSavedItemDTO>>(RESPONSE_SUCCESS_CODE, savedItemsDTO);
	}

	public ResponseDTO<?> getUserItems(boolean isStaff, UserProfileDTO userProfileDTO) {
		List<ItemDTO> itemsDTO = new ArrayList<ItemDTO>();
		List<Item> items = new ArrayList<Item>();
		UserProfile userProfile = getUserProfileInfo(userProfileDTO.getEmail());
		if (userProfile != null) {
			if (isStaff) {
				items = userProfile.getStaffItems();
			} else {
				items = userProfile.getWishItems();
			}
			if (items != null && !items.isEmpty()) {
				items.forEach(item -> {
					ItemDTO itemDTO = new ItemDTO();
					mapper.map(item, itemDTO);
					itemsDTO.add(itemDTO);
				});
			}
		}
		return new ResponseDTO<List<ItemDTO>>(RESPONSE_SUCCESS_CODE, itemsDTO);
	}

	public ResponseDTO<?> addSavedItem(UserSavedItemDTO userSavedItemDTO) {
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		UserProfile userProfile = userProfileRepository.findByEmail(loggedInUserEmail);
		UserSavedItem userSavedItem = new UserSavedItem();
		mapper.map(userSavedItemDTO, userSavedItem);
		userSavedItem.setCreationDate(new Date());
		userSavedItem.setUserProfile(userProfile);
		userSavedItemRepository.save(userSavedItem);
		return new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, SUCCESS_OPERATION_MSG);
	}

	public ResponseDTO<?> removeSavedItem(UserSavedItemDTO userSavedItemDTO) {
		userSavedItemRepository.deleteById(userSavedItemDTO.getId());
		return new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, SUCCESS_OPERATION_MSG);
	}

	private UserProfile getUserProfileInfo(String email) {
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
