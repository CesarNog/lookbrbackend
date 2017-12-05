/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TimelineMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/timeline-my-suffix/timeline-my-suffix-detail.component';
import { TimelineMySuffixService } from '../../../../../../main/webapp/app/entities/timeline-my-suffix/timeline-my-suffix.service';
import { TimelineMySuffix } from '../../../../../../main/webapp/app/entities/timeline-my-suffix/timeline-my-suffix.model';

describe('Component Tests', () => {

    describe('TimelineMySuffix Management Detail Component', () => {
        let comp: TimelineMySuffixDetailComponent;
        let fixture: ComponentFixture<TimelineMySuffixDetailComponent>;
        let service: TimelineMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [TimelineMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TimelineMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(TimelineMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TimelineMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimelineMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TimelineMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.timeline).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
