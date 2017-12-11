import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ConsultantVote } from './consultant-vote.model';
import { ConsultantVotePopupService } from './consultant-vote-popup.service';
import { ConsultantVoteService } from './consultant-vote.service';
import { Look, LookService } from '../look';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-consultant-vote-dialog',
    templateUrl: './consultant-vote-dialog.component.html'
})
export class ConsultantVoteDialogComponent implements OnInit {

    consultantVote: ConsultantVote;
    isSaving: boolean;

    looks: Look[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private consultantVoteService: ConsultantVoteService,
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
        if (this.consultantVote.id !== undefined) {
            this.subscribeToSaveResponse(
                this.consultantVoteService.update(this.consultantVote));
        } else {
            this.subscribeToSaveResponse(
                this.consultantVoteService.create(this.consultantVote));
        }
    }

    private subscribeToSaveResponse(result: Observable<ConsultantVote>) {
        result.subscribe((res: ConsultantVote) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ConsultantVote) {
        this.eventManager.broadcast({ name: 'consultantVoteListModification', content: 'OK'});
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
    selector: 'jhi-consultant-vote-popup',
    template: ''
})
export class ConsultantVotePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultantVotePopupService: ConsultantVotePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.consultantVotePopupService
                    .open(ConsultantVoteDialogComponent as Component, params['id']);
            } else {
                this.consultantVotePopupService
                    .open(ConsultantVoteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
