# Solar System WP
Weather predictor for solar systems

## API documentation

* http://solarsystemweather-245500.appspot.com/swagger-ui.html
* http://solarsystemweather-245500.appspot.com/v2/api-docs

## Local deploy steps

* mvn package docker:build
* docker run -d -p 8080:8080 --name solarsystem ml/solarsystem:0.0.1-SNAPSHOT

### Endpoint

* <http://local:8080/solarsytem>

## GCP deploy steps

* mvn package docker:build
* gcloud auth login
* gcloud init
* docker tag ml/solarsystem gcr.io/solarsystemweather-245500/solarsystem:1.0.0
* gcloud docker -- push gcr.io/solarsystemweather-245500/solarsystem:1.0.0
* gcloud app deploy app.yaml -v v1 

### Endpoint

* <http://solarsystemweather-245500.appspot.com/solarsytem>

