import { Component, OnInit } from '@angular/core';
import { TecnicoService } from '../services/tecnico.service';
import { Tecnico } from '../model/tecnico';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-tecnico-adm',
  templateUrl: './tecnico-adm.component.html',
  styleUrls: ['./tecnico-adm.component.css']
})
export class TecnicoAdmComponent implements OnInit{
  idTecnitoUpdate: number = undefined;
  tecnicoConsultar: Tecnico;
  modoEdicion: boolean = false;
  columnas: string[] = ['Nombre','Apellido', 'DNI','Telefono','Email', 'Direccion', 'Legajo','Acciones']
  dataSource
  

  form: FormGroup;

  constructor(
    private tecnicoService: TecnicoService,
    private fb: FormBuilder
  ){
    this.form = this.fb.group({
      dni: ['',[Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      num_tel: ['',[Validators.required, Validators.minLength(8), Validators.maxLength(13)]],
      legajo: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(5)] ],
      nombre: ['',[Validators.required, Validators.maxLength(50)]],
      apellido: ['',[Validators.required, Validators.maxLength(50)]],
      direccion: ['',[Validators.maxLength(50)]],
      email: ['',[Validators.maxLength(80),Validators.email]]
    })
  }

  ngOnInit(): void {
    console.log('On init');
    this.tecnicoConsultar = new Tecnico();
    this.tecnicoConsultar.nombre = "";
    this.tecnicoConsultar.apellido = '';
    this.tecnicoConsultar.num_tel = '';
    this.tecnicoConsultar.email = '';
    this.tecnicoConsultar.direccion = '';
    this.tecnicoConsultar.dni = null;
    this.tecnicoConsultar.legajo = null;
    this.onConsultar();
  }

  onConsultar(){
    this.tecnicoService.onConsultar(this.tecnicoConsultar).subscribe({
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
    let tecnicoRq: Tecnico = new Tecnico();
    tecnicoRq.activo = true;
    tecnicoRq.apellido = this.form.get('apellido').value;
    tecnicoRq.direccion = this.form.get('direccion').value;
    tecnicoRq.num_tel = this.form.get('num_tel').value;
    tecnicoRq.nombre = this.form.get('nombre').value;
    tecnicoRq.dni = this.form.get('dni').value;
    tecnicoRq.legajo = this.form.get('legajo').value;
    tecnicoRq.email = this.form.get('email').value;

    if(!this.modoEdicion){
      //guardar
      this.tecnicoService.onGuardar(tecnicoRq).subscribe({
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
      tecnicoRq.id = this.idTecnitoUpdate;
      tecnicoRq.activo = true;
      /* tecnicoRq.apellido = tecnico.apellido   ;   
      tecnicoRq.direccion = tecnico.direccion;
      tecnicoRq.num_tel = tecnico.num_tel ;
      tecnicoRq.nombre = tecnico.nombre;
      tecnicoRq.dni = tecnico.dni ;
      tecnicoRq.legajo = tecnico.legajo; */    

      this.tecnicoService.onUpdate(tecnicoRq).subscribe({
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

  onEditar(tecnico: Tecnico){
    this.modoEdicion = true;
    this.idTecnitoUpdate = tecnico.id;
    this.form.get('apellido').setValue(tecnico.apellido);
    this.form.get('direccion').setValue(tecnico.direccion);
    this.form.get('num_tel').setValue(tecnico.num_tel);
    this.form.get('nombre').setValue(tecnico.nombre);
    this.form.get('dni').setValue(tecnico.dni);
    this.form.get('legajo').setValue(tecnico.legajo);
    this.form.get('email').setValue(tecnico.email);
    
  }

  onEliminar(id: number){
    this.tecnicoService.onDeleteById(id).subscribe({
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
    this.idTecnitoUpdate = undefined;
  }

  onValidarCampo(campo: string){

  }
}
