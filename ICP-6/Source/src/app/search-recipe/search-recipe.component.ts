//TypeScript file for ICP-6
import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from './../../environments/environment';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipeValue: any;
  placeValue: any;
  venueList = [];
  recipeList = [];

  currentLat: any;
  currentLong: any;
  geolocationPosition: any;
  //Acquiring the API keys from the environment file
  private App_Id = environment.App_ID_Edamam;
  private App_Key = environment.App_Key_Edamam;
  private Client_Id = environment.Client_ID_Foursq;
  private Client_Key = environment.Client_Secret_FourSq;
  private url_recipe = `https://api.edamam.com/search?app_id=${this.App_Id}&app_key=${this.App_Key}`;
  private url_places = `https://api.foursquare.com/v2/venues/search?client_id=${this.Client_Id}&client_secret=${this.Client_Key}&v=20180323`;
  constructor(private _http: HttpClient) {
  }

  ngOnInit() {

    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;

    if (this.recipeValue !== null) {
      this.getRecipeData();
    }

    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      this.getPlaceData();
    }
  }
  //Function to get the receipe details by using get http
  getRecipeData() {
    this.recipeList = [];
    const url = this.url_recipe + '&q=' + this.recipeValue;
    this._http.get(url).subscribe(info => {
      console.log(info);
      const output = info['hits'];
      output.forEach(output => {
        this.recipeList.push({
          name: output['recipe'].labels,
          url : output['recipe'].url,
          logo: output['recipe'].image
        });
      });
    });
  }
  //Function to get the Restaurant details by using get http
  getPlaceData() {
    this.venueList = [];
    const url_api = this.url_places + '&near=' + this.placeValue + '&query=' + this.recipeValue;
    this._http.get(url_api).subscribe(info => {
      const places = info['response']['venues'];
      places.forEach(places => {
        this.venueList.push({
          name : places['name'],
          location : {
            formattedAddress: [places['location']['address'], places['location']['city'], places['location']['country']]
          }
        });
      });
    });
  }
}
