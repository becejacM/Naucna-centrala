export class AdvancedQuery {
    field1: String;
    value1: String;
    field2: String;
    value2: String;
    operation: String;
  
    constructor(inter: AdvancedQueryInterface = {}) {
        this.field1 = inter.field1;
        this.value1 = inter.value1;
        this.field2 = inter.field2;
        this.value2 = inter.value2;
        this.operation = inter.operation;
    }
}

interface AdvancedQueryInterface {
    field1?: String;
    value1?: String;
    field2?: String;
    value2?: String;
    operation?: String;
}