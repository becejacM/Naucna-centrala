export class SimpleQuery {
    field: String;
    value: String;
  
    constructor(inter: SimpleQueryInterface = {}) {
        this.field = inter.field;
        this.value = inter.value;
    }
}

interface SimpleQueryInterface {
    field?: String;
    value?: String;
 
}