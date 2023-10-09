import { Cliente } from "./cliente";
import { Marca } from "./marca";
import { Modelo } from "./modelo";

export class Vehiculo {
    id: number;
    anio: number;
    kilometraje: number;
    patente: string;
    marca: Marca;
    modelo: Modelo;
    cliente: Cliente;
    activo: boolean;
}