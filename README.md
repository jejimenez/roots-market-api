# DE RAÍZ # 
# ROOTS-MARKET-API #

### Maven profiles

To build the project:

    mvn clean package

To build the project without tests:

    mvn clean package -DskipTests

To build the project whit a specific profile:

    mvn clean package -DskipTests -Plocal   
        
To run the project:

    mvn spring-boot:run

### Creating a Docker container

##### 1. Check the last image and delete it:
    
    docker images
    docker rmi <<image-id>>
    
##### 2. Create the image from maven and save it to tar.gz:

    mvn dockerfile:build
    docker save roots/roots-market-api | gzip > roots-market-api.tar.gz

### Installing a Docker container
    
##### 1. Stop and delete the old container:

    docker ps -a
    docker stop <<container-id>>  
    docker rm <<container-id>>

##### 2. Delete the container image:

    docker images
    docker rmi <<image-id>>  
        
##### 3. Load the image to the docker server:

    zcat roots-market-api.tar.gz | docker load  

##### 4. Run the container using docker-compose:

    cd /opt/docker/
    docker-compose up -d
      
##### 5. Check that everything is ok:

    docker logs roots-market-api --tail 300
