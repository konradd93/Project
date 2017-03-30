import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login/login.service';
import { Router } from '@angular/router';

@Component({
    selector: 'side-navbar',
    templateUrl: 'app/side_navbar/side-navbar.component.html',
    styleUrls: ['css/sidebar.css'],
    providers: [LoginService]
})
export class SideNavbarComponent implements OnInit {
  constructor(private loginService: LoginService, private router: Router){

  }

  ngOnInit(){
    this.loginService.isLogged();
  }

  //just checks a token, refreshing variables
  checkToken(){
    this.loginService.isLogged();
  }
    
}
