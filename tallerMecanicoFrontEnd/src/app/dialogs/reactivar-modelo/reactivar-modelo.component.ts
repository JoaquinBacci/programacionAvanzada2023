import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Modelo } from 'src/app/model/modelo';
import { ModeloService } from 'src/app/services/modelo.service';
import Toastify from 'toastify-js';



@Component({
  selector: 'app-reactivar-modelo',
  templateUrl: './reactivar-modelo.component.html',
  styleUrls: ['./reactivar-modelo.component.css']
})
export class ReactivarModeloComponent implements OnInit {
  columnas: string[] = ['nombre', 'marca', 'acciones'];
  dataSource;
  modeloConsultar: Modelo;
  
  constructor(public dialogRef: MatDialogRef<ReactivarModeloComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private service: ModeloService
  ){}

  ngOnInit(): void {
    this.modeloConsultar= new Modelo();
      this.onFiltrar();
  }

  onFiltrar(){
    this.modeloConsultar.activo=false;
    this.service.consultarModelo(this.modeloConsultar).subscribe({
      next: (data) => {
        console.log('modelos: ', data);
        this.dataSource = data;
      },
      complete: () => {},
      error: (error) => {
        console.log('ERROR ', error);
      },
    });
  }

  onReactivar(id: number){
    this.service.reactivar(id).subscribe({
      next:(value)=> {
          console.log('activado');
          this.onFiltrar();
          Toastify({
            text: 'Modelo reactivado',
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
            text: 'Problema al reactivar el modelo',
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
  onNoClick(): void {
    this.dialogRef.close();
  }
}
