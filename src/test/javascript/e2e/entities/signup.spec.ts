import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Signup e2e test', () => {

    let navBarPage: NavBarPage;
    let signupDialogPage: SignupDialogPage;
    let signupComponentsPage: SignupComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Signups', () => {
        navBarPage.goToEntity('signup');
        signupComponentsPage = new SignupComponentsPage();
        expect(signupComponentsPage.getTitle()).toMatch(/lookbrbackendApp.signup.home.title/);

    });

    it('should load create Signup dialog', () => {
        signupComponentsPage.clickOnCreateButton();
        signupDialogPage = new SignupDialogPage();
        expect(signupDialogPage.getModalTitle()).toMatch(/lookbrbackendApp.signup.home.createOrEditLabel/);
        signupDialogPage.close();
    });

    it('should create and save Signups', () => {
        signupComponentsPage.clickOnCreateButton();
        signupDialogPage.setEmailInput('email');
        expect(signupDialogPage.getEmailInput()).toMatch('email');
        signupDialogPage.loginTypeSelectLastOption();
        signupDialogPage.setPasswordInput('password');
        expect(signupDialogPage.getPasswordInput()).toMatch('password');
        signupDialogPage.setProfilePhotoUrlInput('profilePhotoUrl');
        expect(signupDialogPage.getProfilePhotoUrlInput()).toMatch('profilePhotoUrl');
        signupDialogPage.setProfilePhotoInput('profilePhoto');
        expect(signupDialogPage.getProfilePhotoInput()).toMatch('profilePhoto');
        signupDialogPage.setUsernameInput('username');
        expect(signupDialogPage.getUsernameInput()).toMatch('username');
        signupDialogPage.setTokenInput('token');
        expect(signupDialogPage.getTokenInput()).toMatch('token');
        signupDialogPage.save();
        expect(signupDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SignupComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-signup div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SignupDialogPage {
    modalTitle = element(by.css('h4#mySignupLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    emailInput = element(by.css('input#field_email'));
    loginTypeSelect = element(by.css('select#field_loginType'));
    passwordInput = element(by.css('input#field_password'));
    profilePhotoUrlInput = element(by.css('input#field_profilePhotoUrl'));
    profilePhotoInput = element(by.css('input#field_profilePhoto'));
    usernameInput = element(by.css('input#field_username'));
    tokenInput = element(by.css('input#field_token'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setEmailInput = function (email) {
        this.emailInput.sendKeys(email);
    }

    getEmailInput = function () {
        return this.emailInput.getAttribute('value');
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
    setPasswordInput = function (password) {
        this.passwordInput.sendKeys(password);
    }

    getPasswordInput = function () {
        return this.passwordInput.getAttribute('value');
    }

    setProfilePhotoUrlInput = function (profilePhotoUrl) {
        this.profilePhotoUrlInput.sendKeys(profilePhotoUrl);
    }

    getProfilePhotoUrlInput = function () {
        return this.profilePhotoUrlInput.getAttribute('value');
    }

    setProfilePhotoInput = function (profilePhoto) {
        this.profilePhotoInput.sendKeys(profilePhoto);
    }

    getProfilePhotoInput = function () {
        return this.profilePhotoInput.getAttribute('value');
    }

    setUsernameInput = function (username) {
        this.usernameInput.sendKeys(username);
    }

    getUsernameInput = function () {
        return this.usernameInput.getAttribute('value');
    }

    setTokenInput = function (token) {
        this.tokenInput.sendKeys(token);
    }

    getTokenInput = function () {
        return this.tokenInput.getAttribute('value');
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
