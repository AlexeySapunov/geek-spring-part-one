import {Component, OnInit} from '@angular/core';
import {UserService} from "../services/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../model/user";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.less']
})
export class UserFormComponent implements OnInit {

  user = new User(null, "", "");

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      if (param.id == 'new') {
        this.user = new User(-1, "", "");
      } else {
        this.userService.findById(param.id)
          .subscribe(res => {
              this.user = res;
            },
            err => {
              console.error(err);
            })
      }
    })
  }

  save() {
    this.userService.save(this.user)
      .subscribe(res => {
          console.info(res);
          this.router.navigateByUrl('/user');
        },
        err => {
          console.error(err);
        })
  }
}
