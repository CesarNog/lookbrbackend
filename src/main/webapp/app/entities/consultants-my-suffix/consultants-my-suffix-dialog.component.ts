import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ConsultantsMySuffix } from './consultants-my-suffix.model';
import { ConsultantsMySuffixPopupService } from './consultants-my-suffix-popup.service';
import { ConsultantsMySuffixService } from './consultants-my-suffix.service';

@Component({
    selector: 'jhi-consultants-my-suffix-dialog',
    templateUrl: './consultants-my-suffix-dialog.component.html'
})
export class ConsultantsMySuffixDialogComponent implements OnInit {

    consultants: ConsultantsMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private consultantsService: ConsultantsMySuffixService,
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
        if (this.consultants.id !== undefined) {
            this.subscribeToSaveResponse(
                this.consultantsService.update(this.consultants));
        } else {
            this.subscribeToSaveResponse(
                this.consultantsService.create(this.consultants));
        }
    }

    private subscribeToSaveResponse(result: Observable<ConsultantsMySuffix>) {
        result.subscribe((res: ConsultantsMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ConsultantsMySuffix) {
        this.eventManager.broadcast({ name: 'consultantsListModification', content: 'OK'});
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
    selector: 'jhi-consultants-my-suffix-popup',
    template: ''
})
export class ConsultantsMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultantsPopupService: ConsultantsMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.consultantsPopupService
                    .open(ConsultantsMySuffixDialogComponent as Component, params['id']);
            } else {
                this.consultantsPopupService
                    .open(ConsultantsMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
