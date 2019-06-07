export class Autor{
    imeAutora : string;
    prezimeAutora : string;
    email: string;
    city: string;
    state: string;

    constructor(a: AutorInterface = {}) {
        this.imeAutora = a.imeAutora;
        this.prezimeAutora = a.prezimeAutora;
        this.email = a.email;
        this.city = a.city;
        this.state = a.state;
    }
}

interface AutorInterface {
    imeAutora?: string;
    prezimeAutora?: string;
    email?: string;
    city?: string;
    state?: string;
}