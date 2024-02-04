import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Tecnico } from 'src/app/model/tecnico';
import { TecnicoService } from 'src/app/services/tecnico.service';
import Toastify from 'toastify-js';


@Component({
  selector: 'app-reativar-tecnico',
  templateUrl: './reativar-tecnico.component.html',
  styleUrls: ['./reativar-tecnico.component.css']
})
export class ReativarTecnicoComponent {
  columnas: string[] = [
    'Nombre',
    'Apellido',
    'DNI',
    'Telefono',
    'Email',
    'Direccion',
    'Legajo',
    'Acciones',
  ];
  dataSource;
  tecnicoConsultar: Tecnico;

  constructor(
    public dialogRef: MatDialogRef<ReativarTecnicoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private tecnicoService: TecnicoService) { }

  onNoClick(): void {
    this.dialogRef.close();
  }
  ngOnInit(): void {
      this.tecnicoConsultar = new Tecnico();
      this.onFiltrar();
  }

  onFiltrar(){
    this.tecnicoConsultar.activo = false;
    this.tecnicoService.onConsultar(this.tecnicoConsultar).subscribe({
      next: (data) => {
        console.log('Data: ', data);
        this.dataSource = data;
        Toastify({
          text: 'Tecnico reactivado',
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
      },
      complete: () => {},
      error: (error) => {
        Toastify({
          text: 'Problema al reactivar el tecnico',
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

  onReactivar(id: number){
    console.log('id: ', id);
    this.tecnicoService.reactivar(id).subscribe({
      next:(value)=> {
          console.log('activado');
          this.onFiltrar();
      
      }, error:(err)=> {
          console.log('error');
      },
    });
  }
}
