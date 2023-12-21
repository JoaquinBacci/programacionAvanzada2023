import { Component, OnInit } from '@angular/core';
import { TecnicoService } from '../services/tecnico.service';
import { ServicioService } from '../services/servicio.service';
import { Tecnico } from '../model/tecnico';
import { Marca } from '../model/marca';
import { Servicio } from '../model/servicio';
import { MarcaService } from '../services/marca.service';

import { RqReporteCantServMarca } from '../model/RqReporteCantServMarcaEntreFecha';

import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { OrdenService } from '../services/orden.service';
import { DatePipe } from '@angular/common';
import { ImprRpService } from '../services/impr-rp.service';

@Component({
  selector: 'app-reporte',
  templateUrl: './reporte.component.html',
  styleUrls: ['./reporte.component.css']
})
export class ReporteComponent implements OnInit {

  columnasReporteCantServ = ['marca', 'servicio', 'cantidad'];
  dataSourceRpMarcaServ: any[];

  dataTecnicos: Tecnico[];
  dataServicio: Servicio[];
  dataMarcas: Marca[];

  fechaDesde: Date;
  fechaHasta: Date;
  tecnicos: Tecnico[] = [];
  servicios: Servicio[] = [];
  marcas: Marca[] = [];

  constructor(
    private datePipe: DatePipe,
    private ordenService: OrdenService,
    private tecnicoService: TecnicoService,
    private marcaService: MarcaService,
    private servicioService: ServicioService,
    private servImprReporte: ImprRpService
  ) { }
  ngOnInit(): void {

    this.servicioService.getAllServicio().subscribe({
      next: (value) => {
        this.dataServicio = value;
      }, error: (err) => {
        console.log(err)
      },
    });

    this.marcaService.getAllMarcas().subscribe({
      next: (value) => {
        this.dataMarcas = value;
      }, error: (err) => {
        console.log(err)
      },
    });

    this.tecnicoService.getAll().subscribe({
      next: (value) => {
        this.dataTecnicos = value;
      }, error: (err) => {
        console.log(err)
      },
    })
  }

  // Manejador de cambio de fecha de inicio
  onFechaDesdeChange(event: MatDatepickerInputEvent<Date>) {
    this.fechaDesde = event.value;
  }

  // Manejador de cambio de fecha de finalización
  onFechaHastaChange(event: MatDatepickerInputEvent<Date>) {
    this.fechaHasta = event.value;
  }

  addTecnico(t: Tecnico) {
    const existeTecnico = this.tecnicos.some(tecnico => tecnico.id === t.id);
    if (!existeTecnico) {
      this.tecnicos.push(t);
    }
  }

  removeTecnico(t: Tecnico) {
    const index = this.tecnicos.findIndex(tecnico => tecnico.id === t.id);

    if (index !== -1) {
      this.tecnicos.splice(index, 1);
    }
  }

  addMarca(t: Marca) {
    const existeMarca = this.marcas.some(marca => marca.id === t.id);
    if (!existeMarca) {
      this.marcas.push(t);
    }
  }

  removeMarca(t: Marca) {
    const index = this.marcas.findIndex(marca => marca.id === t.id);

    if (index !== -1) {
      this.marcas.splice(index, 1);
    }
  }

  addServicio(s: Servicio) {
    const servicioExistente = this.servicios.some(servicio => servicio.id === s.id);
    if (!servicioExistente) {
      this.servicios.push(s);
    }
  }

  removeServicio(s: Servicio) {
    const index = this.servicios.findIndex(servicio => servicio.id === s.id);

    if (index !== -1) {
      this.servicios.splice(index, 1);
    }
  }
  mostrarDatos() {
    let rq: RqReporteCantServMarca = new RqReporteCantServMarca();

    rq.fechaDesde = this.datePipe.transform(this.fechaDesde, 'dd-MM-yyyy');
    rq.fechaHasta = this.datePipe.transform(this.fechaHasta, 'dd-MM-yyyy');
    rq.idsServicios = [];
    this.servicios.forEach((s) => { rq.idsServicios.push(s.id) });
    rq.idsMarcas = [];
    this.marcas.forEach((m) => { rq.idsMarcas.push(m.id) });
    console.log(rq);
    this.ordenService.reporteMarcaServ(rq).subscribe({
      next: (value) => {
        this.dataSourceRpMarcaServ = value;

      }, error: (error) => { console.log(error) }
    });
    /*console.log('Fecha Desde:', this.fechaDesde);
    console.log('Fecha Hasta:', this.fechaHasta);
    console.log('Tecnicos:', this.tecnicos);
    console.log('Marcas:', this.marcas);*/
  }

  imprimirReporte() {
    const encabezado = ["Marca", "Nombre Servicio", "Cantidad"]
   
    
    const cuerpo = this.dataSourceRpMarcaServ.map(obj => [obj.marca, obj.nombreServicio, obj.cantidad]);
    this.servImprReporte.imprimir(encabezado, cuerpo, "Listado Cantidad de Servicios por Marca", true);
    
  }

}
