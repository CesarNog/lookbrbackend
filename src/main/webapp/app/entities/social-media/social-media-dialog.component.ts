import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SocialMedia } from './social-media.model';
import { SocialMediaPopupService } from './social-media-popup.service';
import { SocialMediaService } from './social-media.service';
import { Consultant, ConsultantService } from '../consultant';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-social-media-dialog',
    templateUrl: './social-media-dialog.component.html'
})
export class SocialMediaDialogComponent implements OnInit {

    socialMedia: SocialMedia;
    isSaving: boolean;

    consultants: Consultant[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private socialMediaService: SocialMediaService,
        private consultantService: ConsultantService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.consultantService.query()
            .subscribe((res: ResponseWrapper) => { this.consultants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.socialMedia.id !== undefined) {
            this.subscribeToSaveResponse(
                this.socialMediaService.update(this.socialMedia));
        } else {
            this.subscribeToSaveResponse(
                this.socialMediaService.create(this.socialMedia));
        }
    }

    private subscribeToSaveResponse(result: Observable<SocialMedia>) {
        result.subscribe((res: SocialMedia) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SocialMedia) {
        this.eventManager.broadcast({ name: 'socialMediaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackConsultantById(index: number, item: Consultant) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-social-media-popup',
    template: ''
})
export class SocialMediaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private socialMediaPopupService: SocialMediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.socialMediaPopupService
                    .open(SocialMediaDialogComponent as Component, params['id']);
            } else {
                this.socialMediaPopupService
                    .open(SocialMediaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
