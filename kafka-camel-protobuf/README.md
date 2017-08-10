# Instructions To run this code

1. Create a docker network for the docker instances
    
    ```bash
    $ docker network create kafka_docker_local -d bridge
    ```
2. Run the kafka instances locally
    
    ```bash
    $ cd kafka-docker
    $ docker-compose -f docker-compose-single-broker.yml up -d
    ```

3. Edit your `/etc/hosts` file by adding the following line

    ```bash
    # Docker kafka issues
    127.0.0.1       localhost kafka
    ```

2. Go into `kafka-consumer` directory and build the jar

    ```bash
    $ cd kafka-consumer && ./gradlew clean assemble
    
    # build the docker image 
    $ docker build -t rnkoaa/kafka-consumer .
 
    # step back to the main directory
    $ cd ..
    ```

3. Go into `kafka-produer` directory and build the jar
       
    ```bash
    $ cd kafka-producer && ./gradlew clean assemble
    
    # build the docker image 
    $ docker build -t rnkoaa/kafka-producer .
    
    # step back to the main directory
    $ cd ..
    ```
    
4. Now run both containers using docker-compose
    
    ```bash
    $ cd tools/docker
    
    $ docker-compose up -d
    ```

