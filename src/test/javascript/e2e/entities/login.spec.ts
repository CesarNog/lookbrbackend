import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Login e2e test', () => {

    let navBarPage: NavBarPage;
    let loginDialogPage: LoginDialogPage;
    let loginComponentsPage: LoginComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Logins', () => {
        navBarPage.goToEntity('login');
        loginComponentsPage = new LoginComponentsPage();
        expect(loginComponentsPage.getTitle()).toMatch(/lookbrbackendApp.login.home.title/);

    });

    it('should load create Login dialog', () => {
        loginComponentsPage.clickOnCreateButton();
        loginDialogPage = new LoginDialogPage();
        expect(loginDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.login.home.createOrEditLabel/);
        loginDialogPage.close();
    });

    it('should create and save Logins', () => {
        loginComponentsPage.clickOnCreateButton();
        loginDialogPage.loginTypeSelectLastOption();
        loginDialogPage.setTokenInput('token');
        expect(loginDialogPage.getTokenInput()).toMatch('token');
        loginDialogPage.userSelectLastOption();
        loginDialogPage.save();
        expect(loginDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LoginComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-login div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class LoginDialogPage {
    modalTitle = element(by.css('h4#myLoginLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    loginTypeSelect = element(by.css('select#field_loginType'));
    tokenInput = element(by.css('input#field_token'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setLoginTypeSelect = function (loginType) {
        this.loginTypeSelect.sendKeys(loginType);
    }

    getLoginTypeSelect = function () {
        return this.loginTypeSelect.element(by.css('option:checked')).getText();
    }

    loginTypeSelectLastOption = function () {
        this.loginTypeSelect.all(by.tagName('option')).last().click();
    }
    setTokenInput = function (token) {
        this.tokenInput.sendKeys(token);
    }

    getTokenInput = function () {
        return this.tokenInput.getAttribute('value');
    }

    userSelectLastOption = function () {
        this.userSelect.all(by.tagName('option')).last().click();
    }

    userSelectOption = function (option) {
        this.userSelect.sendKeys(option);
    }

    getUserSelect = function () {
        return this.userSelect;
    }

    getUserSelectedOption = function () {
        return this.userSelect.element(by.css('option:checked')).getText();
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
