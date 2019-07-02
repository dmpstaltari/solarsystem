# Solar System WP
Weather predictor for solar systems

### Local deploy steps

* mvn package docker:build
* docker run -d -p 8080:8080 --name solarsystem ml/solarsystem:0.0.1-SNAPSHOT

## GCP deploy steps

* mvn package docker:build
* gcloud auth login
* gcloud init
* docker tag ml/solarsystem gcr.io/solarsystemweather-245500/solarsystem:1.0.0
* gcloud docker -- push gcr.io/solarsystemweather-245500/solarsystem:1.0.0
* gcloud app deploy app.yaml -v v1 

## Endpoints

* <http://local:8080/solarsytem>
* <http://solarsystemweather-245500.appspot.com/solarsytem>
