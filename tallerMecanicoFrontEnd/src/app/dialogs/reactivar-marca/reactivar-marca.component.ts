import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MarcaService } from '../../services/marca.service';
import { Marca } from 'src/app/model/marca';

@Component({
  selector: 'app-reactivar-marca',
  templateUrl: './reactivar-marca.component.html',
  styleUrls: ['./reactivar-marca.component.css']
})
export class ReactivarMarcaComponent implements OnInit{
  nombreMarca: string;
  dataSource
  columnas: string[] = ['nombre', 'acciones'];
  
  constructor(
    public dialogRef: MatDialogRef<ReactivarMarcaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private marcaService: MarcaService ) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
      this.onFiltrar()
  }

  onFiltrar(){
    let marca: Marca = new Marca();
    marca.nombre = this.nombreMarca;
    marca.activo = false;
    this.marcaService.consultarMarca(marca).subscribe({
      next: (data) => {
        console.log('marcas: ', data);
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
    this.marcaService.reactivar(id).subscribe({
      next:(value)=> {
          console.log('activado');
          this.onFiltrar();
      
      }, error:(err)=> {
          console.log('error');
      },
    });
  }
}
