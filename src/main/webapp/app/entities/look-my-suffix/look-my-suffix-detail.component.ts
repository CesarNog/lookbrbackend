import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LookMySuffix } from './look-my-suffix.model';
import { LookMySuffixService } from './look-my-suffix.service';

@Component({
    selector: 'jhi-look-my-suffix-detail',
    templateUrl: './look-my-suffix-detail.component.html'
})
export class LookMySuffixDetailComponent implements OnInit, OnDestroy {

    look: LookMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lookService: LookMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLooks();
    }

    load(id) {
        this.lookService.find(id).subscribe((look) => {
            this.look = look;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLooks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lookListModification',
            (response) => this.load(this.look.id)
        );
    }
}
