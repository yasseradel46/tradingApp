package com.trading.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trading.app.dto.ItemCommentDTO;
import com.trading.app.dto.ItemDTO;
import com.trading.app.dto.ItemImageDTO;
import com.trading.app.dto.ItemSwapTypeDTO;
import com.trading.app.dto.ResponseDTO;

import com.trading.app.entity.Item;
import com.trading.app.entity.ItemComment;
import com.trading.app.entity.ItemImage;
import com.trading.app.entity.ItemSwapType;
import com.trading.app.entity.Lookup;
import com.trading.app.repository.ItemCommentRepository;
import com.trading.app.repository.ItemRepository;
import com.trading.app.repository.UserProfileRepository;
import com.trading.app.util.TradingAppUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

import static com.trading.app.util.TradingAppConstant.SUCCESS_OPERATION_MSG;
import static com.trading.app.util.TradingAppConstant.RESPONSE_SUCCESS_CODE;
import static com.trading.app.util.TradingAppConstant.ITEM_AVAILABLE_STATUS;
import static com.trading.app.util.TradingAppConstant.ITEM_DELETED_STATUS;

@Service("itemService")
@Transactional
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserProfileRepository userProfileRepository;
	@Autowired
	private ItemCommentRepository itemCommentRepository;
	@Autowired
	private TradingAppUtil tradingUtil;
	@Autowired
	private ModelMapper mapper;

	public ResponseDTO<?> saveItem(ItemDTO itemDTO) {
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		if (itemDTO.getItemId() != null) {
			List<ItemSwapType> swapTypes = new ArrayList<ItemSwapType>();
			List<ItemImage> itemImages = new ArrayList<ItemImage>();
			List<ItemSwapTypeDTO> swapTypesDTO = itemDTO.getItemSwapTypes();
			List<ItemImageDTO> itemImagesDTO = itemDTO.getItemImages();

			Item retrievedItem = itemRepository.findByItemId(itemDTO.getItemId());
			Item copyItem = mapper.map(itemDTO, Item.class);
			String[] ignorePropertyNames = tradingUtil.getIgnorePropertyNames(copyItem, "itemSwapTypes", "itemImages");
			BeanUtils.copyProperties(copyItem, retrievedItem, ignorePropertyNames);
			retrievedItem.setModificationDate(new Date());
			// Saving Item Swap Types
			retrievedItem.getItemSwapTypes().clear();
			if (swapTypesDTO != null) {
				swapTypesDTO.forEach(typeDto -> {
					ItemSwapType item = mapper.map(typeDto, ItemSwapType.class);
					item.setItem(retrievedItem);
					swapTypes.add(item);
				});
			}
			retrievedItem.getItemSwapTypes().addAll(swapTypes);
			// Saving Item Images
			retrievedItem.getItemImages().clear();
			if (itemImagesDTO != null) {
				itemImagesDTO.forEach(imageDto -> {
					ItemImage itemImage = new ItemImage();
					itemImage.setItemImage(DatatypeConverter.parseHexBinary(imageDto.getImage()));
					itemImage.setCreationDate(new Date());
					itemImage.setItem(retrievedItem);
					itemImages.add(itemImage);
				});
			}
			retrievedItem.getItemImages().addAll(itemImages);

			itemRepository.save(retrievedItem);
		} else {
			List<ItemImage> itemImages = new ArrayList<ItemImage>();
			List<ItemImageDTO> itemImagesDTO = itemDTO.getItemImages();
			itemDTO.setCreationDate(new Date());
			Item item = mapper.map(itemDTO, Item.class);
			Lookup availableItem = new Lookup();
			availableItem.setLookupId(ITEM_AVAILABLE_STATUS);
			item.setItemStatus(availableItem);
			item.setItemOwner(userProfileRepository.findByEmail(loggedInUserEmail));
			item.setCreationDate(new Date());
			// Saving Item Swap Types
			List<ItemSwapType> swapTypes = item.getItemSwapTypes();
			if (swapTypes != null) {
				swapTypes.forEach(type -> {
					type.setItem(item);
				});
			}
			// Saving Item Images
			if (itemImagesDTO != null) {
				itemImagesDTO.forEach(imageDto -> {
					ItemImage itemImage = new ItemImage();
					itemImage.setItemImage(DatatypeConverter.parseHexBinary(imageDto.getImage()));
					itemImage.setCreationDate(new Date());
					itemImage.setItem(item);
					itemImages.add(itemImage);
				});
				item.setItemImages(itemImages);
			}
			itemRepository.save(item);
		}

		ResponseDTO<?> response = new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, SUCCESS_OPERATION_MSG);
		return response;
	}

	public ResponseDTO<?> getItemDetails(ItemDTO itemDTO) {
		List<ItemImageDTO> itemImagesDTO = new ArrayList<ItemImageDTO>();
		ItemDTO itemDto = new ItemDTO();
		Item retrievedItem = itemRepository.findByItemId(itemDTO.getItemId());
		if (retrievedItem != null) {
			List<ItemImage> itemImages = retrievedItem.getItemImages();
			if (itemImages != null) {
				itemImages.forEach(image -> {
					ItemImageDTO itemImageDTO = new ItemImageDTO();
					itemImageDTO.setImage(DatatypeConverter.printHexBinary(image.getItemImage()));
					itemImagesDTO.add(itemImageDTO);
				});
			}
			mapper.map(retrievedItem, itemDto);
			itemDto.setItemImages(itemImagesDTO);
		}
		return new ResponseDTO<ItemDTO>(RESPONSE_SUCCESS_CODE, itemDto);
	}

	public ResponseDTO<?> removeItem(ItemDTO itemDTO) {
		Item retrievedItem = itemRepository.findByItemId(itemDTO.getItemId());
		Lookup ItemDeleteStatus = new Lookup();
		ItemDeleteStatus.setLookupId(ITEM_DELETED_STATUS);
		retrievedItem.setItemStatus(ItemDeleteStatus);
		retrievedItem.setModificationDate(new Date());
		retrievedItem.getItemImages().clear();
		retrievedItem.getItemSwapTypes().clear();
		itemRepository.save(retrievedItem);
		ResponseDTO<?> response = new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, SUCCESS_OPERATION_MSG);
		return response;
	}
	
	public ResponseDTO<?> saveItemComment(ItemCommentDTO itemCommentDTO) {
		String loggedInUserEmail = tradingUtil.getLoggedInUserEmail();
		ItemComment itemComment = mapper.map(itemCommentDTO, ItemComment.class);
		itemComment.setCreationDate(new Date());
		itemComment.setUserProfile(userProfileRepository.findByEmail(loggedInUserEmail));
		itemCommentRepository.save(itemComment);
		ResponseDTO<?> response = new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, SUCCESS_OPERATION_MSG);
		return response;
	}

	public ResponseDTO<?> removeItemComment(ItemCommentDTO itemCommentDTO) {
		itemCommentRepository.deleteById(itemCommentDTO.getId());
		ResponseDTO<?> response = new ResponseDTO<String>(RESPONSE_SUCCESS_CODE, SUCCESS_OPERATION_MSG);
		return response;
	}
}
