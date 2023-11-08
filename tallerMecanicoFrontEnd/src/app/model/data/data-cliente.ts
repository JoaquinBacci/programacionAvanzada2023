import { Cliente } from "../cliente";

export const CLIENTES: Cliente[] = [
    {
      id: 1,
      dni: 11223344,
      num_tel: '555-123-4567',
      nombre: 'Pedro',
      apellido: 'Gómez',
      direccion: 'Calle Principal 789',
      email: 'pedro@example.com',
      activo: true,
      vehiculos: []
    },
    {
      id: 2,
      dni: 22334455,
      num_tel: '555-987-6543',
      nombre: 'Laura',
      apellido: 'Martínez',
      direccion: 'Avenida Secundaria 123',
      email: 'laura@example.com',
      activo: true,
      vehiculos: []
    }
  ];