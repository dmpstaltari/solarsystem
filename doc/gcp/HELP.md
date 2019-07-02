# Deploy steps

mvn package docker:build

gcloud auth login

gcloud init

docker tag ml/solarsystem gcr.io/solarsystemweather-245500/solarsystem:1.0.0

gcloud docker -- push gcr.io/solarsystemweather-245500/solarsystem:1.0.0

gcloud app deploy app.yaml -v v1

# Target URL

http://solarsystemweather-245500.appspot.com/solarsystem

