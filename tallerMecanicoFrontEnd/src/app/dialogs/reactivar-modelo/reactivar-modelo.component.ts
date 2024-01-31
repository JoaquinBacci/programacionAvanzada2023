import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Modelo } from 'src/app/model/modelo';
import { ModeloService } from 'src/app/services/modelo.service';

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
      
      }, error:(err)=> {
          console.log('error');
      },
    });
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
