import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LookbrbackendSharedModule } from '../../shared';
import {
    TemperatureService,
    TemperaturePopupService,
    TemperatureComponent,
    TemperatureDetailComponent,
    TemperatureDialogComponent,
    TemperaturePopupComponent,
    TemperatureDeletePopupComponent,
    TemperatureDeleteDialogComponent,
    temperatureRoute,
    temperaturePopupRoute,
} from './';

const ENTITY_STATES = [
    ...temperatureRoute,
    ...temperaturePopupRoute,
];

@NgModule({
    imports: [
        LookbrbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TemperatureComponent,
        TemperatureDetailComponent,
        TemperatureDialogComponent,
        TemperatureDeleteDialogComponent,
        TemperaturePopupComponent,
        TemperatureDeletePopupComponent,
    ],
    entryComponents: [
        TemperatureComponent,
        TemperatureDialogComponent,
        TemperaturePopupComponent,
        TemperatureDeleteDialogComponent,
        TemperatureDeletePopupComponent,
    ],
    providers: [
        TemperatureService,
        TemperaturePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LookbrbackendTemperatureModule {}
