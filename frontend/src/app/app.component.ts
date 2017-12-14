import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Ng4LoadingSpinnerService} from 'ng4-loading-spinner';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  INIT_URL = 'http://localhost:8080/api/retrieveDataFromVk';


  constructor(private http: HttpClient, private spinnerService: Ng4LoadingSpinnerService) {

  }

  ngOnInit(): void {
    this.spinnerService.show();
    this.http.get(this.INIT_URL)
      .subscribe(
        data => this.spinnerService.hide(),
        error => this.spinnerService.hide()
      )
    ;
  }


}
