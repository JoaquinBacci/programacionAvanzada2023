import { Injectable } from '@angular/core';
import { jsPDF } from 'jspdf';
import autoTable from 'jspdf-autotable';

@Injectable({
  providedIn: 'root',
})
export class ImprRpService {
  constructor() {}

  imprimir(
    encabezado: string[],
    marcas: string[],
    servicios: string[],
    fechaDesde,
    fechaHasta,
    cuerpo: Array<any>,
    titulo: string,
    guardar?: boolean
  ) {
    // Crear una instancia de jsPDF
    const doc = new jsPDF({
      orientation: 'portrait',
      unit: 'px',
      format: 'letter',
    });

    // Establecer el tamaño de letra para el título
    doc.setFontSize(16);

    // Agregar el título al documento centrado en la parte superior
    doc.text(titulo, doc.internal.pageSize.width / 2, 25, { align: 'center' });

    // Establecer el tamaño de letra para el listado
    doc.setFontSize(12);

    // Almacenar la posición Y antes de agregar la información del listado
    let posYInfoReporte = 40; // Ajusta según sea necesario

    // Agregar información del reporte antes de la tabla
    posYInfoReporte += this.imprimirTabla2x1(doc, marcas, servicios, 20, posYInfoReporte); // Función para imprimir la tabla 2x1

    // Formatear la fecha antes de imprimirla
    const fechaInicioFormateada = this.formatearFecha(fechaDesde);
    const fechaFinFormateada = this.formatearFecha(fechaHasta);

    // Centrar el texto del rango de fechas
    const fechaText = `Entre las fechas: ${fechaInicioFormateada} y ${fechaFinFormateada}`;
    const fechaTextWidth = doc.getTextWidth(fechaText);
    doc.text(fechaText, (doc.internal.pageSize.width - fechaTextWidth) / 2, posYInfoReporte);

    // Agregar un espacio entre el título y la información del listado
    posYInfoReporte += 10;

    // Agregar una tabla al documento utilizando jsPDF-AutoTable
    autoTable(doc, {
      head: [encabezado],
      body: cuerpo,
      startY: posYInfoReporte + 10, // Ajusta la coordenada Y para que la tabla comience más abajo
    });

    // Espacio adicional para la fecha y hora en la parte inferior
    const espacioParaFecha = 10;
    posYInfoReporte += espacioParaFecha;

    // Imprimir fecha y hora en la esquina inferior derecha
    const fechaHoraActual = new Date();
    const fechaHoraText = `${fechaHoraActual.toLocaleDateString()} ${fechaHoraActual.toLocaleTimeString()}`;
    doc.text(fechaHoraText, doc.internal.pageSize.width - 20, doc.internal.pageSize.height - 10, {
      align: 'right',
    });

    // Opción para guardar el documento con un nombre específico
    if (guardar) {
      // Obtener la fecha y hora actual para generar un nombre único para el archivo PDF
      const horas = fechaHoraActual.getHours().toString().padStart(2, '0');
      const minutos = fechaHoraActual.getMinutes().toString().padStart(2, '0');
      const segundos = fechaHoraActual.getSeconds().toString().padStart(2, '0');
      const horaActual = `${horas}h${minutos}m${segundos}s`;

      // Guardar el documento con un nombre que incluye la fecha y hora actual
      doc.save(`${horaActual}_${fechaHoraActual.getDate()}-${fechaHoraActual.getMonth() + 1}-${fechaHoraActual.getFullYear()}.pdf`);
    } else {
      // Si no se especifica guardar, puedes realizar otras acciones aquí
    }
  }

  // Función para imprimir una tabla de 2x1
  private imprimirTabla2x1(doc: jsPDF, column1: string[], column2: string[], x: number, y: number): number {
    const maxRows = Math.max(column1.length, column2.length);
    const columnWidth = 200; // Ajusta según sea necesario
    const lineHeight = 15; // Ajusta según sea necesario

    // Títulos antes de imprimir la columna 1
    doc.setFont('bold');
    doc.text('Marcas incluidas en el Informe', x, y);
    doc.setFont('normal');

    // Títulos antes de imprimir la columna 2
    doc.setFont('bold');
    doc.text('Servicios incluidos en el Informe', x + columnWidth, y);
    doc.setFont('normal');

    // Carácter para representar la aprobación (círculo)
    const caracterAprobado = '>';

    for (let i = 0; i < maxRows; i++) {
      const text1 = i < column1.length ? `${caracterAprobado} ${column1[i]}` : '';
      const text2 = i < column2.length ? `${caracterAprobado} ${column2[i]}` : '';

      doc.text(text1, x, y + (i + 1) * lineHeight);
      doc.text(text2, x + columnWidth, y + (i + 1) * lineHeight);
    }

    return (maxRows + 1) * lineHeight + 20; // Ajusta según sea necesario
  }

  // Función para formatear la fecha
  private formatearFecha(fecha: Date): string {
    const opciones = { year: 'numeric', month: 'short', day: 'numeric' } as Intl.DateTimeFormatOptions;
    return fecha.toLocaleDateString('es-AR', opciones);
  }
}

