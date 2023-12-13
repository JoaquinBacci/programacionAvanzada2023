package com.TallerMecanicoSpring.TM.rest;

import com.TallerMecanicoSpring.TM.model.*;
import com.TallerMecanicoSpring.TM.repository.*;
import com.TallerMecanicoSpring.TM.service.ClienteService;
import com.TallerMecanicoSpring.TM.service.OrdenService;
import com.TallerMecanicoSpring.TM.service.ReporteOrdenService;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reporte/")
public class ReporteOrdenRest {
    
    ReporteOrdenService rptOrdenServ = new ReporteOrdenService();
    
    @PostMapping("orden/")
    private ResponseEntity<List<RsReporteTecServEntreFecha>> search(@RequestBody RqReporteTecServEntreFecha rptOrdenRq){
        return ResponseEntity.ok(rptOrdenServ.reporteOrenesPeriodo(rptOrdenRq));
    }

    
    
    
    
    
}
