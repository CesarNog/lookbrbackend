import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Look } from './look.model';
import { LookPopupService } from './look-popup.service';
import { LookService } from './look.service';
import { Closet, ClosetService } from '../closet';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-look-dialog',
    templateUrl: './look-dialog.component.html'
})
export class LookDialogComponent implements OnInit {

    look: Look;
    isSaving: boolean;

    closets: Closet[];
    dayTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lookService: LookService,
        private closetService: ClosetService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.closetService.query()
            .subscribe((res: ResponseWrapper) => { this.closets = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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

    private subscribeToSaveResponse(result: Observable<Look>) {
        result.subscribe((res: Look) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Look) {
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

    trackClosetById(index: number, item: Closet) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-look-popup',
    template: ''
})
export class LookPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lookPopupService: LookPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lookPopupService
                    .open(LookDialogComponent as Component, params['id']);
            } else {
                this.lookPopupService
                    .open(LookDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
