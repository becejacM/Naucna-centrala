import { naucnaOblast } from "./naucnaOblast";

export class Paper{
    id : string;
    naslovRada : string;
    apstrakt : string;
    keywords : string;
    autori : any[];
    naucnaOblast : any;
    nazivCasopisa : string;
    rad : string;

    constructor(paper: PaperInterface = {}) {
        this.id = paper.id;
        this.naslovRada = paper.naslovRada;
        this.apstrakt = paper.apstrakt;
        this.keywords = paper.keywords;
        this.autori = paper.autori;
        this.naucnaOblast = paper.naucnaOblast;
        this.nazivCasopisa = paper.nazivCasopisa;
        this.rad = paper.rad;
    }
}

interface PaperInterface {
    id ?: string;
    naslovRada ?: string;
    apstrakt ?: string;
    keywords ?: string;
    autori ?: any[];
    naucnaOblast ?: any;
    nazivCasopisa ?: string;
    rad ?: string;
}