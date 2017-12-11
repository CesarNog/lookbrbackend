import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DayTimeComponent } from './day-time.component';
import { DayTimeDetailComponent } from './day-time-detail.component';
import { DayTimePopupComponent } from './day-time-dialog.component';
import { DayTimeDeletePopupComponent } from './day-time-delete-dialog.component';

export const dayTimeRoute: Routes = [
    {
        path: 'day-time',
        component: DayTimeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.dayTime.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'day-time/:id',
        component: DayTimeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.dayTime.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dayTimePopupRoute: Routes = [
    {
        path: 'day-time-new',
        component: DayTimePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.dayTime.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'day-time/:id/edit',
        component: DayTimePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.dayTime.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'day-time/:id/delete',
        component: DayTimeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.dayTime.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
