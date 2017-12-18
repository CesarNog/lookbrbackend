import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { LookbrbackendSharedModule, UserRouteAccessService } from './shared';
import { LookbrbackendAppRoutingModule} from './app-routing.module';
import { LookbrbackendHomeModule } from './home/home.module';
import { LookbrbackendAdminModule } from './admin/admin.module';
import { LookbrbackendAccountModule } from './account/account.module';
import { LookbrbackendEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LookbrbackendAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        LookbrbackendSharedModule,
        LookbrbackendHomeModule,
        LookbrbackendAdminModule,
        LookbrbackendAccountModule,
        LookbrbackendEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class LookbrbackendAppModule {}
