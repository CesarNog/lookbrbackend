import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { LoginMySuffix } from './login-my-suffix.model';
import { LoginMySuffixService } from './login-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-login-my-suffix',
    templateUrl: './login-my-suffix.component.html'
})
export class LoginMySuffixComponent implements OnInit, OnDestroy {
logins: LoginMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private loginService: LoginMySuffixService,
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

    trackId(index: number, item: LoginMySuffix) {
        return item.id;
    }
    registerChangeInLogins() {
        this.eventSubscriber = this.eventManager.subscribe('loginListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
