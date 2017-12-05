import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LoginMySuffix } from './login-my-suffix.model';
import { LoginMySuffixService } from './login-my-suffix.service';

@Component({
    selector: 'jhi-login-my-suffix-detail',
    templateUrl: './login-my-suffix-detail.component.html'
})
export class LoginMySuffixDetailComponent implements OnInit, OnDestroy {

    login: LoginMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLogins();
    }

    load(id) {
        this.loginService.find(id).subscribe((login) => {
            this.login = login;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLogins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'loginListModification',
            (response) => this.load(this.login.id)
        );
    }
}
