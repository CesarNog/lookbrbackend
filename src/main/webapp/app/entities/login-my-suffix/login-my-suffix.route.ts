import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LoginMySuffixComponent } from './login-my-suffix.component';
import { LoginMySuffixDetailComponent } from './login-my-suffix-detail.component';
import { LoginMySuffixPopupComponent } from './login-my-suffix-dialog.component';
import { LoginMySuffixDeletePopupComponent } from './login-my-suffix-delete-dialog.component';

export const loginRoute: Routes = [
    {
        path: 'login-my-suffix',
        component: LoginMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.login.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'login-my-suffix/:id',
        component: LoginMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.login.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const loginPopupRoute: Routes = [
    {
        path: 'login-my-suffix-new',
        component: LoginMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.login.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'login-my-suffix/:id/edit',
        component: LoginMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.login.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'login-my-suffix/:id/delete',
        component: LoginMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.login.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
