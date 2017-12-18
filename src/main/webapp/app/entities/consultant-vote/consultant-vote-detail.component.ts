import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ConsultantVote } from './consultant-vote.model';
import { ConsultantVoteService } from './consultant-vote.service';

@Component({
    selector: 'jhi-consultant-vote-detail',
    templateUrl: './consultant-vote-detail.component.html'
})
export class ConsultantVoteDetailComponent implements OnInit, OnDestroy {

    consultantVote: ConsultantVote;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private consultantVoteService: ConsultantVoteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConsultantVotes();
    }

    load(id) {
        this.consultantVoteService.find(id).subscribe((consultantVote) => {
            this.consultantVote = consultantVote;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConsultantVotes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'consultantVoteListModification',
            (response) => this.load(this.consultantVote.id)
        );
    }
}
