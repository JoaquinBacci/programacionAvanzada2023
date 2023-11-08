package com.TallerMecanicoSpring.TM.repository;

import com.TallerMecanicoSpring.TM.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden,Long> {
}
