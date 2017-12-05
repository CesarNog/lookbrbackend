/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SignupMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/signup-my-suffix/signup-my-suffix-detail.component';
import { SignupMySuffixService } from '../../../../../../main/webapp/app/entities/signup-my-suffix/signup-my-suffix.service';
import { SignupMySuffix } from '../../../../../../main/webapp/app/entities/signup-my-suffix/signup-my-suffix.model';

describe('Component Tests', () => {

    describe('SignupMySuffix Management Detail Component', () => {
        let comp: SignupMySuffixDetailComponent;
        let fixture: ComponentFixture<SignupMySuffixDetailComponent>;
        let service: SignupMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [SignupMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SignupMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(SignupMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SignupMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SignupMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SignupMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.signup).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
