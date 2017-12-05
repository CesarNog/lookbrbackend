import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { SignupMySuffix } from './signup-my-suffix.model';
import { SignupMySuffixService } from './signup-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-signup-my-suffix',
    templateUrl: './signup-my-suffix.component.html'
})
export class SignupMySuffixComponent implements OnInit, OnDestroy {
signups: SignupMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private signupService: SignupMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.signupService.query().subscribe(
            (res: ResponseWrapper) => {
                this.signups = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSignups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SignupMySuffix) {
        return item.id;
    }
    registerChangeInSignups() {
        this.eventSubscriber = this.eventManager.subscribe('signupListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
