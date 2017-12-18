import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Signup } from './signup.model';
import { SignupPopupService } from './signup-popup.service';
import { SignupService } from './signup.service';

@Component({
    selector: 'jhi-signup-dialog',
    templateUrl: './signup-dialog.component.html'
})
export class SignupDialogComponent implements OnInit {

    signup: Signup;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private signupService: SignupService,
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

    private subscribeToSaveResponse(result: Observable<Signup>) {
        result.subscribe((res: Signup) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Signup) {
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
    selector: 'jhi-signup-popup',
    template: ''
})
export class SignupPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private signupPopupService: SignupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.signupPopupService
                    .open(SignupDialogComponent as Component, params['id']);
            } else {
                this.signupPopupService
                    .open(SignupDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
