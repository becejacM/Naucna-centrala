import { browser } from "protractor";
import { BookBuyPage } from "./app.po";

describe('Buy Book', function () {

    let page  = new  BookBuyPage();

    beforeEach(()=>{
        page.doLogin('autor1','MDJ421054-bsep');
        
    });

    it('shoud buy a bok and display a success page', () => {
        page.navigateToBookBuy();
        browser.sleep(1000);
       /* page.getBuyButton().count().then((size)=>{
            expect(size).toBe(3);
        })*/
        page.getBuyButton().first().click().then(()=>{
            browser.getCurrentUrl().then((url)=>{
                expect(url.startsWith("http://192.168.0.16:4201/orders")).toBeTruthy();
                page.getBankButton().click().then(()=>{
                    browser.getCurrentUrl().then((urlBank)=>{
                        expect(urlBank.startsWith("http://192.168.0.19:4200/payment")).toBeTruthy();
                        page.imputDataBank("Zaharije Trnavcevic", "4026708020739462", "123", "05-05-2019");
                        page.getBuyBankButton().click().then(()=>{
                            browser.sleep(3000);
                                browser.getCurrentUrl().then((myUrl)=>{
                                    //alert(myUrl);
                                    expect(myUrl.startsWith("http://localhost:4200/success")).toBeTruthy();
                                })                        
                        })
                    })
                })

            })
        })
      });

      
});