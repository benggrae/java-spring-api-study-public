package com.project.springapistudy.beverages.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {

    List<Beverage> findByDeletedFalseOrderByUpdatedDesc();

    Optional<Beverage> findByIdAndDeletedFalse(long id);

}
