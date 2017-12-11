import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    LookService,
    LookPopupService,
    LookComponent,
    LookDetailComponent,
    LookDialogComponent,
    LookPopupComponent,
    LookDeletePopupComponent,
    LookDeleteDialogComponent,
    lookRoute,
    lookPopupRoute,
} from './';

const ENTITY_STATES = [
    ...lookRoute,
    ...lookPopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LookComponent,
        LookDetailComponent,
        LookDialogComponent,
        LookDeleteDialogComponent,
        LookPopupComponent,
        LookDeletePopupComponent,
    ],
    entryComponents: [
        LookComponent,
        LookDialogComponent,
        LookPopupComponent,
        LookDeleteDialogComponent,
        LookDeletePopupComponent,
    ],
    providers: [
        LookService,
        LookPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendLookModule {}
