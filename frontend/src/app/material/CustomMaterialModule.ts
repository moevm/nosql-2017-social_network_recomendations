import {NgModule} from '@angular/core';
import {MatCardModule, MatCheckboxModule, MatSlideToggleModule} from '@angular/material';

@NgModule({
  imports: [MatSlideToggleModule, MatCheckboxModule, MatCardModule],
  exports: [MatSlideToggleModule, MatCheckboxModule, MatCardModule],
})
export class CustomMaterialModule {
}
