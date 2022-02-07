import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service";
import {User} from "../model/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.less']
})

export class UserListComponent implements OnInit {

  users: User[] = [];

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.userService.findAll(1)
      .subscribe(res => {
          this.users = res.content;
        },
        err =>{
          console.error(err);
        })
  }

  public delete(id: number | null) {
    if(id != null){
      this.userService.deleteById(id).subscribe(res =>{
          console.info(res);
          this.router.navigateByUrl('/product');
        },
        err =>{
          console.error(err)
        })
    }
  }
}
