import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ServicioService } from '../../services/servicio.service';
import { Servicio } from 'src/app/model/servicio';

@Component({
  selector: 'app-reactivar-servicio',
  templateUrl: './reactivar-servicio.component.html',
  styleUrls: ['./reactivar-servicio.component.css']
})
export class ReactivarServicioComponent implements OnInit {
  servicioConsultar: Servicio;
  columnas: string[] = ['descripcion', 'nombre', 'precio', 'acciones'];
  dataSource;
  
  constructor(
    public dialogRef: MatDialogRef<ReactivarServicioComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private service: ServicioService) { }

  onNoClick(): void {
    this.dialogRef.close();
  }
  ngOnInit(): void {
    this.servicioConsultar = new Servicio();
      this.onFiltrar();
  }

  onFiltrar(){
    this.servicioConsultar.activo = false;
    this.service.filterServicio(this.servicioConsultar).subscribe({
      next: (data) => {
        console.log('servicios: ', data);
        this.dataSource = data;
      },
      complete: () => {},
      error: (error) => {
        console.log('ERROR ', error);
      },
    });
  }

  onReactivar(id: number){
    console.log('id: ', id);
    this.service.reactivar(id).subscribe({
      next:(value)=> {
          console.log('activado');
          this.onFiltrar();
      
      }, error:(err)=> {
          console.log('error');
      },
    });
  }
}
