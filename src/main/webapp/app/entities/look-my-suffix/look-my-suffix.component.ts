import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { LookMySuffix } from './look-my-suffix.model';
import { LookMySuffixService } from './look-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-look-my-suffix',
    templateUrl: './look-my-suffix.component.html'
})
export class LookMySuffixComponent implements OnInit, OnDestroy {
looks: LookMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private lookService: LookMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.lookService.query().subscribe(
            (res: ResponseWrapper) => {
                this.looks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLooks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LookMySuffix) {
        return item.id;
    }
    registerChangeInLooks() {
        this.eventSubscriber = this.eventManager.subscribe('lookListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
