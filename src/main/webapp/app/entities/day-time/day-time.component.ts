import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { DayTime } from './day-time.model';
import { DayTimeService } from './day-time.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-day-time',
    templateUrl: './day-time.component.html'
})
export class DayTimeComponent implements OnInit, OnDestroy {
dayTimes: DayTime[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dayTimeService: DayTimeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.dayTimeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.dayTimes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDayTimes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DayTime) {
        return item.id;
    }
    registerChangeInDayTimes() {
        this.eventSubscriber = this.eventManager.subscribe('dayTimeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
