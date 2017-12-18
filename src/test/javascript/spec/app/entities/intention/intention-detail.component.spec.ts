/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IntentionDetailComponent } from '../../../../../../main/webapp/app/entities/intention/intention-detail.component';
import { IntentionService } from '../../../../../../main/webapp/app/entities/intention/intention.service';
import { Intention } from '../../../../../../main/webapp/app/entities/intention/intention.model';

describe('Component Tests', () => {

    describe('Intention Management Detail Component', () => {
        let comp: IntentionDetailComponent;
        let fixture: ComponentFixture<IntentionDetailComponent>;
        let service: IntentionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [IntentionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IntentionService,
                    JhiEventManager
                ]
            }).overrideTemplate(IntentionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IntentionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntentionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Intention(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.intention).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
