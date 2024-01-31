import { Injectable } from '@angular/core';
import { jsPDF } from "jspdf";
import autoTable from 'jspdf-autotable';

@Injectable({
  providedIn: 'root'
})
export class ImprRpService {

  constructor() { }

  imprimir(encabezado: string[], marcas: string[], servicios: string[], fechaDesde, fechaHasta, cuerpo: Array<any>, titulo: string, guardar?: boolean) {
    // Crear una instancia de jsPDF
    const doc = new jsPDF({
      orientation: "portrait",
      unit: "px",
      format: 'letter'
    });

    // Establecer el tamaño de letra para el título
    doc.setFontSize(16);

    // Agregar el título al documento centrado en la parte superior
    doc.text(titulo, doc.internal.pageSize.width / 2, 25, { align: 'center' });

    // Establecer el tamaño de letra para el listado
    doc.setFontSize(12);

    // Almacenar la posición Y antes de agregar la información del listado
    let posYInfoReporte = 50; // Ajusta según sea necesario

    // Agregar información del reporte antes de la tabla
    posYInfoReporte += this.imprimirTabla2x1(doc, marcas, servicios, 20, posYInfoReporte); // Función para imprimir la tabla 2x1

    // Formatear la fecha antes de imprimirla
    const fechaInicioFormateada = this.formatearFecha(fechaDesde);
    const fechaFinFormateada = this.formatearFecha(fechaHasta);

    // Centrar el texto del rango de fechas
    const fechaText = `Entre las fechas: ${fechaInicioFormateada} y ${fechaFinFormateada}`;
    const fechaTextWidth = doc.getTextWidth(fechaText);
    doc.text(fechaText, (doc.internal.pageSize.width - fechaTextWidth) / 2, posYInfoReporte + 10);

    // Agregar un espacio entre el título y la información del listado
    posYInfoReporte += 40;

    // Agregar una tabla al documento utilizando jsPDF-AutoTable
    autoTable(doc, {
      head: [encabezado],
      body: cuerpo,
      startY: posYInfoReporte + 10  // Ajusta la coordenada Y para que la tabla comience más abajo
    });

    // Opción para guardar el documento con un nombre específico
    if (guardar) {
      // Obtener la fecha y hora actual para generar un nombre único para el archivo PDF
      const hoy = new Date();
      const horas = hoy.getHours().toString().padStart(2, '0');
      const minutos = hoy.getMinutes().toString().padStart(2, '0');
      const segundos = hoy.getSeconds().toString().padStart(2, '0');
      const horaActual = `${horas}h${minutos}m${segundos}s`;

      // Guardar el documento con un nombre que incluye la fecha y hora actual
      doc.save(horaActual + "_" + hoy.getDate() + "-" + hoy.getMonth() + "-" + hoy.getFullYear() + '.pdf');
    } else {
      // Si no se especifica guardar, puedes realizar otras acciones aquí
    }
  }

  // Función para imprimir una tabla de 2x1
  private imprimirTabla2x1(doc: jsPDF, column1: string[], column2: string[], x: number, y: number): number {
    const maxRows = Math.max(column1.length, column2.length);
    const columnWidth = 200; // Ajusta según sea necesario
    const lineHeight = 15; // Ajusta según sea necesario

    for (let i = 0; i < maxRows; i++) {
      const text1 = i < column1.length ? column1[i] : '';
      const text2 = i < column2.length ? column2[i] : '';

      doc.text(text1, x, y + i * lineHeight);
      doc.text(text2, x + columnWidth, y + i * lineHeight);
    }

    return maxRows * lineHeight;
  }

  // Función para formatear la fecha
  private formatearFecha(fecha: Date): string {
    const opciones = { year: 'numeric', month: 'short', day: 'numeric' } as Intl.DateTimeFormatOptions;
    return fecha.toLocaleDateString('es-AR', opciones);
  }
}
