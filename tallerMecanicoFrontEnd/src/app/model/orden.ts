import { Cliente } from "./cliente";
import { DetalleOrden } from "./detalleOrden";
import { Tecnico } from "./tecnico";
import { Vehiculo } from "./vehiculo";

export class Orden {
    id: number;
    activo: boolean;
    descripcion: string;
    detallesOrden: DetalleOrden[];
    tecnico: Tecnico;
    vehiculo: Vehiculo;
    // cliente: Cliente;
    fechaIngreso: string;
  }