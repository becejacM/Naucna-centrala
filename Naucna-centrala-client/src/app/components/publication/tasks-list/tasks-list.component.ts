import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-tasks-list',
  templateUrl: './tasks-list.component.html',
  styleUrls: ['./tasks-list.component.css']
})
export class TasksListComponent implements OnInit {

  @Input()
  tasks: any[];

  activeIndex = -1;

  constructor() { }

  ngOnInit() {
  }

  updateActiveIndex(index: number) {
    console.log(this.activeIndex);
    this.activeIndex = index;
  }

}
