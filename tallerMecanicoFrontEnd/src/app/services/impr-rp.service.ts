import { Injectable } from '@angular/core';

import { jsPDF } from "jspdf";
import autoTable from 'jspdf-autotable'

@Injectable({
  providedIn: 'root'
})

export class ImprRpService {

  constructor() { }
  imprimir(encabezado: string[], cuerpo:Array<any>, titulo:string, guardar?:boolean){
    const doc = new jsPDF({
      orientation: "portrait",
      unit: "px",
      format: 'letter'
    });

    doc.text(titulo,doc.internal.pageSize.width/2, 25, {align: 'center'});
    
    autoTable(doc, {
      head: [encabezado],
      body: cuerpo,
    })


    if (guardar){
      const hoy = new Date();
      const horas = hoy.getHours().toString().padStart(2, '0');
      const minutos = hoy.getMinutes().toString().padStart(2, '0');
      const segundos = hoy.getSeconds().toString().padStart(2, '0');
      const horaActual = `${horas}h${minutos}m${segundos}s`;
      doc.save(horaActual + "_" + hoy.getDate()+ "-" + hoy.getMonth()+ "-" + hoy.getFullYear()+ '.pdf');
    } else{

    }

  }

  


}
