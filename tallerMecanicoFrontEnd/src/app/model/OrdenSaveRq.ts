import { DetalleOrden } from "./detalleOrden";

export class OrdenSaveRq {
    idOrden: number;
    idTecnico: number;
    idVehiculo: number;
    detallesAGuardar: DetalleOrden[];
    detallesAEliminar: DetalleOrden[];
    descripcion: string;
}