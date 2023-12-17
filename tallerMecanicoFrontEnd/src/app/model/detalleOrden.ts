import { Servicio } from "./servicio";

export class DetalleOrden {
    id: number;
    servicio: Servicio;
    cantidad: number;
    precioIndividual: number;
    precioTotal: number;
}