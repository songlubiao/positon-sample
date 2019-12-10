coding test for position:
This project is a demo to show position from file or input

build:
mvn clean install -DskipTests

run:
java -jar target/position-1.0-SNAPSHOT.jar [filepath]




Assumption:
position data set in position.data file
exchange rate compared to USD is set at file currencyRate

user can set file path param when startup
    eg:  java -jar target/position-1.0-SNAPSHOT.jar position.data
and input position data when running ,with currency code and position in one line like "CNY 111" ,press enter;
It will print exception when type format is not correct,and you can continue input.


you can exit with type "quit"


