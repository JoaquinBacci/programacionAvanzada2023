/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.TallerMecanicoSpring.TM.repository;

import com.TallerMecanicoSpring.TM.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TecnicoRepository extends JpaRepository<Tecnico, Long>{

    Optional<Tecnico> findByEmail(String email);
    
}
