import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SocialMedia } from './social-media.model';
import { SocialMediaPopupService } from './social-media-popup.service';
import { SocialMediaService } from './social-media.service';

@Component({
    selector: 'jhi-social-media-delete-dialog',
    templateUrl: './social-media-delete-dialog.component.html'
})
export class SocialMediaDeleteDialogComponent {

    socialMedia: SocialMedia;

    constructor(
        private socialMediaService: SocialMediaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.socialMediaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'socialMediaListModification',
                content: 'Deleted an socialMedia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-social-media-delete-popup',
    template: ''
})
export class SocialMediaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private socialMediaPopupService: SocialMediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.socialMediaPopupService
                .open(SocialMediaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
