import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Consultant } from './consultant.model';
import { ConsultantService } from './consultant.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-consultant',
    templateUrl: './consultant.component.html'
})
export class ConsultantComponent implements OnInit, OnDestroy {
consultants: Consultant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private consultantService: ConsultantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.consultantService.query().subscribe(
            (res: ResponseWrapper) => {
                this.consultants = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInConsultants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Consultant) {
        return item.id;
    }
    registerChangeInConsultants() {
        this.eventSubscriber = this.eventManager.subscribe('consultantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
