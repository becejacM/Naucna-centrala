export class TransactionRequestDto {
    sellerUuid: String;
    amount: number;
    description: String;
    successUrl:String;
    failUrl:String;

    constructor(inter: TransactionRequestDtoInterface = {}) {
        this.sellerUuid = inter.sellerUuid;
        this.amount = inter.amount;
        this.description = inter.description;
        this.successUrl = inter.successUrl;
        this.failUrl = inter.failUrl;
    }
}

interface TransactionRequestDtoInterface {
    sellerUuid?: String;
    amount?: number;
    description?: String;
    successUrl?:String;
    failUrl?:String;
}