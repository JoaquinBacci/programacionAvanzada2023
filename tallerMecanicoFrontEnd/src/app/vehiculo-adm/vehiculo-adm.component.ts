import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Vehiculo } from '../model/vehiculo';
import { Marca } from '../model/marca';
import { Modelo } from '../model/modelo';
import { Cliente } from '../model/cliente';
import { VehiculoService } from '../services/vehiculo.service';
import { MarcaService } from '../services/marca.service';
import { ModeloService } from '../services/modelo.service';
import { ClienteService } from '../services/cliente.service';
@Component({
  selector: 'app-vehiculo-adm',
  templateUrl: './vehiculo-adm.component.html',
  styleUrls: ['./vehiculo-adm.component.css']
})
export class VehiculoAdmComponent {
  idVehiculoUpdate: number = undefined;
  vehiculoConsultar: Vehiculo;
  filtroCliente: string;
  modoEdicion: boolean = false;
  columnas: string[] = ['Patente','Marca', 'Modelo','Kilometros','Cliente','Acciones']
  dataSource
  marcas: Marca[] = [];
  modelos: Modelo[] = [];
  clientes: Cliente[];

  form: FormGroup;

  constructor(
    private vehiculoService: VehiculoService,
    private marcaService: MarcaService,
    private modeloService: ModeloService,
    private clienteService: ClienteService,
    private fb: FormBuilder
  ){
    this.form = this.fb.group({
      patente: ['',[Validators.required, Validators.pattern('/^[A-Z]{2,3}\d{3}[A-Z]{2}$/')]],
      marca: ['',[Validators.required]],
      modelo: ['', [Validators.required] ],
      kilometraje: ['',[Validators.maxLength(7)]],
      cliente: ['',[Validators.required, Validators.maxLength(50)]],
    })
  }

  ngOnInit(): void {
    console.log('On init');
    this.vehiculoConsultar = new Vehiculo();
    this.vehiculoConsultar.patente = "";
    this.vehiculoConsultar.kilometraje = null;
    this.vehiculoConsultar.marca = new Marca();
    this.vehiculoConsultar.modelo = new Modelo();
    this.vehiculoConsultar.cliente = new Cliente();

    this.marcaService.getAllMarcas().subscribe((data)=> this.marcas = data);
    this.modeloService.getAllModelo().subscribe((data)=> this.modelos = data);
    this.clienteService.getAll().subscribe((data)=> this.clientes = data);
    
    
    this.onConsultar();
  }

  onConsultar(){
    this.vehiculoService.onConsultar(this.vehiculoConsultar).subscribe({
      next:(data)=>{
        console.log('Data: ', data)
        this.dataSource = data
      }, complete:()=>{

      }, error:(error)=>{

      }
    });
  }

  onGuardar(){
    console.log('On Guardar')
    let vehiculoRq: Vehiculo = new Vehiculo();
    vehiculoRq.activo = true;
    vehiculoRq.patente = this.form.get('patente').value;
    vehiculoRq.kilometraje = this.form.get('kilometraje').value;
    vehiculoRq.marca = this.marcas.find((m) => m.id = this.form.get('marca').value);
    vehiculoRq.modelo = this.modelos.find((m)=> m.id = this.form.get('modelo').value);
    vehiculoRq.cliente = this.form.get('cliente').value;
    

    if(!this.modoEdicion){
      //guardar
      this.vehiculoService.onSave(vehiculoRq).subscribe({
        next:(data)=>{
          if(data.id!= null){
            console.log('dataOK: ', data);
          } else {
            console.log('dataNULL: ', data);
          }
          
        }, complete: ()=>{
          this.form.reset();
          this.onConsultar();
        }, error:(error)=>{
          console.log('error: ', error);
        }
      });
    } else{
      //editar
      console.log('Editar: ');
      vehiculoRq.id = this.idVehiculoUpdate;
    
      this.vehiculoService.onUpdate(vehiculoRq).subscribe({
        next:(data)=>{
          if(data.id!= null){
            console.log('dataOK: ', data);
          } else {
            console.log('dataNULL: ', data);
          }
          
        }, complete: ()=>{
          this.onConsultar();
          this.form.reset();
        }, error:(error)=>{
          console.log('error: ', error);
        }
      });

    }

  }

  onEditar(vehiculo: Vehiculo){
    this.modoEdicion = true;
    this.idVehiculoUpdate = vehiculo.id;
    this.form.get('patente').setValue(vehiculo.patente);
    this.form.get('kilomentraje').setValue(vehiculo.kilometraje);
    this.form.get('marca').setValue(vehiculo.marca.id);
    this.form.get('modelo').setValue(vehiculo.modelo.id);
    this.form.get('cliente').setValue(vehiculo.cliente.id);
  }

  onEliminar(id: number){
    this.vehiculoService.onDelete(id).subscribe({
      next:(data)=>{
        if(data){
          console.log('dataOK: ', data);
        } else {
          console.log('dataNULL: ', data);
        }
        
      }, complete: ()=>{
        this.onConsultar();
      }, error:(error)=>{
        console.log('error: ', error);
      }
    });
  }
  onCancelar(){
    this.form.reset();
    this.modoEdicion = false;
    this.idVehiculoUpdate = undefined;
  }

  onValidarCampo(campo: string){

  }
}
  

