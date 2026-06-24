# Start a new spring-petclinic container
docker stop spring-petclinic 2> $null
docker rm spring-petclinic 2> $null
docker run -d --name spring-petclinic -p 8080:8080 docker.io/library/spring-petclinic:4.0.0-SNAPSHOT
