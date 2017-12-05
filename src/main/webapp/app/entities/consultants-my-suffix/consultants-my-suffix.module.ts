import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    ConsultantsMySuffixService,
    ConsultantsMySuffixPopupService,
    ConsultantsMySuffixComponent,
    ConsultantsMySuffixDetailComponent,
    ConsultantsMySuffixDialogComponent,
    ConsultantsMySuffixPopupComponent,
    ConsultantsMySuffixDeletePopupComponent,
    ConsultantsMySuffixDeleteDialogComponent,
    consultantsRoute,
    consultantsPopupRoute,
    ConsultantsMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...consultantsRoute,
    ...consultantsPopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ConsultantsMySuffixComponent,
        ConsultantsMySuffixDetailComponent,
        ConsultantsMySuffixDialogComponent,
        ConsultantsMySuffixDeleteDialogComponent,
        ConsultantsMySuffixPopupComponent,
        ConsultantsMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ConsultantsMySuffixComponent,
        ConsultantsMySuffixDialogComponent,
        ConsultantsMySuffixPopupComponent,
        ConsultantsMySuffixDeleteDialogComponent,
        ConsultantsMySuffixDeletePopupComponent,
    ],
    providers: [
        ConsultantsMySuffixService,
        ConsultantsMySuffixPopupService,
        ConsultantsMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendConsultantsMySuffixModule {}
