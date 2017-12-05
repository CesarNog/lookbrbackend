import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Intention e2e test', () => {

    let navBarPage: NavBarPage;
    let intentionDialogPage: IntentionDialogPage;
    let intentionComponentsPage: IntentionComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Intentions', () => {
        navBarPage.goToEntity('intention-my-suffix');
        intentionComponentsPage = new IntentionComponentsPage();
        expect(intentionComponentsPage.getTitle()).toMatch(/lookbrbackendApp.intention.home.title/);

    });

    it('should load create Intention dialog', () => {
        intentionComponentsPage.clickOnCreateButton();
        intentionDialogPage = new IntentionDialogPage();
        expect(intentionDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.intention.home.createOrEditLabel/);
        intentionDialogPage.close();
    });

    it('should create and save Intentions', () => {
        intentionComponentsPage.clickOnCreateButton();
        intentionDialogPage.setPageInput('5');
        expect(intentionDialogPage.getPageInput()).toMatch('5');
        intentionDialogPage.save();
        expect(intentionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IntentionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-intention-my-suffix div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IntentionDialogPage {
    modalTitle = element(by.css('h4#myIntentionLabel'));
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
