import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  orderDetails :any;
  orderId!:number;
  constructor(private http:HttpClient, private router: Router, private route:ActivatedRoute) {
    this.orderId=route.snapshot.params['orderId'];
  }
//http://localhost:9999/order-service/fullOrder/
  ///api/orders/fullOrder/
  ngOnInit(): void {
    this.http.get("/api/orders/fullOrder/"+this.orderId)
      .subscribe({
        next : (data)=>{
          this.orderDetails=data;
        },
        error : (err)=>{}
      });
  }

  getOrderDetails(o: any) {
    this.router.navigateByUrl("/order-details/"+o.id);
  }
}
