import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    LookMySuffixService,
    LookMySuffixPopupService,
    LookMySuffixComponent,
    LookMySuffixDetailComponent,
    LookMySuffixDialogComponent,
    LookMySuffixPopupComponent,
    LookMySuffixDeletePopupComponent,
    LookMySuffixDeleteDialogComponent,
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
        LookMySuffixComponent,
        LookMySuffixDetailComponent,
        LookMySuffixDialogComponent,
        LookMySuffixDeleteDialogComponent,
        LookMySuffixPopupComponent,
        LookMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        LookMySuffixComponent,
        LookMySuffixDialogComponent,
        LookMySuffixPopupComponent,
        LookMySuffixDeleteDialogComponent,
        LookMySuffixDeletePopupComponent,
    ],
    providers: [
        LookMySuffixService,
        LookMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendLookMySuffixModule {}
