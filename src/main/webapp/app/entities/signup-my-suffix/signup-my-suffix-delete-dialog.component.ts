import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SignupMySuffix } from './signup-my-suffix.model';
import { SignupMySuffixPopupService } from './signup-my-suffix-popup.service';
import { SignupMySuffixService } from './signup-my-suffix.service';

@Component({
    selector: 'jhi-signup-my-suffix-delete-dialog',
    templateUrl: './signup-my-suffix-delete-dialog.component.html'
})
export class SignupMySuffixDeleteDialogComponent {

    signup: SignupMySuffix;

    constructor(
        private signupService: SignupMySuffixService,
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
    selector: 'jhi-signup-my-suffix-delete-popup',
    template: ''
})
export class SignupMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private signupPopupService: SignupMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.signupPopupService
                .open(SignupMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
