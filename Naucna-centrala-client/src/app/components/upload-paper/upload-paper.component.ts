import { Component, OnInit } from '@angular/core';
import { PublicationService } from '../../services/publication/publication.service';
import { NotificationsService } from 'angular2-notifications';
import { naucnaOblast } from '../../model/naucnaOblast';
import { Casopis } from '../../model/Casopis';
import { Autor } from '../../model/Autor';
import { areAllEquivalent } from '@angular/compiler/src/output/output_ast';
import { SearchService } from '../../services/search/search.service';
import { Paper } from '../../model/Paper';

@Component({
  selector: 'app-upload-paper',
  templateUrl: './upload-paper.component.html',
  styleUrls: ['./upload-paper.component.css']
})
export class UploadPaperComponent implements OnInit {

  options: string[];
  no: naucnaOblast;
  naucnaOblast: typeof naucnaOblast = naucnaOblast;

  options2: string[];
  casopis: Casopis;
  Casopis: typeof Casopis = Casopis;

  rad: any;
  naslovRada: string;
  apstrakt: string;
  nazivCasopisa: string;
  autori: Autor[] = [];
  keywords: string;
  imeAutora: any;
  prezimeAutora: any;
  paper: Paper = new Paper();
  constructor(private taskService: PublicationService, private notificationService: NotificationsService, private searchService: SearchService) { }

  ngOnInit() {

    const x = naucnaOblast;
    const options = Object.keys(naucnaOblast);
    this.options = options.slice(options.length / 2);
    //console.log(this.options);
    const x2 = Casopis;
    const options2 = Object.keys(Casopis);
    this.options2 = options2.slice(options2.length / 2);
  }

  localUrl: any[];
  fileList: FileList;
  radic:any;
  uploadFile(event: any) {


    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
      //console.log(file);
      this.taskService.upload(file)
        .subscribe(data => {
          console.log(data);
          this.radic = data.filename;
          //this.book.title = data.title.toString();
          //this.book.author = data.author.toString();
          //this.book.filename = data.filename.toString();
          //this.book.keywords = data.keywords.toString();
          //this.book.publicationYear = data.publicationYear.toString();
        });
    }
  }


  upload() {
    console.log(this.rad);
    this.paper.naslovRada = this.naslovRada;
    this.paper.apstrakt = this.apstrakt;
    this.paper.autori = this.autori;
    this.paper.nazivCasopisa = this.nazivCasopisa;
    this.paper.rad = this.radic;
    this.paper.keywords = this.keywords;
    this.paper.naucnaOblast = this.naucnaOblast;

    this.searchService.save(this.paper)
      .subscribe(
        data => {
          console.log(data);
          this.notificationService.info("Uspesno ste dodali clanak!")
        },
        error => {
          console.log(error);
        }
      );

  }


addAuthor() {
  let aa = new Autor();
  aa.imeAutora = this.imeAutora;
  aa.prezimeAutora = this.prezimeAutora;
  this.autori.push(aa);
  this.imeAutora = "";
  this.prezimeAutora = "";
}
}
