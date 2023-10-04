package com.TallerMecanicoSpring.TM.service;


import com.TallerMecanicoSpring.TM.model.Servicio;
import com.TallerMecanicoSpring.TM.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ServicioService{
     @Autowired
    ServicioRepository servicioRepository;

     public List<Servicio> findAllServicios(){
         return servicioRepository.findAll();
     }

     public Optional<Servicio> findById(Long id){
         return servicioRepository.findById(id);
     }


    public Servicio saveServicio(Servicio servicio){
        return servicioRepository.save(servicio);
    }

}
