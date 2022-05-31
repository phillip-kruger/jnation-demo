# JNation GraphQL Demo

* Existing REST Application
  * Show server and Swagger UI
    * `GET` /movie/{title} with "Top Gun: Maverick"
    * `GET` /movie/cast/{id} with "tt1745960"
  * Show client(s)
    *
    *

* Convert to GraphQL
  * Convert the server + first source
    * Basic"

      ```
        {
            movie(title:"Top Gun: Maverick"){
              title:fullTitle
              image
              genres
            }
          }
      ```
    * Add source

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
  * Show client(s)
  * Add deeper level source
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

  * Show client(s)

* Multiple queries in one request
  * Query 2 movies
    * Add Another movie
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
  * Show client(s)

* Map support
  * Add Map of ratings
    * Java:
      ```
        public Map<Reviewer,Double> getRatings(@Source Movie movie){
            return movieService.getMovieRatings(movie.getId());
        }
      ```
    * GraphQL:
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
  * Show client(s)

* Mutation
  * Add rate method
    * Java:
        ```
        @Mutation
        public Map<Reviewer,Double> rate(String id, Double rating){
            movieService.rate(id, rating);
            return movieService.getMovieRatings(id);
        }
        ```
    * GraphQL:
        ```
            mutation rate {
              rate(id: "tt1745960", rating: 7.0){
                key
                value
              }
            }
        ```
  * Show client(s)

* Subscriptions
  * Add listenForRatingChange method
    * Java:
      ```
        @Subscription
        public Multi<Rating> listenForRateChanges(){
            return movieService.ratingChangedListener();
        }
      ```
    * GraphQL:
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

* Security
  * Secure the rate method
    * `@RolesAllowed("admin")` on rate method
    * also add `oidc` in pom
  * Show failure
  * Authenticate and show success
  * Show client(s)

* Non-blocking
  * Add reviews source Uni
    * Java:
      ```
        public Uni<Reviews> getReviews(@Source Movie movie){
          System.out.println(">>>>>>> REVIEW :" + Thread.currentThread().getName());
          return movieService.getReviews(movie.getId());
        }
      ```
     Also Add `Sys.out` in Movie

    * GraphQL
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

TODO:
Error Handling (with vaidation ?) and partial errors
Context ?
??