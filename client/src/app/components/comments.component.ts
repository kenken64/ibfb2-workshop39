import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MarvelCharService } from '../services/marvel-char.service';
import { Subscription } from 'rxjs';
import { Comment } from '../model/Comment';
@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit, OnDestroy{

  form!: FormGroup;
  queryParanms$!: Subscription;
  charParam!: any;
  charName!: string;
  charId!: string;

  constructor(private activatedRoute: ActivatedRoute, 
        private formBld: FormBuilder, private marvelApiSvc: MarvelCharService,
          private router:Router){

        }

  ngOnDestroy(): void {
    this.queryParanms$.unsubscribe();
  }

  ngOnInit(): void {
      this.form = this.createForm();
      this.queryParanms$ = this.activatedRoute.queryParams.subscribe(
        (queryParams)=> {
          this.charParam = queryParams['charParam'].split('|');
          this.charName = this.charParam[0];
          this.charId = this.charParam[1];
        }
      )
  }

  saveComment(){
    const commentVal = this.form?.value['comment'];
    const c = {} as Comment;
    c.comment = commentVal;
    c.charId = this.charId;
    this.marvelApiSvc.saveComment(c);
    this.backtoCharDetails();
  }

  backtoCharDetails(){
    this.router.navigate(['/details', this.charId]);
  }

  private createForm(): FormGroup {
    return this.formBld.group({
      comment: this.formBld.control(''),
    })
  }
}
