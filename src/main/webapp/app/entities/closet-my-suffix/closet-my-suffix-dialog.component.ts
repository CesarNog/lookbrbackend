import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ClosetMySuffix } from './closet-my-suffix.model';
import { ClosetMySuffixPopupService } from './closet-my-suffix-popup.service';
import { ClosetMySuffixService } from './closet-my-suffix.service';

@Component({
    selector: 'jhi-closet-my-suffix-dialog',
    templateUrl: './closet-my-suffix-dialog.component.html'
})
export class ClosetMySuffixDialogComponent implements OnInit {

    closet: ClosetMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private closetService: ClosetMySuffixService,
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
        if (this.closet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.closetService.update(this.closet));
        } else {
            this.subscribeToSaveResponse(
                this.closetService.create(this.closet));
        }
    }

    private subscribeToSaveResponse(result: Observable<ClosetMySuffix>) {
        result.subscribe((res: ClosetMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ClosetMySuffix) {
        this.eventManager.broadcast({ name: 'closetListModification', content: 'OK'});
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
    selector: 'jhi-closet-my-suffix-popup',
    template: ''
})
export class ClosetMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private closetPopupService: ClosetMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.closetPopupService
                    .open(ClosetMySuffixDialogComponent as Component, params['id']);
            } else {
                this.closetPopupService
                    .open(ClosetMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
