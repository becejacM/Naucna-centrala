export class Response {
    value: String;
  
    constructor(inter: ResponseInterface = {}) {
        this.value = inter.value;
    }
}

interface ResponseInterface {
    value?: String;
 
}