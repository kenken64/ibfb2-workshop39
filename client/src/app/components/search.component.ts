import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit{
    form!: FormGroup;
    charName?: string
    constructor(private formBld: FormBuilder, private router: Router){

    }

    ngOnInit() {
        this.form = this.createForm();
    }

    search(){
      const charName = this.form?.value['charName'];
      this.router.navigate(['/list', charName]);
    }

    createForm(): FormGroup {
      return this.formBld.group({
        charName: this.formBld.control(''),
      });
    }
}
