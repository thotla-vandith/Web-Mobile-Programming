//TypeScript file for ICP-5
import { Component, OnInit } from '@angular/core';
//Refering the components here
@Component({
  selector: 'app-icp5',
  templateUrl: './icp5.component.html',
  styleUrls: ['./icp5.component.css']
})
//Initializtion
export class TodosComponent implements OnInit {
  public todos = [];
  public countDownTimes;
  public interval;
  public todo = '';
  public time :any;
  constructor() { }
  ngOnInit(): void {
  }
//savToDo function to save the name of the task entered by user
  saveTodo(){
    const todoObject = {
      id: this.todos.length + 1,
      name: this.todo,
      completed: false,
    }
    this.todos.push(todoObject);
    console.log(this.todos)
  }
  //saveTimer function to save the countdown timer
  saveTimer(){
    clearInterval(this.interval);
    this.getCountDown(new Date(this.time));
  }
  countDown(todo){  }
  //To get the Calculated time for the Event Date
  getCountDown(todoTime) {
    this.interval = setInterval(() => {
      const now = new Date().getTime();
      const distance = todoTime.getTime() - now;
      const days = Math.floor(distance / (1000 * 60 * 60 * 24));
      const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
      const seconds = Math.floor((distance % (1000 * 60)) / 1000);
      this.countDownTimes= {
        days,
        hours,
        minutes,
        seconds
      };
    }, 1000);
  }
//To delete the name from the list
  removeTodo(index) {
    this.todos.splice(index,1)
    this.countDownTimes.splice(index,1)
  }
//To mark it as completed but not deleted
  toggleTodoComplete(index) {
    const todo = this.todos[index];
    todo['completed'] = !todo['completed'];
    this.todos[index] = todo;
  }
}
