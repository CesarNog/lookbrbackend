import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Signup } from './signup.model';
import { SignupPopupService } from './signup-popup.service';
import { SignupService } from './signup.service';

@Component({
    selector: 'jhi-signup-delete-dialog',
    templateUrl: './signup-delete-dialog.component.html'
})
export class SignupDeleteDialogComponent {

    signup: Signup;

    constructor(
        private signupService: SignupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.signupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'signupListModification',
                content: 'Deleted an signup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-signup-delete-popup',
    template: ''
})
export class SignupDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private signupPopupService: SignupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.signupPopupService
                .open(SignupDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
