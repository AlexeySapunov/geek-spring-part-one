import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProductListComponent} from "./product-list/product-list.component";
import {ProductFormComponent} from "./product-form/product-form.component";
import {UserFormComponent} from "./user-form/user-form.component.ts ";
import {UserListComponent} from "./user-list/user-list.component";

const routes: Routes = [
  {path: "", pathMatch: "full", redirectTo: "product"},
  {path: "product", component: ProductListComponent},
  {path: "product/:id", component: ProductFormComponent},
  {path:"user", component: UserListComponent},
  {path:"user/:id", component: UserFormComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
