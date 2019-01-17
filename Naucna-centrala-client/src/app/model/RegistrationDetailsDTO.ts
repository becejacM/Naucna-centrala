export class RegistrationDetailsDTO {
    firstname: string;
    lastname: string;
    username: string;
    password: string;
    email: string;
    city: string;
    state: string;
    processInstanceId: string;

    constructor(regDetCfg: RegistrationDetailsInterface = {}) {
        this.firstname = regDetCfg.firstname;
        this.lastname = regDetCfg.lastname;
        this.username = regDetCfg.username;
        this.password = regDetCfg.password;
        this.email = regDetCfg.email;
        this.city = regDetCfg.city;
        this.state = regDetCfg.state;
    }
    set setprocessInstanceId(newprocessInstanceId: string){
        this.processInstanceId = newprocessInstanceId;
    }
}

interface RegistrationDetailsInterface {
    firstname?: string;
    lastname?: string;
    username?: string;
    password?: string;
    email?: string;
    city?: string;
    state?: string;
}