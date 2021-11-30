package com.zonework.osvaldo.repository;

import com.zonework.osvaldo.model.Place;
import com.zonework.osvaldo.model.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

}
