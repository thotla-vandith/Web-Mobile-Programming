// TypeScript File for Book Edit Component
import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {ApiService} from '../api.service';
import {FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})
export class BookEditComponent implements OnInit {
  book = {};
  bookForm: FormGroup;
  isbn = '';
  title = '';
  description = '';
  author = '';
  publisher = '';
  published_year = '';
  edition = '';
  constructor(private router: Router, private route: ActivatedRoute, private api: ApiService, private formBuilder: FormBuilder) {}
  ngOnInit() {
    this.bookForm = this.formBuilder.group({
      'isbn': [null, Validators.required],
      'title': [null, Validators.required],
      'description': [null, Validators.required],
      'author': [null, Validators.required],
      'publisher': [null, Validators.required],
      'published_year': [null, Validators.required],
      'edition': [null, Validators.required]
    });
    this.getBook(this.route.snapshot.params['id']);
  }
  onFormSubmit(form: NgForm) {
    const info = this.route.snapshot.params['id'];
    console.log(form);
    this.api.updateBook(info, form)
      .subscribe(res => {
        this.router.navigate(['/book-details', info]);
      }, (err) => {
        console.log(err);
      });
  }
  getBook(id) {
    this.api.getBook(id).subscribe(details => {
      id = details._id;
      this.bookForm.setValue({
        isbn: details.isbn,
        title: details.title,
        description: details.description,
        author: details.author,
        publisher: details.publisher,
        published_year: details.published_year,
        edition: details.edition,
      });
    });
  }
}
