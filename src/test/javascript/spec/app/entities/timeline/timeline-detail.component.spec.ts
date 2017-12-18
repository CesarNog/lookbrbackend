/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TimelineDetailComponent } from '../../../../../../main/webapp/app/entities/timeline/timeline-detail.component';
import { TimelineService } from '../../../../../../main/webapp/app/entities/timeline/timeline.service';
import { Timeline } from '../../../../../../main/webapp/app/entities/timeline/timeline.model';

describe('Component Tests', () => {

    describe('Timeline Management Detail Component', () => {
        let comp: TimelineDetailComponent;
        let fixture: ComponentFixture<TimelineDetailComponent>;
        let service: TimelineService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [TimelineDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TimelineService,
                    JhiEventManager
                ]
            }).overrideTemplate(TimelineDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TimelineDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimelineService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Timeline(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.timeline).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
