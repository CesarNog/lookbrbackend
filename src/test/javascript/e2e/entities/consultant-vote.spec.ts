import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('ConsultantVote e2e test', () => {

    let navBarPage: NavBarPage;
    let consultantVoteDialogPage: ConsultantVoteDialogPage;
    let consultantVoteComponentsPage: ConsultantVoteComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ConsultantVotes', () => {
        navBarPage.goToEntity('consultant-vote');
        consultantVoteComponentsPage = new ConsultantVoteComponentsPage();
        expect(consultantVoteComponentsPage.getTitle()).toMatch(/lookbrbackendApp.consultantVote.home.title/);

    });

    it('should load create ConsultantVote dialog', () => {
        consultantVoteComponentsPage.clickOnCreateButton();
        consultantVoteDialogPage = new ConsultantVoteDialogPage();
        expect(consultantVoteDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.consultantVote.home.createOrEditLabel/);
        consultantVoteDialogPage.close();
    });

    it('should create and save ConsultantVotes', () => {
        consultantVoteComponentsPage.clickOnCreateButton();
        consultantVoteDialogPage.setConsultantProfilePhotoUrlInput('consultantProfilePhotoUrl');
        expect(consultantVoteDialogPage.getConsultantProfilePhotoUrlInput()).toMatch('consultantProfilePhotoUrl');
        consultantVoteDialogPage.lookSelectLastOption();
        consultantVoteDialogPage.save();
        expect(consultantVoteDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ConsultantVoteComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-consultant-vote div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ConsultantVoteDialogPage {
    modalTitle = element(by.css('h4#myConsultantVoteLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    consultantProfilePhotoUrlInput = element(by.css('input#field_consultantProfilePhotoUrl'));
    lookSelect = element(by.css('select#field_look'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setConsultantProfilePhotoUrlInput = function (consultantProfilePhotoUrl) {
        this.consultantProfilePhotoUrlInput.sendKeys(consultantProfilePhotoUrl);
    }

    getConsultantProfilePhotoUrlInput = function () {
        return this.consultantProfilePhotoUrlInput.getAttribute('value');
    }

    lookSelectLastOption = function () {
        this.lookSelect.all(by.tagName('option')).last().click();
    }

    lookSelectOption = function (option) {
        this.lookSelect.sendKeys(option);
    }

    getLookSelect = function () {
        return this.lookSelect;
    }

    getLookSelectedOption = function () {
        return this.lookSelect.element(by.css('option:checked')).getText();
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
