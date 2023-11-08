import { Modelo } from "../modelo";

export const MODELOS: Modelo[] = [
    {
      id: 1,
      nombre: 'Modelo A',
      activo: true,
      marca: { id: 1, nombre: 'Marca 1', activo: true }
    },
    {
      id: 2,
      nombre: 'Modelo B',
      activo: true,
      marca: { id: 2, nombre: 'Marca 2', activo: true }
    }
  ];