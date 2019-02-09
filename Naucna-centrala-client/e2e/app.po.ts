import { browser, element, by } from "protractor";

export class BookBuyPage{

    navigateToLogin() {
        return browser.get('/login');
      }
    
    navigateToBookBuy(){
        return browser.get('/search-and-buy');
    }

    getBuyButton(){
        return element.all(by.buttonText("Kupi"));
    }

    getBankButton(){
        return element.all(by.buttonText("BANK"));
    }

    getBuyBankButton(){
        return element(by.id("button"));
    }
    getCardHolderNameText(){
        return element(by.id('cardHolderName'));
    }

    setCardHolderNameText(chn){
        this.getCardHolderNameText().clear();
        this.getCardHolderNameText().sendKeys(chn);
    }
    getPanText(){
        return element(by.id('pan'));
    }

    setPanText(pan){
        this.getPanText().clear();
        this.getPanText().sendKeys(pan);
    }

    getSecurityCodeText(){
        return element(by.id('securityCode'));
    }

    setSecurityCodeText(sc){
        this.getSecurityCodeText().clear();
        this.getSecurityCodeText().sendKeys(sc);
    }

    getCardExpirationText(){
        return element(by.id('cardExpiration'));
    }

    setCardExpirationText(ce){
        this.getCardExpirationText().clear();
        this.getCardExpirationText().sendKeys(ce);
    }

    imputDataBank(chn, pan, sc, ce){
        this.setCardHolderNameText(chn);
        this.setPanText(pan);
        this.setSecurityCodeText(sc);
        this.setCardExpirationText(ce);

    }
    getUserNameText(){
        return element(by.id('login-username'));
    }

    setUserNameText(username){
        this.getUserNameText().clear();
        this.getUserNameText().sendKeys(username);
    }

    getPasswordText(){
        return element(by.id('login-password'));
    }

    getLoginButto(){
        return element(by.id('login-button'));
    }

    setPasswordText(password){
        this.getPasswordText().clear();
        this.getPasswordText().sendKeys(password);
    }


    doLogin(username:string,password:string){
        this.navigateToLogin();
        this.setUserNameText(username);
        this.setPasswordText(password);
        this.getLoginButto().click();
        browser.sleep(2000);
    }

}