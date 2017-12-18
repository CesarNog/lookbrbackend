/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OccasionDetailComponent } from '../../../../../../main/webapp/app/entities/occasion/occasion-detail.component';
import { OccasionService } from '../../../../../../main/webapp/app/entities/occasion/occasion.service';
import { Occasion } from '../../../../../../main/webapp/app/entities/occasion/occasion.model';

describe('Component Tests', () => {

    describe('Occasion Management Detail Component', () => {
        let comp: OccasionDetailComponent;
        let fixture: ComponentFixture<OccasionDetailComponent>;
        let service: OccasionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [OccasionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OccasionService,
                    JhiEventManager
                ]
            }).overrideTemplate(OccasionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OccasionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OccasionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Occasion(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.occasion).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
