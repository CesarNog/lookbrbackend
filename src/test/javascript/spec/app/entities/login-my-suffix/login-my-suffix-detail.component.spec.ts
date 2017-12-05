/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LoginMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/login-my-suffix/login-my-suffix-detail.component';
import { LoginMySuffixService } from '../../../../../../main/webapp/app/entities/login-my-suffix/login-my-suffix.service';
import { LoginMySuffix } from '../../../../../../main/webapp/app/entities/login-my-suffix/login-my-suffix.model';

describe('Component Tests', () => {

    describe('LoginMySuffix Management Detail Component', () => {
        let comp: LoginMySuffixDetailComponent;
        let fixture: ComponentFixture<LoginMySuffixDetailComponent>;
        let service: LoginMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [LoginMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LoginMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(LoginMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LoginMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoginMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LoginMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.login).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
