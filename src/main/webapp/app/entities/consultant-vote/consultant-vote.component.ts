import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ConsultantVote } from './consultant-vote.model';
import { ConsultantVoteService } from './consultant-vote.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-consultant-vote',
    templateUrl: './consultant-vote.component.html'
})
export class ConsultantVoteComponent implements OnInit, OnDestroy {
consultantVotes: ConsultantVote[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private consultantVoteService: ConsultantVoteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.consultantVoteService.query().subscribe(
            (res: ResponseWrapper) => {
                this.consultantVotes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInConsultantVotes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ConsultantVote) {
        return item.id;
    }
    registerChangeInConsultantVotes() {
        this.eventSubscriber = this.eventManager.subscribe('consultantVoteListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
