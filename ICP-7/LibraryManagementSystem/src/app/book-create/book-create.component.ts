// Typescript to create a new book (Book create component)
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ApiService} from '../api.service';
import {FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
@Component({
  selector: 'app-book-create',
  templateUrl: './book-create.component.html',
  styleUrls: ['./book-create.component.css']
})
export class BookCreateComponent implements OnInit {
  bookForm: FormGroup;
  isbn = ''; title = '';  description = '';
  author = '';  publisher = '';
  published_year = '';  edition: string = '';
  constructor(private router: Router, private api: ApiService, private formBuilder: FormBuilder) {
  }
  ngOnInit() {
    this.bookForm = this.formBuilder.group({
      'isbn': [null, Validators.required],
      'title': [null, Validators.required],
      'description': [null, Validators.required],
      'author': [null, Validators.required],
      'publisher': [null, Validators.required],
      'edition': [null, Validators.required],
      'published_year': [null, Validators.required]
    });
  }
  onFormSubmit(form: NgForm) {
    this.api.postBook(form)
      .subscribe(res => {
        const info = res['_id'];
        this.router.navigate(['/book-details', info]);
      }, (err) => {
        console.log(err);
      });
  }
}
