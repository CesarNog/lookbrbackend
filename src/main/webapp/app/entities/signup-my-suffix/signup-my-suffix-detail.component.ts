import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SignupMySuffix } from './signup-my-suffix.model';
import { SignupMySuffixService } from './signup-my-suffix.service';

@Component({
    selector: 'jhi-signup-my-suffix-detail',
    templateUrl: './signup-my-suffix-detail.component.html'
})
export class SignupMySuffixDetailComponent implements OnInit, OnDestroy {

    signup: SignupMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private signupService: SignupMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSignups();
    }

    load(id) {
        this.signupService.find(id).subscribe((signup) => {
            this.signup = signup;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSignups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'signupListModification',
            (response) => this.load(this.signup.id)
        );
    }
}
