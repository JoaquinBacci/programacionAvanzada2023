import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/enviroments/enviroment';
import { Cliente } from '../model/cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private API_REST: string = environment.apiUrl;
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  
  constructor(
    private http: HttpClient
  ) { }

  /* @GetMapping
  private ResponseEntity<List<Cliente>> getAllClientes(){
      return ResponseEntity.ok(clienteService.findAll());
  } */

  public getAll(): Observable<any>{ 
    return this.http.get(`${this.API_REST}/cliente/`);
  }

  /* @PostMapping("filtrar/")
  private ResponseEntity<List<Cliente>> search(@RequestBody Cliente clienteRq){
      return ResponseEntity.ok(this.clienteService.filtrar(clienteRq));
  } */

  public onConsultar(clienteRq: Cliente): Observable<any>{ 
    return this.http.post(`${this.API_REST}/cliente/filtrar/`, clienteRq, {headers: this.headers});
  }

  public getDesactivados(): Observable<Cliente[]>{
    return this.http.get<Cliente[]>(`${this.API_REST}/cliente/deactivados/`);
  }
  
  /* @PutMapping("update/")
  private ResponseEntity<Cliente> update(@RequestBody Cliente clienteRq){
      return ResponseEntity.ok(this.clienteService.update(clienteRq));
  } */

  public update(clienteRq: Cliente): Observable<any>{
    return this.http.put(`${this.API_REST}/cliente/update/`, clienteRq,{headers: this.headers});
  }
  
  /* @DeleteMapping("delete/{id}")
  private ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id){
      this.clienteService.deleteById(id);
      return ResponseEntity.ok(!(this.clienteService.getById(id) != null));
  } */

  public deleteById(id: number): Observable<any>{ 
    return this.http.delete(`${this.API_REST}/cliente/delete/${id}`);
  }
  
  /* @PostMapping("save/")
  private ResponseEntity<Cliente> saveTecnico(@RequestBody Cliente clienteRq){
      return ResponseEntity.ok(this.clienteService.save(clienteRq));
  } */
  
  public save(clienteRq: Cliente): Observable<any>{ 
    return this.http.post(`${this.API_REST}/cliente/save/`, clienteRq, {headers: this.headers});
  }

  public reactivar(id):Observable<Cliente[]>{
    console.log('idService: ', id);
    return this.http.get<Cliente[]>(`${this.API_REST}/cliente/activar/${id}`);
  }


}
