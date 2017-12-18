import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Signup } from './signup.model';
import { SignupService } from './signup.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-signup',
    templateUrl: './signup.component.html'
})
export class SignupComponent implements OnInit, OnDestroy {
signups: Signup[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private signupService: SignupService,
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

    trackId(index: number, item: Signup) {
        return item.id;
    }
    registerChangeInSignups() {
        this.eventSubscriber = this.eventManager.subscribe('signupListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
