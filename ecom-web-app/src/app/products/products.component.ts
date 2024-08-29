import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products : any;
  constructor(private http:HttpClient) { }
//http://localhost:9999/inventory-service/products?projection=fullProduct"
  ngOnInit(): void {
    this.http.get("http://geteway-service:99/products?projection=fullProduct")
      .subscribe({
      next : (data)=>{
        this.products=data;
      },
      error : (err)=>{}
    });
  }

}
