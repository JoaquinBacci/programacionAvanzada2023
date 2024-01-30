import { Component, OnInit } from '@angular/core';
import { ModeloService } from 'src/app/services/modelo.service';

@Component({
  selector: 'app-reactivar-modelo',
  templateUrl: './reactivar-modelo.component.html',
  styleUrls: ['./reactivar-modelo.component.css']
})
export class ReactivarModeloComponent implements OnInit {
  constructor(
    private service: ModeloService
  ){}

  ngOnInit(): void {
      
  }

  onFiltrar(){

  }

  onReactivar(){
    
  }
}
