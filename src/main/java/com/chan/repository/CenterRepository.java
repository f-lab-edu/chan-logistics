package com.chan.repository;

import com.chan.domain.Center;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CenterRepository extends JpaRepository<Center, Long> {

    Center findByLocalCode(String localCode);
}
