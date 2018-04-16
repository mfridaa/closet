ps aux | grep closet-0.0.1-SNAPSHOT.jar | awk '{print $2}' | xargs kill -9
#mvn clean
mvn install
java -jar target/closet-0.0.1-SNAPSHOT.jar
