import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LookComponent } from './look.component';
import { LookDetailComponent } from './look-detail.component';
import { LookPopupComponent } from './look-dialog.component';
import { LookDeletePopupComponent } from './look-delete-dialog.component';

export const lookRoute: Routes = [
    {
        path: 'look',
        component: LookComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.look.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'look/:id',
        component: LookDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.look.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lookPopupRoute: Routes = [
    {
        path: 'look-new',
        component: LookPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.look.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'look/:id/edit',
        component: LookPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.look.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'look/:id/delete',
        component: LookDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.look.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
