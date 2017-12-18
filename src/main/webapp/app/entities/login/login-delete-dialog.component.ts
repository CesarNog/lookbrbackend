import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Login } from './login.model';
import { LoginPopupService } from './login-popup.service';
import { LoginService } from './login.service';

@Component({
    selector: 'jhi-login-delete-dialog',
    templateUrl: './login-delete-dialog.component.html'
})
export class LoginDeleteDialogComponent {

    login: Login;

    constructor(
        private loginService: LoginService,
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
    selector: 'jhi-login-delete-popup',
    template: ''
})
export class LoginDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private loginPopupService: LoginPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.loginPopupService
                .open(LoginDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
