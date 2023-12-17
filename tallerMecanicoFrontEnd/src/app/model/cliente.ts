import { Vehiculo } from "./vehiculo";

export class Cliente{
    id: number;
    dni: number;
    num_tel: string;
    nombre: string;
    apellido: string;
    direccion: string;
    email: string;
    activo: boolean;
    vehiculos?: Vehiculo[];
    licenciaConducir: string;
}