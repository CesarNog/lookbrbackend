/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LookbrbackendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LoginDetailComponent } from '../../../../../../main/webapp/app/entities/login/login-detail.component';
import { LoginService } from '../../../../../../main/webapp/app/entities/login/login.service';
import { Login } from '../../../../../../main/webapp/app/entities/login/login.model';

describe('Component Tests', () => {

    describe('Login Management Detail Component', () => {
        let comp: LoginDetailComponent;
        let fixture: ComponentFixture<LoginDetailComponent>;
        let service: LoginService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LookbrbackendTestModule],
                declarations: [LoginDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LoginService,
                    JhiEventManager
                ]
            }).overrideTemplate(LoginDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LoginDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoginService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Login(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.login).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
