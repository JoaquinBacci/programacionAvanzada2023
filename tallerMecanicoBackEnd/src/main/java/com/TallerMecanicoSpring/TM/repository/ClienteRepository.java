/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.TallerMecanicoSpring.TM.repository;

import com.TallerMecanicoSpring.TM.model.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author maite
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Corregir la firma del método findAll
    Page<Cliente> findAll(org.springframework.data.domain.Pageable pageable);
}
