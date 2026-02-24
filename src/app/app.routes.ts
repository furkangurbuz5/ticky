import { Routes } from '@angular/router';
import {ScrumBoardComponent} from './board/board';
import {Login} from './login/login';

export const routes: Routes = [
  {path: "board", component: ScrumBoardComponent},
  {path: "login", component: Login},
];
