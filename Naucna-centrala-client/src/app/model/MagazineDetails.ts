export class MagazineDetails{
    id : string;
    name : string;
    issn : string;
    paymentMethod : string;
    processInstanceId : string;

    constructor(magazine: MagazineInterface = {}) {
        this.id = magazine.id;
        this.name = magazine.name;
        this.issn = magazine.issn;
        this.paymentMethod = magazine.paymentMethod;
        this.processInstanceId = magazine.processInstanceId;
    }
    set setprocessInstanceId(newprocessInstanceId: string){
        this.processInstanceId = newprocessInstanceId;
    }
}

interface MagazineInterface {
    id?: string;
    name?: string;
    issn?: string;
    paymentMethod?: string;
    processInstanceId?: string;
}