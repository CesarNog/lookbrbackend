import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LookMySuffix } from './look-my-suffix.model';
import { LookMySuffixPopupService } from './look-my-suffix-popup.service';
import { LookMySuffixService } from './look-my-suffix.service';

@Component({
    selector: 'jhi-look-my-suffix-dialog',
    templateUrl: './look-my-suffix-dialog.component.html'
})
export class LookMySuffixDialogComponent implements OnInit {

    look: LookMySuffix;
    isSaving: boolean;
    dayTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lookService: LookMySuffixService,
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
        if (this.look.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lookService.update(this.look));
        } else {
            this.subscribeToSaveResponse(
                this.lookService.create(this.look));
        }
    }

    private subscribeToSaveResponse(result: Observable<LookMySuffix>) {
        result.subscribe((res: LookMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LookMySuffix) {
        this.eventManager.broadcast({ name: 'lookListModification', content: 'OK'});
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
    selector: 'jhi-look-my-suffix-popup',
    template: ''
})
export class LookMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lookPopupService: LookMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lookPopupService
                    .open(LookMySuffixDialogComponent as Component, params['id']);
            } else {
                this.lookPopupService
                    .open(LookMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
