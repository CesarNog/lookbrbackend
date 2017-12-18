import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Temperature } from './temperature.model';
import { TemperatureService } from './temperature.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-temperature',
    templateUrl: './temperature.component.html'
})
export class TemperatureComponent implements OnInit, OnDestroy {
temperatures: Temperature[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private temperatureService: TemperatureService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.temperatureService.query().subscribe(
            (res: ResponseWrapper) => {
                this.temperatures = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTemperatures();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Temperature) {
        return item.id;
    }
    registerChangeInTemperatures() {
        this.eventSubscriber = this.eventManager.subscribe('temperatureListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
