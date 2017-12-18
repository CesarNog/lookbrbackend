import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Closet } from './closet.model';
import { ClosetService } from './closet.service';

@Component({
    selector: 'jhi-closet-detail',
    templateUrl: './closet-detail.component.html'
})
export class ClosetDetailComponent implements OnInit, OnDestroy {

    closet: Closet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private closetService: ClosetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClosets();
    }

    load(id) {
        this.closetService.find(id).subscribe((closet) => {
            this.closet = closet;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClosets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'closetListModification',
            (response) => this.load(this.closet.id)
        );
    }
}
