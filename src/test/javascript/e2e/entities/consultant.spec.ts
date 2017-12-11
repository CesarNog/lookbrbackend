import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Consultant e2e test', () => {

    let navBarPage: NavBarPage;
    let consultantDialogPage: ConsultantDialogPage;
    let consultantComponentsPage: ConsultantComponentsPage;
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
        navBarPage.goToEntity('consultant');
        consultantComponentsPage = new ConsultantComponentsPage();
        expect(consultantComponentsPage.getTitle()).toMatch(/lookbrbackendApp.consultant.home.title/);

    });

    it('should load create Consultant dialog', () => {
        consultantComponentsPage.clickOnCreateButton();
        consultantDialogPage = new ConsultantDialogPage();
        expect(consultantDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.consultant.home.createOrEditLabel/);
        consultantDialogPage.close();
    });

    it('should create and save Consultants', () => {
        consultantComponentsPage.clickOnCreateButton();
        consultantDialogPage.setConsultantIdInput('consultantId');
        expect(consultantDialogPage.getConsultantIdInput()).toMatch('consultantId');
        consultantDialogPage.setConsultantNameInput('consultantName');
        expect(consultantDialogPage.getConsultantNameInput()).toMatch('consultantName');
        consultantDialogPage.setConsultantDescriptionInput('consultantDescription');
        expect(consultantDialogPage.getConsultantDescriptionInput()).toMatch('consultantDescription');
        consultantDialogPage.setConsultantCoverPhotoURLInput('consultantCoverPhotoURL');
        expect(consultantDialogPage.getConsultantCoverPhotoURLInput()).toMatch('consultantCoverPhotoURL');
        consultantDialogPage.setConsultantProfilePhotoURLInput('consultantProfilePhotoURL');
        expect(consultantDialogPage.getConsultantProfilePhotoURLInput()).toMatch('consultantProfilePhotoURL');
        consultantDialogPage.setChargeInput('5');
        expect(consultantDialogPage.getChargeInput()).toMatch('5');
        consultantDialogPage.setInspirationURLInput('inspirationURL');
        expect(consultantDialogPage.getInspirationURLInput()).toMatch('inspirationURL');
        consultantDialogPage.setProfilePhotoInput('profilePhoto');
        expect(consultantDialogPage.getProfilePhotoInput()).toMatch('profilePhoto');
        consultantDialogPage.statusSelectLastOption();
        consultantDialogPage.setPageInput('5');
        expect(consultantDialogPage.getPageInput()).toMatch('5');
        consultantDialogPage.lookSelectLastOption();
        consultantDialogPage.save();
        expect(consultantDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ConsultantComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-consultant div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ConsultantDialogPage {
    modalTitle = element(by.css('h4#myConsultantLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    consultantIdInput = element(by.css('input#field_consultantId'));
    consultantNameInput = element(by.css('input#field_consultantName'));
    consultantDescriptionInput = element(by.css('input#field_consultantDescription'));
    consultantCoverPhotoURLInput = element(by.css('input#field_consultantCoverPhotoURL'));
    consultantProfilePhotoURLInput = element(by.css('input#field_consultantProfilePhotoURL'));
    chargeInput = element(by.css('input#field_charge'));
    inspirationURLInput = element(by.css('input#field_inspirationURL'));
    profilePhotoInput = element(by.css('input#field_profilePhoto'));
    statusSelect = element(by.css('select#field_status'));
    pageInput = element(by.css('input#field_page'));
    lookSelect = element(by.css('select#field_look'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setConsultantIdInput = function (consultantId) {
        this.consultantIdInput.sendKeys(consultantId);
    }

    getConsultantIdInput = function () {
        return this.consultantIdInput.getAttribute('value');
    }

    setConsultantNameInput = function (consultantName) {
        this.consultantNameInput.sendKeys(consultantName);
    }

    getConsultantNameInput = function () {
        return this.consultantNameInput.getAttribute('value');
    }

    setConsultantDescriptionInput = function (consultantDescription) {
        this.consultantDescriptionInput.sendKeys(consultantDescription);
    }

    getConsultantDescriptionInput = function () {
        return this.consultantDescriptionInput.getAttribute('value');
    }

    setConsultantCoverPhotoURLInput = function (consultantCoverPhotoURL) {
        this.consultantCoverPhotoURLInput.sendKeys(consultantCoverPhotoURL);
    }

    getConsultantCoverPhotoURLInput = function () {
        return this.consultantCoverPhotoURLInput.getAttribute('value');
    }

    setConsultantProfilePhotoURLInput = function (consultantProfilePhotoURL) {
        this.consultantProfilePhotoURLInput.sendKeys(consultantProfilePhotoURL);
    }

    getConsultantProfilePhotoURLInput = function () {
        return this.consultantProfilePhotoURLInput.getAttribute('value');
    }

    setChargeInput = function (charge) {
        this.chargeInput.sendKeys(charge);
    }

    getChargeInput = function () {
        return this.chargeInput.getAttribute('value');
    }

    setInspirationURLInput = function (inspirationURL) {
        this.inspirationURLInput.sendKeys(inspirationURL);
    }

    getInspirationURLInput = function () {
        return this.inspirationURLInput.getAttribute('value');
    }

    setProfilePhotoInput = function (profilePhoto) {
        this.profilePhotoInput.sendKeys(profilePhoto);
    }

    getProfilePhotoInput = function () {
        return this.profilePhotoInput.getAttribute('value');
    }

    setStatusSelect = function (status) {
        this.statusSelect.sendKeys(status);
    }

    getStatusSelect = function () {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    statusSelectLastOption = function () {
        this.statusSelect.all(by.tagName('option')).last().click();
    }
    setPageInput = function (page) {
        this.pageInput.sendKeys(page);
    }

    getPageInput = function () {
        return this.pageInput.getAttribute('value');
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
