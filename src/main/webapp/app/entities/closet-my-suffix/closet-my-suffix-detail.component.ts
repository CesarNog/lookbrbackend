import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ClosetMySuffix } from './closet-my-suffix.model';
import { ClosetMySuffixService } from './closet-my-suffix.service';

@Component({
    selector: 'jhi-closet-my-suffix-detail',
    templateUrl: './closet-my-suffix-detail.component.html'
})
export class ClosetMySuffixDetailComponent implements OnInit, OnDestroy {

    closet: ClosetMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private closetService: ClosetMySuffixService,
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
