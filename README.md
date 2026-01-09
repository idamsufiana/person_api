# person_api
person api

step running this application

git clone https://github.com/idamsufiana/person_api.git

cd person_api

mvn clean install

mvn spring-boot:run

curl http://localhost:8080/api/person

{

  "gender": "female",
  
  "fullname": "mrs becky campbell",
  
  "address": "1135 w sherman dr greeley",
  
  "picture": "https://randomuser.me/api/portraits/women/45.jpg"
  
}
