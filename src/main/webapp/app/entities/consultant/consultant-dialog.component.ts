import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Consultant } from './consultant.model';
import { ConsultantPopupService } from './consultant-popup.service';
import { ConsultantService } from './consultant.service';
import { Look, LookService } from '../look';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-consultant-dialog',
    templateUrl: './consultant-dialog.component.html'
})
export class ConsultantDialogComponent implements OnInit {

    consultant: Consultant;
    isSaving: boolean;

    looks: Look[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private consultantService: ConsultantService,
        private lookService: LookService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.lookService.query()
            .subscribe((res: ResponseWrapper) => { this.looks = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.consultant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.consultantService.update(this.consultant));
        } else {
            this.subscribeToSaveResponse(
                this.consultantService.create(this.consultant));
        }
    }

    private subscribeToSaveResponse(result: Observable<Consultant>) {
        result.subscribe((res: Consultant) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Consultant) {
        this.eventManager.broadcast({ name: 'consultantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLookById(index: number, item: Look) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-consultant-popup',
    template: ''
})
export class ConsultantPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultantPopupService: ConsultantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.consultantPopupService
                    .open(ConsultantDialogComponent as Component, params['id']);
            } else {
                this.consultantPopupService
                    .open(ConsultantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
