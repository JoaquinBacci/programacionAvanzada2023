import { Vehiculo } from "../vehiculo";

const VEHICULOS: Vehiculo[] = [
    {
      id: 1,
      kilometraje: 50000,
      patente: 'ABC123',
      marca: { id: 1, nombre: 'Marca 1', activo: true },
      modelo: { id: 1, nombre: 'Modelo A', activo: true, marca: { id: 1, nombre: 'Marca 1', activo: true } },
      cliente: { id: 1, dni: 11223344, num_tel: '555-123-4567', nombre: 'Pedro', apellido: 'Gómez', direccion: 'Calle Principal 789', email: 'pedro@example.com', activo: true },
      activo: true
    },
    {
      id: 2,
      kilometraje: 75000,
      patente: 'XYZ789',
      marca: { id: 2, nombre: 'Marca 2', activo: true },
      modelo: { id: 2, nombre: 'Modelo B', activo: true, marca: { id: 2, nombre: 'Marca 2', activo: true } },
      cliente: { id: 2, dni: 22334455, num_tel: '555-987-6543', nombre: 'Laura', apellido: 'Martínez', direccion: 'Avenida Secundaria 123', email: 'laura@example.com', activo: true },
      activo: true
    }
  ];