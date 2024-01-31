import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Tecnico } from 'src/app/model/tecnico';
import { TecnicoService } from 'src/app/services/tecnico.service';

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
      },
      complete: () => {},
      error: (error) => {},
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
