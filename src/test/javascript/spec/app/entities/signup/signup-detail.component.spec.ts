/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SignupDetailComponent } from '../../../../../../main/webapp/app/entities/signup/signup-detail.component';
import { SignupService } from '../../../../../../main/webapp/app/entities/signup/signup.service';
import { Signup } from '../../../../../../main/webapp/app/entities/signup/signup.model';

describe('Component Tests', () => {

    describe('Signup Management Detail Component', () => {
        let comp: SignupDetailComponent;
        let fixture: ComponentFixture<SignupDetailComponent>;
        let service: SignupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [SignupDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SignupService,
                    JhiEventManager
                ]
            }).overrideTemplate(SignupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SignupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SignupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Signup(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.signup).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
