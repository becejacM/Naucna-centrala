import { Component, OnInit } from '@angular/core';
import { SearchService } from '../../services/search/search.service';
import { ActivatedRoute, Router } from '@angular/router';
import { saveAs } from "file-saver";
import { LoggedUtils } from '../../utils/logged-utils';
import { SimpleQuery } from '../../model/simpleQuery';
@Component({
  selector: 'app-paper',
  templateUrl: './paper.component.html',
  styleUrls: ['./paper.component.css']
})
export class PaperComponent implements OnInit {

  public type: string = "";
  public role: string = "";
  public paperfilename;
  public rad;
  public simpleQuery = new SimpleQuery;
  
  constructor(private searchService: SearchService, private route: ActivatedRoute) {
    this.paperfilename = route.snapshot.params['filename'];
    
   }

  ngOnInit() {
    this.role = LoggedUtils.getRole();
    console.log(this.role);
    console.log(this.paperfilename);
    this.simpleQuery.value = this.paperfilename;
    this.searchService.getById(this.simpleQuery)
    .subscribe(
      data => {
        this.rad = data;
        this.type = this.rad.dostupnost;
        console.log(this.rad);
      },
      error => {
        console.log(error);
      }
    );
  }


  download() {
    this.simpleQuery.value = this.paperfilename;    
    this.searchService.download(this.simpleQuery)
      .subscribe(
        data => {
          this.downloadFile(data, this.rad.filename);
        },
        error => {
          console.log(error);
        }
      );
  }

  downloadFile(data, fileName) {
    var blob = new Blob([data], { type: 'application/pdf' });
    saveAs(blob, fileName);
  }
}
