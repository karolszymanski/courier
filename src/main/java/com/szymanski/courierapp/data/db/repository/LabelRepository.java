package com.szymanski.courierapp.data.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.szymanski.courierapp.data.db.entity.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

}
