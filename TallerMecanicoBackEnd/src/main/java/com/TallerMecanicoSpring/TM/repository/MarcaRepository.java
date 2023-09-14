/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.TallerMecanicoSpring.TM.repository;

import com.TallerMecanicoSpring.TM.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author maite
 */
public interface MarcaRepository extends JpaRepository<Marca, Long>{
    
}
