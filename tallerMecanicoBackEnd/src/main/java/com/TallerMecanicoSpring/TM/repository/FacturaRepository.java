package com.TallerMecanicoSpring.TM.repository;

import com.TallerMecanicoSpring.TM.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura,Long> {
    
}
