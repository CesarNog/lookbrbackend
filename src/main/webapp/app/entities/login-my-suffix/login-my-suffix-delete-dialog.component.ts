import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginMySuffix } from './login-my-suffix.model';
import { LoginMySuffixPopupService } from './login-my-suffix-popup.service';
import { LoginMySuffixService } from './login-my-suffix.service';

@Component({
    selector: 'jhi-login-my-suffix-delete-dialog',
    templateUrl: './login-my-suffix-delete-dialog.component.html'
})
export class LoginMySuffixDeleteDialogComponent {

    login: LoginMySuffix;

    constructor(
        private loginService: LoginMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.loginService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'loginListModification',
                content: 'Deleted an login'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-login-my-suffix-delete-popup',
    template: ''
})
export class LoginMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private loginPopupService: LoginMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.loginPopupService
                .open(LoginMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
