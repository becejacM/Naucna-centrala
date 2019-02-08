import { Component, OnInit } from '@angular/core';
import { SearchService } from '../../services/search/search.service';
import { SimpleQuery } from '../../model/simpleQuery';
import { AdvancedQuery } from '../../model/advancedQuery';
import { SearchCriteria } from '../../model/searchCriteria';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { SearchType } from '../../model/searchType';
import { isBoolean } from 'util';
import { NotificationsService } from 'angular2-notifications';
import { saveAs } from "file-saver";
import { TransactionRequestDto } from '../../model/TransactionRequestDto';
import {Router} from '@angular/router';
import { LoggedUtils } from '../../utils/logged-utils';

@Component({
  selector: 'app-searchlist',
  templateUrl: './searchlist.component.html',
  styleUrls: ['./searchlist.component.css']
})
export class SearchlistComponent implements OnInit {
  public books = [];
  public booksForDownload=[];
  public field;
  public type;
  public pojam;
  public pojam2;
  public field2;
  
  options: string[];
  searchCriteria: SearchCriteria;
  SearchCriteria: typeof SearchCriteria = SearchCriteria;

  options2: string[];
  searchType: SearchType;
  SearchType: typeof SearchType = SearchType;
  userForm: FormGroup;

  isBoolean: string;

  constructor(private searchService: SearchService, private fb: FormBuilder, private router: Router, private notificationService: NotificationsService) { }

  ngOnInit() {
    this.getAllBooksForBuy();
    this.getAllBooksForDownload();
    //this.createForm();
    const x = SearchCriteria;
    const options = Object.keys(SearchCriteria);
    this.options = options.slice(options.length / 2);
    //console.log(this.options);
    const x2 = SearchType;
    const options2 = Object.keys(SearchType);
    this.options2 = options2.slice(options2.length / 2);
    this.isBoolean="no";
  }

  /*createFormRegisteredPerson() {
    
        
        this.userForm = this.fb.group({
          username: new FormControl('', [Validators.required, Validators.minLength(3)]),
          password: new FormControl('', [Validators.required, Validators.minLength(3)]),
          repeatedPassword: new FormControl('', [Validators.required, Validators.minLength(3)]),
          email: new FormControl('', [Validators.required, Validators.email]),
          firstname: new FormControl('', [Validators.required, Validators.minLength(3)]),
          lastname: new FormControl('', [Validators.required, Validators.minLength(3)]),
          location: new FormControl('', [Validators.required, Validators.minLength(3)]),
          damageType: new FormControl('', [Validators.required]),
        }, { updateOn: 'submit' });
      }*/

  public search(){
    console.log(this.type);
    console.log(this.field);
    if(this.type==""){
      this.notificationService.error('Morate odabrati tip pretrage!');      
    }
    else if(this.field==""){
      this.notificationService.error('Morate odabrati polje pretrage!');      
    }
    else if(this.pojam==""){
      this.notificationService.error('Morate uneti pojam pretrage!');      
    }
    else if(this.isBoolean=="yes" && this.pojam2==""){
      this.notificationService.error('Morate uneti pojam pretrage!');      
    }
    else if(this.isBoolean=="yes" && this.operationAND=="" && this.operationOR==""){
      this.notificationService.error('Morate operator za boolean pretragu!');      
    }
    else{
      if(this.isBoolean=="yes"){
        this.pretraziBoolean();
      }
      else{
        this.pretrazi();
      }
    }
  }

  public pretrazi(){

    this.pripremiFieldove();

    if(this.type==="TERM"){
      if(this.field==="sadrzaj"){
        this.pretraziContent();
      }
      else{
        this.pretraziTerm();        
      }
    }
    else if(this.type==="FUZZY"){
      this.pretraziFuzzy();
    }
    else if(this.type==="PHRASE"){
      this.pretraziFuzzy();
    }
  }

  public pripremiFieldove(){
    this.simpleQuery.value = this.pojam;
    if(this.field==="naziv casopisa"){
      this.simpleQuery.field="nazivCasopisa";
    }
    else if(this.field==="naslov rada"){
      this.simpleQuery.field="naslovRada";
    }
    else if(this.field==="ime autora"){
      this.simpleQuery.field="imeAutora";
    }
    else if(this.field==="prezime autora"){
      this.simpleQuery.field="prezimeAutora";
    }
    else if(this.field==="kljucne reci"){
      this.simpleQuery.field="keywords";
    }
    else if(this.field==="sadrzaj"){
      this.simpleQuery.field="text";
    }
    else if(this.field==="naucna oblast"){
      this.simpleQuery.field="naucnaOblast";
    }
  }

  public pripremiFieldoveZaBoolean(){
    this.advancedQuery.value1 = this.pojam;
    this.advancedQuery.value2 = this.pojam2;
    
    if(this.operationAND != "") {
      this.advancedQuery.operation = "AND";
    } else {
      this.advancedQuery.operation = "OR";
    }

    if(this.field==="naziv casopisa"){
      this.advancedQuery.field1="nazivCasopisa";
    }
    else if(this.field==="naslov rada"){
      this.advancedQuery.field1="naslovRada";
    }
    else if(this.field==="ime autora"){
      this.advancedQuery.field1="imeAutora";
    }
    else if(this.field==="prezime autora"){
      this.advancedQuery.field1="prezimeAutora";
    }
    else if(this.field==="kljucne reci"){
      this.advancedQuery.field1="keywords";
    }
    else if(this.field==="sadrzaj"){
      this.advancedQuery.field1="text";
    }
    else if(this.field==="naucna oblast"){
      this.advancedQuery.field1="naucnaOblast";
    }



    if(this.field2==="naziv casopisa"){
      this.advancedQuery.field2="nazivCasopisa";
    }
    else if(this.field2==="naslov rada"){
      this.advancedQuery.field2="naslovRada";
    }
    else if(this.field2==="ime autora"){
      this.advancedQuery.field2="imeAutora";
    }
    else if(this.field2==="prezime autora"){
      this.advancedQuery.field2="prezimeAutora";
    }
    else if(this.field2==="kljucne reci"){
      this.advancedQuery.field2="keywords";
    }
    else if(this.field2==="sadrzaj"){
      this.advancedQuery.field2="text";
    }
    else if(this.field2==="naucna oblast"){
      this.advancedQuery.field2="naucnaOblast";
    }
  }
  public pretraziContent(){
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
  public pretraziPhrase(){
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

  public pretraziFuzzy(){
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
  public pretraziTerm(){
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
  public pretraziBoolean(){
    this.pripremiFieldoveZaBoolean();
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

  public promenaTipa(value : string){
    console.log("promenaaaa "+SearchType[value]);
    if(SearchType[value]===3){
        this.isBoolean="yes";
    }
    else{
      this.isBoolean="no";
    }
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

  public getAllBooksForDownload() {
    this.searchService.getAllForDownload()
      .subscribe(
        data => {
          this.booksForDownload = data;
          console.log(this.books)
        },
        error => {
          console.log(error);
        }
      );
  }

  public getAllBooksForBuy() {
    this.searchService.getAllForBuy()
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
  download(filename : any) {
    this.simpleQuery.value = filename;    
    this.searchService.download(this.simpleQuery)
      .subscribe(
        data => {
          this.downloadFile(data, filename);
        },
        error => {
          console.log(error);
        }
      );
  }
  uuid : any;
  url : any;
  transactionRequestDto : TransactionRequestDto = new TransactionRequestDto;
  kupi(naslovRada:String, nazivCasopisa:String){
    this.uuid = "49afdb55-0d09-4e87-af2d-d50ea40f5452";
    //this.transactionRequestDto.amount = 10.00;
    //this.transactionRequestDto.description = "lep opis";
    //this.transactionRequestDto.sellerUuid = "5e0f86b8-2f9e-414d-bb63-01164f1ab87a";
    //this.transactionRequestDto.successUrl = "https://www.youtube.com";
    //this.transactionRequestDto.failUrl = "https://www.google.com";
    this.transactionRequestDto.naslovRada = naslovRada;
    this.transactionRequestDto.prodavac = nazivCasopisa;
    this.transactionRequestDto.kupac = LoggedUtils.getUsername();
    this.searchService.kupi(this.transactionRequestDto)
    .subscribe(
      data => {
        console.log(data);
        
        this.url = data.redirectUrl;
        window.location.href=this.url;
        //this.router.navigate([this.url]);
        
      },
      error => {
        console.log(error);
      }
    );

  }

  proveraKupovine(naslovRada:String, nazivCasopisa:String, dostupnost:String):Boolean{
      if(dostupnost==="OPEN_ACCESS"){
        return true;
      }
      else{
        this.transactionRequestDto.naslovRada = naslovRada;
        this.transactionRequestDto.prodavac = nazivCasopisa;
        this.transactionRequestDto.kupac = LoggedUtils.getUsername();
        let flag:any;
        this.searchService.proveriKupovinu(this.transactionRequestDto)
        .subscribe(
          data => {
            console.log(data);
            flag=data.jeKupljen;            
          },
          error => {
            console.log(error);
          }
        );

        if(flag===true){
          return true;
        }
        else{
          return false;
        }
      }

  }
  downloadFile(data, fileName) {
    var blob = new Blob([data], { type: 'application/pdf' });
    saveAs(blob, fileName);
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
