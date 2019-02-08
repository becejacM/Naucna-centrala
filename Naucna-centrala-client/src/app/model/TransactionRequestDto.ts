export class TransactionRequestDto {
    //sellerUuid: String;
    //amount: number;
    //description: String;
    //successUrl:String;
    //failUrl:String;
    naslovRada:String;
    kupac:String;
    prodavac:String;

    constructor(inter: TransactionRequestDtoInterface = {}) {
        this.naslovRada = inter.naslovRada;
        this.kupac = inter.kupac;
        this.prodavac = inter.prodavac;
        //this.successUrl = inter.successUrl;
        //this.failUrl = inter.failUrl;
    }
}

interface TransactionRequestDtoInterface {
    //sellerUuid?: String;
    //amount?: number;
    //description?: String;
    //successUrl?:String;
    //failUrl?:String;
    naslovRada?:String;
    kupac?:String;
    prodavac?:String;
}