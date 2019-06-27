package com.trading.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trading.app.dto.OfferItemDTO;
import com.trading.app.dto.ResponseDTO;
import com.trading.app.dto.UserOfferDTO;
import com.trading.app.entity.Item;
import com.trading.app.entity.Lookup;
import com.trading.app.entity.OfferItem;
import com.trading.app.entity.UserOffer;
import com.trading.app.repository.UserOfferRepository;
import com.trading.app.repository.UserProfileRepository;
import com.trading.app.util.TradingAppUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.trading.app.util.TradingAppConstant.SUCCESS_OPERATION_MSG;
import static com.trading.app.util.TradingAppConstant.RESPONSE_SUCCESS_CODE;
import static com.trading.app.util.TradingAppConstant.OFFER_CREATED_STATUS;
import static com.trading.app.util.TradingAppConstant.OFFER_CHANGED_STATUS;
import static com.trading.app.util.TradingAppConstant.OFFER_ACCEPTED_STATUS;
import static com.trading.app.util.TradingAppConstant.ITEM_SWAPED_STATUS;

@Service("offerService")
@Transactional
public class OfferService {
	@Autowired
	UserOfferRepository userOfferRepository;
	@Autowired
	private UserProfileRepository userProfileRepository;
	@Autowired
	private TradingAppUtil tradingUtil;
	@Autowired
	private ModelMapper mapper;

	public ResponseDTO<?> saveOffer(UserOfferDTO offerDTO) {
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		if (offerDTO.getId() != null) {
			List<OfferItem> offerItems = new ArrayList<OfferItem>();
			List<OfferItemDTO> offerItemsDTO = offerDTO.getOfferItems();
			UserOffer retrievedOffer = userOfferRepository.findById(offerDTO.getId()).orElse(null);
			if (retrievedOffer != null) {
				UserOffer copyOffer = mapper.map(offerDTO, UserOffer.class);
				String[] ignorePropertyNames = tradingUtil.getIgnorePropertyNames(copyOffer, "offerItems");
				BeanUtils.copyProperties(copyOffer, retrievedOffer, ignorePropertyNames);
				retrievedOffer.setModificationDate(new Date());
				// Saving Offer Items
				retrievedOffer.getOfferItems().clear();
				if (offerItemsDTO != null) {
					offerItemsDTO.forEach(offerDto -> {
						OfferItem offerItem = mapper.map(offerDto, OfferItem.class);
						offerItem.setUserOffer(retrievedOffer);
						offerItem.setCreationDate(new Date());
						offerItems.add(offerItem);
					});
				}
				retrievedOffer.getOfferItems().addAll(offerItems);
				Lookup changedOfferStatus = new Lookup();
				changedOfferStatus.setLookupId(OFFER_CHANGED_STATUS);
				retrievedOffer.setOfferStatus(changedOfferStatus);
				retrievedOffer.setModificationDate(new Date());
				userOfferRepository.save(retrievedOffer);
			}
		} else {
			UserOffer userOffer = mapper.map(offerDTO, UserOffer.class);
			Lookup availableOffer = new Lookup();
			availableOffer.setLookupId(OFFER_CREATED_STATUS);
			userOffer.setOfferStatus(availableOffer);
			userOffer.setUserProfile(userProfileRepository.findByEmail(loggedInUserEmail));
			userOffer.setCreationDate(new Date());
			// Saving Offer Items
			List<OfferItem> offerItems = userOffer.getOfferItems();
			if (offerItems != null) {
				offerItems.forEach(item -> {
					item.setUserOffer(userOffer);
					item.setCreationDate(new Date());
				});
			}
			userOfferRepository.save(userOffer);
		}

		ResponseDTO<?> response = new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, SUCCESS_OPERATION_MSG);
		return response;
	}

	public ResponseDTO<?> getOfferDetails(UserOfferDTO userOfferDTO) {
		UserOfferDTO OfferDTO = new UserOfferDTO();
		UserOffer retrievedOffer = userOfferRepository.findById(userOfferDTO.getId()).orElse(null);
		if (retrievedOffer != null)
			mapper.map(retrievedOffer, OfferDTO);
		return new ResponseDTO<UserOfferDTO>(RESPONSE_SUCCESS_CODE, OfferDTO);
	}

	public ResponseDTO<?> changeOfferStatus(UserOfferDTO userOfferDTO, int offerStatus) {
		UserOffer retrievedOffer = userOfferRepository.findById(userOfferDTO.getId()).orElse(null);
		if (retrievedOffer != null) {
			if (OFFER_ACCEPTED_STATUS == offerStatus) {
				Lookup swapedStatus = new Lookup();
				swapedStatus.setLookupId(ITEM_SWAPED_STATUS);
				Item item = retrievedOffer.getItem();
				item.setItemStatus(swapedStatus);
				item.setModificationDate(new Date());

				List<OfferItem> offerItems = retrievedOffer.getOfferItems();
				if (offerItems != null) {
					offerItems.forEach(offer -> {
						Item offerItem = offer.getItem();
						if (offerItem != null)
							offerItem.setItemStatus(swapedStatus);
						    offerItem.setModificationDate(new Date());
					});
				}
			}
			Lookup status = new Lookup();
			status.setLookupId(offerStatus);
			retrievedOffer.setOfferStatus(status);
			retrievedOffer.setModificationDate(new Date());
			userOfferRepository.save(retrievedOffer);
		}
		ResponseDTO<?> response = new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, SUCCESS_OPERATION_MSG);
		return response;
	}
}
