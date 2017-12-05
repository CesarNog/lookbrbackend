import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    LoginMySuffixService,
    LoginMySuffixPopupService,
    LoginMySuffixComponent,
    LoginMySuffixDetailComponent,
    LoginMySuffixDialogComponent,
    LoginMySuffixPopupComponent,
    LoginMySuffixDeletePopupComponent,
    LoginMySuffixDeleteDialogComponent,
    loginRoute,
    loginPopupRoute,
} from './';

const ENTITY_STATES = [
    ...loginRoute,
    ...loginPopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LoginMySuffixComponent,
        LoginMySuffixDetailComponent,
        LoginMySuffixDialogComponent,
        LoginMySuffixDeleteDialogComponent,
        LoginMySuffixPopupComponent,
        LoginMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        LoginMySuffixComponent,
        LoginMySuffixDialogComponent,
        LoginMySuffixPopupComponent,
        LoginMySuffixDeleteDialogComponent,
        LoginMySuffixDeletePopupComponent,
    ],
    providers: [
        LoginMySuffixService,
        LoginMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendLoginMySuffixModule {}
