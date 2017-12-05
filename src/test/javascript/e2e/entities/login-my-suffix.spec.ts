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
        navBarPage.goToEntity('login-my-suffix');
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
        loginDialogPage.setUsernameInput('username');
        expect(loginDialogPage.getUsernameInput()).toMatch('username');
        loginDialogPage.setEmailInput('email');
        expect(loginDialogPage.getEmailInput()).toMatch('email');
        loginDialogPage.setPasswordInput('password');
        expect(loginDialogPage.getPasswordInput()).toMatch('password');
        loginDialogPage.save();
        expect(loginDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LoginComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-login-my-suffix div h2 span')).first();

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
    usernameInput = element(by.css('input#field_username'));
    emailInput = element(by.css('input#field_email'));
    passwordInput = element(by.css('input#field_password'));

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
    setUsernameInput = function (username) {
        this.usernameInput.sendKeys(username);
    }

    getUsernameInput = function () {
        return this.usernameInput.getAttribute('value');
    }

    setEmailInput = function (email) {
        this.emailInput.sendKeys(email);
    }

    getEmailInput = function () {
        return this.emailInput.getAttribute('value');
    }

    setPasswordInput = function (password) {
        this.passwordInput.sendKeys(password);
    }

    getPasswordInput = function () {
        return this.passwordInput.getAttribute('value');
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
