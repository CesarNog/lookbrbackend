import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ConsultantsMySuffix } from './consultants-my-suffix.model';
import { ConsultantsMySuffixService } from './consultants-my-suffix.service';

@Component({
    selector: 'jhi-consultants-my-suffix-detail',
    templateUrl: './consultants-my-suffix-detail.component.html'
})
export class ConsultantsMySuffixDetailComponent implements OnInit, OnDestroy {

    consultants: ConsultantsMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private consultantsService: ConsultantsMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConsultants();
    }

    load(id) {
        this.consultantsService.find(id).subscribe((consultants) => {
            this.consultants = consultants;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConsultants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'consultantsListModification',
            (response) => this.load(this.consultants.id)
        );
    }
}
