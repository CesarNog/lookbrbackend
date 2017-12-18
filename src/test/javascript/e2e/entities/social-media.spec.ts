import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('SocialMedia e2e test', () => {

    let navBarPage: NavBarPage;
    let socialMediaDialogPage: SocialMediaDialogPage;
    let socialMediaComponentsPage: SocialMediaComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SocialMedias', () => {
        navBarPage.goToEntity('social-media');
        socialMediaComponentsPage = new SocialMediaComponentsPage();
        expect(socialMediaComponentsPage.getTitle()).toMatch(/lookbrbackendApp.socialMedia.home.title/);

    });

    it('should load create SocialMedia dialog', () => {
        socialMediaComponentsPage.clickOnCreateButton();
        socialMediaDialogPage = new SocialMediaDialogPage();
        expect(socialMediaDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.socialMedia.home.createOrEditLabel/);
        socialMediaDialogPage.close();
    });

    it('should create and save SocialMedias', () => {
        socialMediaComponentsPage.clickOnCreateButton();
        socialMediaDialogPage.setTypeInput('type');
        expect(socialMediaDialogPage.getTypeInput()).toMatch('type');
        socialMediaDialogPage.setUrlInput('url');
        expect(socialMediaDialogPage.getUrlInput()).toMatch('url');
        socialMediaDialogPage.consultantSelectLastOption();
        socialMediaDialogPage.save();
        expect(socialMediaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SocialMediaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-social-media div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SocialMediaDialogPage {
    modalTitle = element(by.css('h4#mySocialMediaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    typeInput = element(by.css('input#field_type'));
    urlInput = element(by.css('input#field_url'));
    consultantSelect = element(by.css('select#field_consultant'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTypeInput = function (type) {
        this.typeInput.sendKeys(type);
    }

    getTypeInput = function () {
        return this.typeInput.getAttribute('value');
    }

    setUrlInput = function (url) {
        this.urlInput.sendKeys(url);
    }

    getUrlInput = function () {
        return this.urlInput.getAttribute('value');
    }

    consultantSelectLastOption = function () {
        this.consultantSelect.all(by.tagName('option')).last().click();
    }

    consultantSelectOption = function (option) {
        this.consultantSelect.sendKeys(option);
    }

    getConsultantSelect = function () {
        return this.consultantSelect;
    }

    getConsultantSelectedOption = function () {
        return this.consultantSelect.element(by.css('option:checked')).getText();
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
