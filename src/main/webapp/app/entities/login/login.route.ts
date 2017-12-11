import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LoginComponent } from './login.component';
import { LoginDetailComponent } from './login-detail.component';
import { LoginPopupComponent } from './login-dialog.component';
import { LoginDeletePopupComponent } from './login-delete-dialog.component';

export const loginRoute: Routes = [
    {
        path: 'login',
        component: LoginComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.login.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'login/:id',
        component: LoginDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.login.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const loginPopupRoute: Routes = [
    {
        path: 'login-new',
        component: LoginPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.login.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'login/:id/edit',
        component: LoginPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.login.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'login/:id/delete',
        component: LoginDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.login.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
