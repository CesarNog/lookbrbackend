import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Consultants e2e test', () => {

    let navBarPage: NavBarPage;
    let consultantsDialogPage: ConsultantsDialogPage;
    let consultantsComponentsPage: ConsultantsComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Consultants', () => {
        navBarPage.goToEntity('consultants-my-suffix');
        consultantsComponentsPage = new ConsultantsComponentsPage();
        expect(consultantsComponentsPage.getTitle()).toMatch(/lookbrbackendApp.consultants.home.title/);

    });

    it('should load create Consultants dialog', () => {
        consultantsComponentsPage.clickOnCreateButton();
        consultantsDialogPage = new ConsultantsDialogPage();
        expect(consultantsDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.consultants.home.createOrEditLabel/);
        consultantsDialogPage.close();
    });

    it('should create and save Consultants', () => {
        consultantsComponentsPage.clickOnCreateButton();
        consultantsDialogPage.setPageInput('5');
        expect(consultantsDialogPage.getPageInput()).toMatch('5');
        consultantsDialogPage.save();
        expect(consultantsDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ConsultantsComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-consultants-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ConsultantsDialogPage {
    modalTitle = element(by.css('h4#myConsultantsLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    pageInput = element(by.css('input#field_page'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setPageInput = function (page) {
        this.pageInput.sendKeys(page);
    }

    getPageInput = function () {
        return this.pageInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
