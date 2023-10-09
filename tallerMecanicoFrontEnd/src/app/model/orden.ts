import { DetalleOrden } from "./detalleOrden";
import { Tecnico } from "./tecnico";
import { Vehiculo } from "./vehiculo";

export class Orden {
    id: number;
    activo: boolean;
    detallesOrden: DetalleOrden[];
    tecnico: Tecnico;
    vehiculo: Vehiculo;
  }