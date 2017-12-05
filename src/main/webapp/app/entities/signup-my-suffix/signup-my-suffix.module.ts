import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    SignupMySuffixService,
    SignupMySuffixPopupService,
    SignupMySuffixComponent,
    SignupMySuffixDetailComponent,
    SignupMySuffixDialogComponent,
    SignupMySuffixPopupComponent,
    SignupMySuffixDeletePopupComponent,
    SignupMySuffixDeleteDialogComponent,
    signupRoute,
    signupPopupRoute,
} from './';

const ENTITY_STATES = [
    ...signupRoute,
    ...signupPopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SignupMySuffixComponent,
        SignupMySuffixDetailComponent,
        SignupMySuffixDialogComponent,
        SignupMySuffixDeleteDialogComponent,
        SignupMySuffixPopupComponent,
        SignupMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        SignupMySuffixComponent,
        SignupMySuffixDialogComponent,
        SignupMySuffixPopupComponent,
        SignupMySuffixDeleteDialogComponent,
        SignupMySuffixDeletePopupComponent,
    ],
    providers: [
        SignupMySuffixService,
        SignupMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendSignupMySuffixModule {}
