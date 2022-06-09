package com.tw.travelmanage.infrastructure.repository;

import com.tw.travelmanage.infrastructure.repository.entity.TravelUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lexu
 */
public interface TravelUserRepository extends JpaRepository<TravelUser, Integer> {

    TravelUser getTravelUserById(Integer userId);

    List<TravelUser> getAll();
}
