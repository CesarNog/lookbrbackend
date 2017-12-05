import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SignupMySuffixComponent } from './signup-my-suffix.component';
import { SignupMySuffixDetailComponent } from './signup-my-suffix-detail.component';
import { SignupMySuffixPopupComponent } from './signup-my-suffix-dialog.component';
import { SignupMySuffixDeletePopupComponent } from './signup-my-suffix-delete-dialog.component';

export const signupRoute: Routes = [
    {
        path: 'signup-my-suffix',
        component: SignupMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.signup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'signup-my-suffix/:id',
        component: SignupMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.signup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const signupPopupRoute: Routes = [
    {
        path: 'signup-my-suffix-new',
        component: SignupMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.signup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'signup-my-suffix/:id/edit',
        component: SignupMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.signup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'signup-my-suffix/:id/delete',
        component: SignupMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.signup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
