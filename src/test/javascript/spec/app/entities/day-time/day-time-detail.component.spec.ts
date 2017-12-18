/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DayTimeDetailComponent } from '../../../../../../main/webapp/app/entities/day-time/day-time-detail.component';
import { DayTimeService } from '../../../../../../main/webapp/app/entities/day-time/day-time.service';
import { DayTime } from '../../../../../../main/webapp/app/entities/day-time/day-time.model';

describe('Component Tests', () => {

    describe('DayTime Management Detail Component', () => {
        let comp: DayTimeDetailComponent;
        let fixture: ComponentFixture<DayTimeDetailComponent>;
        let service: DayTimeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [DayTimeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DayTimeService,
                    JhiEventManager
                ]
            }).overrideTemplate(DayTimeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DayTimeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DayTimeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DayTime(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dayTime).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
