import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LookMySuffixComponent } from './look-my-suffix.component';
import { LookMySuffixDetailComponent } from './look-my-suffix-detail.component';
import { LookMySuffixPopupComponent } from './look-my-suffix-dialog.component';
import { LookMySuffixDeletePopupComponent } from './look-my-suffix-delete-dialog.component';

export const lookRoute: Routes = [
    {
        path: 'look-my-suffix',
        component: LookMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.look.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'look-my-suffix/:id',
        component: LookMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.look.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lookPopupRoute: Routes = [
    {
        path: 'look-my-suffix-new',
        component: LookMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.look.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'look-my-suffix/:id/edit',
        component: LookMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.look.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'look-my-suffix/:id/delete',
        component: LookMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.look.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
