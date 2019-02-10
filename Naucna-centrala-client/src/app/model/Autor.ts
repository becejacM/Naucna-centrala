export class Autor{
    imeAutora : string;
    prezimeAutora : string;

    constructor(a: AutorInterface = {}) {
        this.imeAutora = a.imeAutora;
        this.prezimeAutora = a.prezimeAutora;
    }
}

interface AutorInterface {
    imeAutora?: string;
    prezimeAutora?: string;
}