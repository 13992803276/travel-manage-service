package com.tw.travelmanage.infrastructure.repository;

import com.tw.travelmanage.infrastructure.repository.entity.TravelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lexu
 */
@Repository
public interface TravelUserRepository extends JpaRepository<TravelUser, Integer> {

    TravelUser findTravelUserById(Integer Id);
}
