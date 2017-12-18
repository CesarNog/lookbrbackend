import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OccasionComponent } from './occasion.component';
import { OccasionDetailComponent } from './occasion-detail.component';
import { OccasionPopupComponent } from './occasion-dialog.component';
import { OccasionDeletePopupComponent } from './occasion-delete-dialog.component';

export const occasionRoute: Routes = [
    {
        path: 'occasion',
        component: OccasionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.occasion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'occasion/:id',
        component: OccasionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.occasion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const occasionPopupRoute: Routes = [
    {
        path: 'occasion-new',
        component: OccasionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.occasion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'occasion/:id/edit',
        component: OccasionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.occasion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'occasion/:id/delete',
        component: OccasionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.occasion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
