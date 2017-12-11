import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InspirationComponent } from './inspiration.component';
import { InspirationDetailComponent } from './inspiration-detail.component';
import { InspirationPopupComponent } from './inspiration-dialog.component';
import { InspirationDeletePopupComponent } from './inspiration-delete-dialog.component';

export const inspirationRoute: Routes = [
    {
        path: 'inspiration',
        component: InspirationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.inspiration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inspiration/:id',
        component: InspirationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.inspiration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inspirationPopupRoute: Routes = [
    {
        path: 'inspiration-new',
        component: InspirationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.inspiration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inspiration/:id/edit',
        component: InspirationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.inspiration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inspiration/:id/delete',
        component: InspirationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.inspiration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
