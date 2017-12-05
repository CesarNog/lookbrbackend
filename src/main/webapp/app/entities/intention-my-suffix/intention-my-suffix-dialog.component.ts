import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IntentionMySuffix } from './intention-my-suffix.model';
import { IntentionMySuffixPopupService } from './intention-my-suffix-popup.service';
import { IntentionMySuffixService } from './intention-my-suffix.service';

@Component({
    selector: 'jhi-intention-my-suffix-dialog',
    templateUrl: './intention-my-suffix-dialog.component.html'
})
export class IntentionMySuffixDialogComponent implements OnInit {

    intention: IntentionMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private intentionService: IntentionMySuffixService,
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
        if (this.intention.id !== undefined) {
            this.subscribeToSaveResponse(
                this.intentionService.update(this.intention));
        } else {
            this.subscribeToSaveResponse(
                this.intentionService.create(this.intention));
        }
    }

    private subscribeToSaveResponse(result: Observable<IntentionMySuffix>) {
        result.subscribe((res: IntentionMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: IntentionMySuffix) {
        this.eventManager.broadcast({ name: 'intentionListModification', content: 'OK'});
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
    selector: 'jhi-intention-my-suffix-popup',
    template: ''
})
export class IntentionMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private intentionPopupService: IntentionMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.intentionPopupService
                    .open(IntentionMySuffixDialogComponent as Component, params['id']);
            } else {
                this.intentionPopupService
                    .open(IntentionMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
