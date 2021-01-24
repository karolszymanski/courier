package com.szymanski.courierapp.data.db.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.szymanski.courierapp.data.db.entity.Parcel;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {

  Optional<Parcel> findByLabelId(Long labelId);

}
