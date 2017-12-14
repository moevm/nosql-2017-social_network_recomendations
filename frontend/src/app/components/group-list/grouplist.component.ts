import {Component, OnInit} from '@angular/core';
import {GrouplistService} from './grouplist.service';
import {NgxCarousel} from 'ngx-carousel';
import {Ng4LoadingSpinnerService} from "ng4-loading-spinner";

@Component({
  selector: 'app-group-list',
  templateUrl: './grouplist.component.html',
  styleUrls: ['./grouplist.component.scss']
})
export class GroupListComponent implements OnInit {


  public carouselTileOneItems: Array<any> = [];
  public carouselTileOne: NgxCarousel;

  public carouselTileTwoItems: Array<any> = [];
  public carouselTileTwo: NgxCarousel;

  timer;

  constructor(private mainpageService: GrouplistService, private spinnerService: Ng4LoadingSpinnerService) {
  }

  getRecommendations(): void {
    if (this.spinnerService.spinnerSubject.getValue()) {
    } else {
      clearInterval(this.timer);
      this.spinnerService.show();
      this.mainpageService.getRecommendations(false, 20).subscribe(items => {
        items.forEach(item => this.carouselTileOneItems.push(item));
      });
      this.mainpageService.getRecommendations(true, 20).subscribe(items => {
        items.forEach(item => this.carouselTileTwoItems.push(item));
        this.spinnerService.hide();
      });
    }

  }

  ngOnInit() {
    this.timer = setInterval(() => this.getRecommendations(), 3000);
    this.carouselTileOne = {
      grid: {xs: 2, sm: 3, md: 4, lg: 4, all: 0},
      speed: 600,
      interval: 3000,
      point: {
        visible: true,
        pointStyles: `
          .ngxcarouselPoint {
            list-style-type: none;
            text-align: center;
            padding: 12px;
            margin: 0;
            white-space: nowrap;
            overflow: auto;
            box-sizing: border-box;
          }
          .ngxcarouselPoint li {
            display: inline-block;
            border-radius: 50%;
            background: #6b6b6b;
            padding: 5px;
            margin: 0 3px;
            transition: .4s;
          }
          .ngxcarouselPoint li.active {
              border: 2px solid rgba(0, 0, 0, 0.55);
              transform: scale(1.2);
              background: transparent;
            }
        `
      },
      load: 2,
      touch: true
    };
    this.carouselTileTwo = {...this.carouselTileOne};
  }

}
