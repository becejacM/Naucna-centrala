export class ReviewDTO{
    commentAuthor : string;
    suggestion : string;
    commentEditor : string;
    newReviewers : string;
    reviewer : string;

    constructor(a: ReviewInterface = {}) {
        this.commentAuthor = a.commentAuthor;
        this.suggestion = a.suggestion;
        this.commentEditor = a.commentEditor;
        this.newReviewers = a.newReviewers;
        this.reviewer = a.reviewer;
    }
}

interface ReviewInterface {
    commentAuthor?: string;
    suggestion?: string;
    commentEditor?: string;
    newReviewers?: string;
    reviewer?: string;
}