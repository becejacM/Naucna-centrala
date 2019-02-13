import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicationService } from '../../../services/publication/publication.service';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.css']
})
export class SubscribeComponent implements OnInit {

  id: string;
  name: string;
  start: any;
  constructor(private route: ActivatedRoute, private taskService: PublicationService) { }

  ngOnInit() {
    this.start=false;
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
      this.getMagazine();
    })
    
  }

  getMagazine(){
    this.taskService.getMagazineWithTask(this.id).subscribe(response => {
      this.name = response.name;
      console.log(response);
      this.start=true;
      //this.formTaskDetails();
    })
  }
  pretplatiSe(){
    console.log("evo me pretplata");
    this.taskService.subscribe(this.id).subscribe(response => {
      console.log(response);
      this.start=true;
      //this.formTaskDetails();
    })
  }

}
