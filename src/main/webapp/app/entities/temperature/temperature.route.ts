import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TemperatureComponent } from './temperature.component';
import { TemperatureDetailComponent } from './temperature-detail.component';
import { TemperaturePopupComponent } from './temperature-dialog.component';
import { TemperatureDeletePopupComponent } from './temperature-delete-dialog.component';

export const temperatureRoute: Routes = [
    {
        path: 'temperature',
        component: TemperatureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.temperature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'temperature/:id',
        component: TemperatureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.temperature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const temperaturePopupRoute: Routes = [
    {
        path: 'temperature-new',
        component: TemperaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.temperature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'temperature/:id/edit',
        component: TemperaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.temperature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'temperature/:id/delete',
        component: TemperatureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.temperature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
