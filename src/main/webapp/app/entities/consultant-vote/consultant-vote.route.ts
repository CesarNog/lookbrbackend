import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ConsultantVoteComponent } from './consultant-vote.component';
import { ConsultantVoteDetailComponent } from './consultant-vote-detail.component';
import { ConsultantVotePopupComponent } from './consultant-vote-dialog.component';
import { ConsultantVoteDeletePopupComponent } from './consultant-vote-delete-dialog.component';

export const consultantVoteRoute: Routes = [
    {
        path: 'consultant-vote',
        component: ConsultantVoteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.consultantVote.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'consultant-vote/:id',
        component: ConsultantVoteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.consultantVote.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const consultantVotePopupRoute: Routes = [
    {
        path: 'consultant-vote-new',
        component: ConsultantVotePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.consultantVote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultant-vote/:id/edit',
        component: ConsultantVotePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.consultantVote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'consultant-vote/:id/delete',
        component: ConsultantVoteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lookbrbackendApp.consultantVote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
