package com.my.contactbook.repository;

import com.my.contactbook.entity.SlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlotRepository extends JpaRepository<SlotEntity, Long> {

    Optional<SlotEntity> findBySlotName(String slotName);
}
