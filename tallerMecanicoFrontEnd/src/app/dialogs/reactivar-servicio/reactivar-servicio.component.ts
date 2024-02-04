import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ServicioService } from '../../services/servicio.service';
import { Servicio } from 'src/app/model/servicio';
import Toastify from 'toastify-js';


@Component({
  selector: 'app-reactivar-servicio',
  templateUrl: './reactivar-servicio.component.html',
  styleUrls: ['./reactivar-servicio.component.css']
})
export class ReactivarServicioComponent implements OnInit {
  servicioConsultar: Servicio;
  columnas: string[] = ['descripcion', 'nombre', 'precio','impuesto', 'acciones'];
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
          Toastify({
            text: 'Servicio reactivado',
            duration: 3000,
            destination: 'https://github.com/apvarun/toastify-js',
            newWindow: true,
            close: true,
            gravity: 'bottom', // Cambiado a "bottom" para colocarlo en la parte inferior
            position: 'right', // Cambiado a "right" para colocarlo en la esquina derecha
            stopOnFocus: true,
            style: {
              background: 'black', // Cambiado a negro
            },
            onClick: function () {},
          }).showToast();
      
      }, error:(err)=> {
          console.log('error');
          Toastify({
            text: 'Problema al reactivar el servicio',
            duration: 3000,
            destination: 'https://github.com/apvarun/toastify-js',
            newWindow: true,
            close: true,
            gravity: 'bottom', // Cambiado a "bottom" para colocarlo en la parte inferior
            position: 'right', // Cambiado a "right" para colocarlo en la esquina derecha
            stopOnFocus: true,
            style: {
              background: 'red', // Cambiado a negro
              color: 'black'
            },
            onClick: function () {},
          }).showToast();
      },
    });
  }
}
