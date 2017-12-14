import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GroupListComponent} from './components/group-list/grouplist.component';
import {GraphViewComponent} from './components/graph-view/graph-view.component';
import {WelcomePageComponent} from './components/welcome-page/welcome-page.component';
import {FriendListComponent} from './components/friend-list/friend-list.component';
import {AuthGuard} from "./guards/AuthGuard";
import {UploadJsonComponent} from "./components/upload-json/upload-json.component";

const routes: Routes = [
  {path: 'groupList', component: GroupListComponent, pathMatch: 'full', canActivate: [AuthGuard]},
  {path: 'signin', component: WelcomePageComponent, pathMatch: 'full'},
  {path: 'graph', component: GraphViewComponent, pathMatch: 'full', canActivate: [AuthGuard]},
  {path: 'friendList', component: FriendListComponent, pathMatch: 'full', canActivate: [AuthGuard]},
  {path: 'uploadJsonView', component: UploadJsonComponent, pathMatch: 'full'},
  {path: '**', redirectTo: 'groupList', pathMatch: 'full'},
];

@NgModule({
  providers: [AuthGuard],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
