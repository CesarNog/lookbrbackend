import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Login } from './login.model';
import { LoginService } from './login.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-login',
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit, OnDestroy {
logins: Login[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private loginService: LoginService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.loginService.query().subscribe(
            (res: ResponseWrapper) => {
                this.logins = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLogins();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Login) {
        return item.id;
    }
    registerChangeInLogins() {
        this.eventSubscriber = this.eventManager.subscribe('loginListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
