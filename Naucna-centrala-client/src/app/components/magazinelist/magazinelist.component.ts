import { Component, OnInit } from '@angular/core';
import { TransactionRequestDto } from '../../model/TransactionRequestDto';
import { SearchService } from '../../services/search/search.service';
import { LoggedUtils } from '../../utils/logged-utils';

@Component({
  selector: 'app-magazinelist',
  templateUrl: './magazinelist.component.html',
  styleUrls: ['./magazinelist.component.css']
})
export class MagazinelistComponent implements OnInit {

  magazines:any[];
  magazinesSubscribed:any[];
  transactionRequestDto : TransactionRequestDto = new TransactionRequestDto;

  constructor(private searchService: SearchService) { }

  ngOnInit() {
    this.getAllForSubscribe();
    this.getAllSubscribed();
  }

  getAllForSubscribe(){
    this.searchService.getMagazinesForSubscribe()
    .subscribe(
      data => {
        console.log(data);
        this.magazines=data;
        
      },
      error => {
        console.log(error);
      }
    );
  }

  getAllSubscribed(){
    this.searchService.getMagazinesSubscribed()
    .subscribe(
      data => {
        console.log(data);
        this.magazinesSubscribed=data;
        
      },
      error => {
        console.log(error);
      }
    );
  }
  url:any;
  pretplatiSe(nazivCasopisa:any){
    this.transactionRequestDto.prodavac=nazivCasopisa;
    this.transactionRequestDto.kupac= LoggedUtils.getUsername();
    this.searchService.pretplatiSe(this.transactionRequestDto)
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

}
