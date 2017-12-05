import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SignupMySuffix } from './signup-my-suffix.model';
import { SignupMySuffixPopupService } from './signup-my-suffix-popup.service';
import { SignupMySuffixService } from './signup-my-suffix.service';

@Component({
    selector: 'jhi-signup-my-suffix-dialog',
    templateUrl: './signup-my-suffix-dialog.component.html'
})
export class SignupMySuffixDialogComponent implements OnInit {

    signup: SignupMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private signupService: SignupMySuffixService,
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
        if (this.signup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.signupService.update(this.signup));
        } else {
            this.subscribeToSaveResponse(
                this.signupService.create(this.signup));
        }
    }

    private subscribeToSaveResponse(result: Observable<SignupMySuffix>) {
        result.subscribe((res: SignupMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SignupMySuffix) {
        this.eventManager.broadcast({ name: 'signupListModification', content: 'OK'});
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
    selector: 'jhi-signup-my-suffix-popup',
    template: ''
})
export class SignupMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private signupPopupService: SignupMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.signupPopupService
                    .open(SignupMySuffixDialogComponent as Component, params['id']);
            } else {
                this.signupPopupService
                    .open(SignupMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
