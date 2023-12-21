import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { OrdenService } from '../services/orden.service';
import { Orden } from '../model/orden';

@Component({
  selector: 'app-orden-xcliente',
  templateUrl: './orden-xcliente.component.html',
  styleUrls: ['./orden-xcliente.component.css']
})
export class OrdenXclienteComponent implements OnInit {

  dataSourceOrdenes: Orden[];
  displayedColumns: string[] = ['nroOrden','cliente', 'vehiculo','fechaIngreso','tecnico','estado'];

  constructor( 
    public ordenService: OrdenService,
    public dialogRef: MatDialogRef<OrdenXclienteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: number
  ){}

  ngOnInit(): void {
      this.ordenService.getOrdenByIdCliente(this.data).subscribe({
        next:(data)=>{
          this.dataSourceOrdenes = data;
          console.log('data: ', data);
        }, error: (err)=>{
          console.log(err);
        }
    });
  }
}
