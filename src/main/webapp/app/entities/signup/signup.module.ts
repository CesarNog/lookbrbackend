import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    SignupService,
    SignupPopupService,
    SignupComponent,
    SignupDetailComponent,
    SignupDialogComponent,
    SignupPopupComponent,
    SignupDeletePopupComponent,
    SignupDeleteDialogComponent,
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
        SignupComponent,
        SignupDetailComponent,
        SignupDialogComponent,
        SignupDeleteDialogComponent,
        SignupPopupComponent,
        SignupDeletePopupComponent,
    ],
    entryComponents: [
        SignupComponent,
        SignupDialogComponent,
        SignupPopupComponent,
        SignupDeleteDialogComponent,
        SignupDeletePopupComponent,
    ],
    providers: [
        SignupService,
        SignupPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendSignupModule {}
