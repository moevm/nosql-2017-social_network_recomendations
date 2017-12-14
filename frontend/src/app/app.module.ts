import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {GroupListComponent} from './components/group-list/grouplist.component';
import {HttpClientModule} from '@angular/common/http';
import {GrouplistService} from './components/group-list/grouplist.service';
import {GraphComponent} from './visuals/graph/graph.component';
import {SHARED_VISUALS} from './visuals/shared';
import {D3_DIRECTIVES} from './d3/directives';
import {D3Service} from './d3';
import {AppRoutingModule} from './app-routing.module';
import {GroupItemComponent} from './components/group-item/group-item.component';
import {GraphViewComponent} from './components/graph-view/graph-view.component';
import {WelcomePageComponent} from './components/welcome-page/welcome-page.component';
import {FriendListComponent} from './components/friend-list/friend-list.component';
import {FriendItemComponent} from './components/friend-item/friend-item.component';
import {FriendsStore} from './stores/FriendsStore';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {AuthService} from './services/authService';
import {PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CustomMaterialModule} from './material/CustomMaterialModule';
import {TagInputComponent} from './components/tag-input/tag-input.component';
import {SearchFriendsPipe} from './pipes/search-friends.pipe';
import {Ng4LoadingSpinnerModule} from "ng4-loading-spinner";
import {NgxCarouselModule} from "ngx-carousel";
import { UploadJsonComponent } from './components/upload-json/upload-json.component';
import {FormsModule} from "@angular/forms";
import {FileSelectDirective} from "ng2-file-upload";


const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

@NgModule({
  declarations: [
    AppComponent,
    GroupListComponent,
    GraphComponent,
    ...SHARED_VISUALS,
    ...D3_DIRECTIVES,
    GroupItemComponent,
    GraphViewComponent,
    WelcomePageComponent,
    FriendListComponent,
    FriendItemComponent,
    HeaderComponent,
    FooterComponent,
    TagInputComponent,
    SearchFriendsPipe,
    UploadJsonComponent,
    FileSelectDirective
  ],
  imports: [
    FormsModule,
    NgxCarouselModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    PerfectScrollbarModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
    Ng4LoadingSpinnerModule.forRoot(),
  ],
  providers: [
    GrouplistService,
    D3Service,
    FriendsStore,
    AuthService,
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
