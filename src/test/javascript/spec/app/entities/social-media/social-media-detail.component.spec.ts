/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SocialMediaDetailComponent } from '../../../../../../main/webapp/app/entities/social-media/social-media-detail.component';
import { SocialMediaService } from '../../../../../../main/webapp/app/entities/social-media/social-media.service';
import { SocialMedia } from '../../../../../../main/webapp/app/entities/social-media/social-media.model';

describe('Component Tests', () => {

    describe('SocialMedia Management Detail Component', () => {
        let comp: SocialMediaDetailComponent;
        let fixture: ComponentFixture<SocialMediaDetailComponent>;
        let service: SocialMediaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [SocialMediaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SocialMediaService,
                    JhiEventManager
                ]
            }).overrideTemplate(SocialMediaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SocialMediaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SocialMediaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SocialMedia(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.socialMedia).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
