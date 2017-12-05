/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IntentionMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/intention-my-suffix/intention-my-suffix-detail.component';
import { IntentionMySuffixService } from '../../../../../../main/webapp/app/entities/intention-my-suffix/intention-my-suffix.service';
import { IntentionMySuffix } from '../../../../../../main/webapp/app/entities/intention-my-suffix/intention-my-suffix.model';

describe('Component Tests', () => {

    describe('IntentionMySuffix Management Detail Component', () => {
        let comp: IntentionMySuffixDetailComponent;
        let fixture: ComponentFixture<IntentionMySuffixDetailComponent>;
        let service: IntentionMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [IntentionMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IntentionMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(IntentionMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IntentionMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntentionMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new IntentionMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.intention).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
