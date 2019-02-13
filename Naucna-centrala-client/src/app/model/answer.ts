import { naucnaOblast } from "./naucnaOblast";

export class Answer{
     answer: string;

    constructor(answer: AnswerInterface = {}) {
        this.answer = answer.answer;
    }
}

interface AnswerInterface {
    answer ?: string;
}