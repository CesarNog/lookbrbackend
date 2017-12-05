import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LoginMySuffix } from './login-my-suffix.model';
import { LoginMySuffixPopupService } from './login-my-suffix-popup.service';
import { LoginMySuffixService } from './login-my-suffix.service';

@Component({
    selector: 'jhi-login-my-suffix-dialog',
    templateUrl: './login-my-suffix-dialog.component.html'
})
export class LoginMySuffixDialogComponent implements OnInit {

    login: LoginMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private loginService: LoginMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.login.id !== undefined) {
            this.subscribeToSaveResponse(
                this.loginService.update(this.login));
        } else {
            this.subscribeToSaveResponse(
                this.loginService.create(this.login));
        }
    }

    private subscribeToSaveResponse(result: Observable<LoginMySuffix>) {
        result.subscribe((res: LoginMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LoginMySuffix) {
        this.eventManager.broadcast({ name: 'loginListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-login-my-suffix-popup',
    template: ''
})
export class LoginMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private loginPopupService: LoginMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.loginPopupService
                    .open(LoginMySuffixDialogComponent as Component, params['id']);
            } else {
                this.loginPopupService
                    .open(LoginMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
