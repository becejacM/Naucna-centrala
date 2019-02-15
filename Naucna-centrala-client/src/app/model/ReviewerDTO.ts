export class ReviewersDTO{
    username : string;
    firstname : string;
    lastname : string;
    no : string;

    constructor(a: ReviewerInterface = {}) {
        this.username = a.username;
        this.firstname = a.firstname;
        this.lastname = a.lastname;
        this.no = a.no;
    }
}

interface ReviewerInterface {
    username?: string;
    firstname?: string;
    lastname?: string;
    no?: string;
}