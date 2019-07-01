# Solar System WP
Weather predictor for solar systems

## Create image

mvn package docker:build


## Run solar system

docker run -d -p 8080:8080 --name solarsystem ml/solarsystem:0.0.1-SNAPSHOT
