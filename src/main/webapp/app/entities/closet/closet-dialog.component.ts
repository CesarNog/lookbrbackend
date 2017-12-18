import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Closet } from './closet.model';
import { ClosetPopupService } from './closet-popup.service';
import { ClosetService } from './closet.service';

@Component({
    selector: 'jhi-closet-dialog',
    templateUrl: './closet-dialog.component.html'
})
export class ClosetDialogComponent implements OnInit {

    closet: Closet;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private closetService: ClosetService,
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

    private subscribeToSaveResponse(result: Observable<Closet>) {
        result.subscribe((res: Closet) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Closet) {
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
    selector: 'jhi-closet-popup',
    template: ''
})
export class ClosetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private closetPopupService: ClosetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.closetPopupService
                    .open(ClosetDialogComponent as Component, params['id']);
            } else {
                this.closetPopupService
                    .open(ClosetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
