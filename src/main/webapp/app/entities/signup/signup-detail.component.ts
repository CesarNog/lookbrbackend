import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Signup } from './signup.model';
import { SignupService } from './signup.service';

@Component({
    selector: 'jhi-signup-detail',
    templateUrl: './signup-detail.component.html'
})
export class SignupDetailComponent implements OnInit, OnDestroy {

    signup: Signup;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private signupService: SignupService,
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
