import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Consultant } from './consultant.model';
import { ConsultantService } from './consultant.service';

@Component({
    selector: 'jhi-consultant-detail',
    templateUrl: './consultant-detail.component.html'
})
export class ConsultantDetailComponent implements OnInit, OnDestroy {

    consultant: Consultant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private consultantService: ConsultantService,
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
        this.consultantService.find(id).subscribe((consultant) => {
            this.consultant = consultant;
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
            'consultantListModification',
            (response) => this.load(this.consultant.id)
        );
    }
}
