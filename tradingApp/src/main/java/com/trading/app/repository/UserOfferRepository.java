package com.trading.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trading.app.entity.Lookup;
import com.trading.app.entity.UserOffer;
import com.trading.app.entity.UserProfile;

public interface UserOfferRepository extends JpaRepository<UserOffer, Integer> {
	@Query("select u from UserOffer u where u.item.itemOwner=?1 and u.offerStatus in (?2) order by creationDate DESC")
	public List<UserOffer> getRecievedOffers(UserProfile itemOwner, List<Lookup> availableStatus);

	@Query("select u from UserOffer u where u.userProfile=?1 and u.offerStatus in (?2) order by creationDate DESC")
	public List<UserOffer> getSentOffers(UserProfile fromUser, List<Lookup> availableStatus);

	@Query("select u from UserOffer u where (u.userProfile=?1 or u.item.itemOwner=?1)  and u.offerStatus = ?2 order by modificationDate DESC")
	public List<UserOffer> getDeals(UserProfile fromUser, Lookup dealStatus);
}
