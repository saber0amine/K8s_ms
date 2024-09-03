import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  customers :any;
  constructor(private http:HttpClient, private router: Router) { }
// /api/customers?projection=fullCustomer with nginx
  // http://localhost:9999/customer-service/customers?projection=fullCustomer
  ngOnInit(): void {
    this.http.get("/api/customers?projection=fullCustomer")
      .subscribe({
        next : (data)=>{
          console.log('Received customer data:', data);
          this.customers=data;
        },
        error : (err)=>{
          console.error('Error fetching customers:', err);
        }
      });
  }
  getOrders(c: any) {
    this.router.navigateByUrl("/orders/"+c.id);
  }
}
