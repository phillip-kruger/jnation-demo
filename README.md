# JNation GraphQL Demo

##  Existing REST Application
  * Show server and Swagger UI
    * `GET` /movie/search/{keyword} with "Godfather" 
    * `GET` /movie/{title} with "Top Gun: Maverick"
    * `GET` /movie/cast/{id} with "tt1745960"
  * Show client
    * `mprest -o search`
    * `mprest -o get`

## Convert to GraphQL

Convert the server + first source

### Basic

      ```
        { 
          searchMovies(keyword:"The Godfather") {
            id
            title
          }
        }
      ```
and 

      ```
        {
            movie(title:"Top Gun: Maverick"){
              title:fullTitle
              image
              genres
            }
          }
     ```         
    * Show dynamic client:
      * `GET` /graphql/search/{keyword} with "The Godfather"
    * Show typesafe client:
      * `GET` /graphql/{title} with "Top Gun: Maverick"

### Add source

    ```
         {
            movie(title:"Top Gun: Maverick"){
              title:fullTitle
              image
              genres
              castMembers {
                actors {
                  name
                  image
                  asCharacter
                }
              }
            }
          }
    ```

#### Add deeper level source

    ```
        public List<Actor> getMainActors(@Source CastMembers castMembers, int limit){
            return castMembers.getActors().subList(0, limit);
        }
    ```
    
Then query

    ```
          {
            movie(title:"Top Gun: Maverick"){
              title:fullTitle
              image
              genres
              castMembers {
                mainActors(limit:3) {
                  name
                  image
                  asCharacter
                }
              }
            }
          }
    ```

  * Show source with typesafe client
    * `graphql -o mainActors`

## Multiple queries in one request

Query 2 movies

      ```
        {
          topgun:movie(title:"Top Gun: Maverick"){
            title:fullTitle
            image
            genres
            castMembers {
              mainActors(limit:3) {
                name
                asCharacter
              }
            }
          }

          godfather:movie(title:"The Godfather"){
            title:fullTitle
            image
            genres
            castMembers {
              mainActors(limit:3) {
                name
                asCharacter
              }
            }
          }
        }
      ```

## Map support

Add Map of ratings

      ```
        public Map<Reviewer,Double> getRatings(@Source Movie movie){
            return movieService.getMovieRatings(movie.getId());
        }
      ```

      ```
        {
          topgun:movie(title:"Top Gun: Maverick"){
            title:fullTitle
            image
            genres
            ratings{
              key
              value
            }
          }
        }
      ```

or per key:

      
      ```
      {
        topgun:movie(title:"Top Gun: Maverick"){
          title:fullTitle
          image
          genres
          ratings(key:rottenTomatoes){
            rottenTomatoes:value
          }
        }
      }
      ```

  * Explain Adapter (AdaptWith and AdaptTo) - should we demo this ?

## Mutation

    ```
        @Mutation
        public Map<Reviewer,Double> rate(String id, Double rating){
            movieService.rate(id, rating);
            return movieService.getMovieRatings(id);
        }
    ```

    ```
        mutation rate {
          rate(id: "tt1745960", rating: 7.0){
            key
            value
          }
        }
    ```

  * Show typesafe client (serves as example for both Map and Mutation)
    * `graphql -o rate`

## Subscriptions
  
      ```
        @Subscription
        public Multi<Rating> listenForRateChanges(){
            return movieService.ratingChangedListener();
        }
      ```

      ```
        subscription listenForRatings {
          listenForRateChanges{
            id
            rating
          }
        }      
      ```

and

      ```
        mutation rate {
          rate(id: "tt1745960", rating: 9.9){
            key
            value
          }
        }
      ```

  * Show client(s)
    * `graphql -o listen`
    * On the server side, call the `rate` mutation

## Security
  
* `@RolesAllowed("admin")` on rate method
* also add `oidc` in pom
* Show failure
* Authenticate and show success
* Show client(s)

## Error Handling
### Schema Validation and Bean validation

Add the Bean validation in the rate method:

    ```
    @DecimalMin(value = "0.0", message = "Rating too low")
    @DecimalMax(value = "10.0", message = "Rating too high")
    ```

Run with valid and invalid input

   ```
   mutation rateMovie{
    rate(id:"tt0137523",rating:11.3, key:quarkus){
      key
      value
    }
  }
  ```

### Partial responses

* Add `@RolesAllowed("user")` to the getReviews method
* Run without authentication

## Non-blocking

Add reviews source Uni
  
      ```
        public Uni<Reviews> getReviews(@Source Movie movie){
          System.out.println(">>>>>>> REVIEW :" + Thread.currentThread().getName());
          return movieService.getReviews(movie.getId());
        }
      ```

Also Add `Sys.out` in Movie


    ```
      {
        movie(title:"Top Gun: Maverick"){
          fullTitle
          reviews {
            review{
              title
              content
            }
          }
        }
      }
    ```

  * Demo mix of worker and eventloop
  * Talk about `@NonBlocking`
  * Show client(s) ?

## Other:

* Context
* Batch
