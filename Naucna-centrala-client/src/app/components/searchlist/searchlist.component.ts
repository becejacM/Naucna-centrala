import { Component, OnInit } from '@angular/core';
import { SearchService } from '../../services/search/search.service';
import { SimpleQuery } from '../../model/simpleQuery';
import { AdvancedQuery } from '../../model/advancedQuery';

@Component({
  selector: 'app-searchlist',
  templateUrl: './searchlist.component.html',
  styleUrls: ['./searchlist.component.css']
})
export class SearchlistComponent implements OnInit {
  public books = [];
  
  constructor(private searchService: SearchService) { }

  ngOnInit() {
    this.getAllBooks();
  }


  public getAllBooks() {
    this.searchService.getAll()
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }


  public languages = [];
  public categories = [];

  public row = { title: "", author: "", publicationYear: "", languageName: "" };
  public book = { title: "", author: "", keywords: "", publicationYear: "", languageName: "", filename: "", hightlight: "" };

  public title = "";
  public author = "";
  public keywords = "";
  public languageName = "";

  public text = "";

  public operationAND = "";
  public operationOR = "";

  public fieldTitle = "";
  public simpleQuery = new SimpleQuery;
  public advancedQuery = new AdvancedQuery;

  public searchByText() {
    this.simpleQuery.field = "text";
    this.simpleQuery.value = this.text;
    this.searchService.searchByContent(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByTextFuzzy() {
    this.simpleQuery.field = "text";
    this.simpleQuery.value = this.text;
    this.searchService.searchFuzzy(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByTextPhrase() {
    this.simpleQuery.field = "text";
    this.simpleQuery.value = this.text;
    this.searchService.searchPhrase(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchBoolean() {
    if(this.operationAND != "") {
      this.advancedQuery.operation = this.operationAND;
    } else {
      this.advancedQuery.operation = this.operationOR;
    }
    if(this.title != "" && this.author != "") {
      this.advancedQuery.field1 = "title";
      this.advancedQuery.value1 = this.title;

      this.advancedQuery.field2 = "author";
      this.advancedQuery.value2 = this.author;
    } 
    if(this.title != "" && this.keywords != "") {
      this.advancedQuery.field1 = "title";
      this.advancedQuery.value1 = this.title;

      this.advancedQuery.field2 = "keywords";
      this.advancedQuery.value2 = this.keywords;
    } 
    if(this.title != "" && this.text != "") {
      this.advancedQuery.field1 = "title";
      this.advancedQuery.value1 = this.title;

      this.advancedQuery.field2 = "text";
      this.advancedQuery.value2 = this.text;
    } 
    if(this.keywords != "" && this.text != "") {
      this.advancedQuery.field1 = "keywords";
      this.advancedQuery.value1 = this.keywords;

      this.advancedQuery.field2 = "text";
      this.advancedQuery.value2 = this.text;
    } 
    if(this.author != "" && this.text != "") {
      this.advancedQuery.field1 = "author";
      this.advancedQuery.value1 = this.author;

      this.advancedQuery.field2 = "text";
      this.advancedQuery.value2 = this.text;
    } 
    /*if(this.author != "") {
      if(this.advancedQuery.field1 == "") {
        this.advancedQuery.field1 = "author";
        this.advancedQuery.value1 = this.author;
      } else {
        this.advancedQuery.field2 = "author";
        this.advancedQuery.value2 = this.author;
      }
    }
    if(this.keywords != "") {
      if(this.advancedQuery.field1 == "") {
        this.advancedQuery.field1 = "keywords";
        this.advancedQuery.value1 = this.keywords;
      }
      else if(this.advancedQuery.field1 != "") {
        this.advancedQuery.field2 = "keywords";
        this.advancedQuery.value2 = this.keywords;
      }
    }
    if(this.text != null) {
      if(this.advancedQuery.field1 == "") {
        this.advancedQuery.field1 = "text";
        this.advancedQuery.value1 = this.text;
      }
      else {
        this.advancedQuery.field2 = "text";
        this.advancedQuery.value2 = this.text;
      }
    }*/
    console.log(this.advancedQuery);
    this.searchService.searchBoolean(this.advancedQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByTitleTerm() {
    this.simpleQuery.field = "title";
    this.simpleQuery.value = this.title;
    this.searchService.searchTerm(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByTitleFuzzy() {
    this.simpleQuery.field = "title";
    this.simpleQuery.value = this.title;
    this.searchService.searchFuzzy(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByTitlePhrase() {
    this.simpleQuery.field = "title";
    this.simpleQuery.value = this.title;
    this.searchService.searchPhrase(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }

  public searchByAuthorTerm() {
    this.simpleQuery.field = "author";
    this.simpleQuery.value = this.author;
    this.searchService.searchTerm(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByAuthorFuzzy() {
    this.simpleQuery.field = "author";
    this.simpleQuery.value = this.author;
    this.searchService.searchFuzzy(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByAuthorPhrase() {
    this.simpleQuery.field = "author";
    this.simpleQuery.value = this.author;
    this.searchService.searchPhrase(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByKeywordsTerm() {
    this.simpleQuery.field = "keywords";
    this.simpleQuery.value = this.keywords;
    this.searchService.searchTerm(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByKeywordsFuzzy() {
    this.simpleQuery.field = "keywords";
    this.simpleQuery.value = this.keywords;
    this.searchService.searchFuzzy(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByKeywordsPhrase() {
    this.simpleQuery.field = "keywords";
    this.simpleQuery.value = this.keywords;
    this.searchService.searchPhrase(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
  public searchByLanguageTerm() {
    this.simpleQuery.field = "languageName";
    this.simpleQuery.value = this.languageName;
    this.searchService.searchTerm(this.simpleQuery)
      .subscribe(
        data => {
          this.books = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }
}
