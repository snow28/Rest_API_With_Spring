1. application.properties -> server.port=8090

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
2. I used H2 relational in-memory database written in Java.

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
3.

Spring MVC:
Client Request   -> My RestControllerClass                  ->   MyService

Sprint Data Rest:
Client Request   -> Spring Data REST(Framework Generated)   ->   My Spring Data Repository

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
4. Auto-generated methods + how they could be implemented with Spring MVC:

CREATE:
    HTTP POST /events {<request body>}
        Event ev = new Event();
        ... set attributes ...
        EventRepository.save(ev);
READ:
    HTTP GET /events
        EventRepository.findAll()
    HTTP GET /events/<code>
        EventRepository.findById(String code)
UPDATE:
    HTTP PUT or PATCH /events/<code> {<request body>}
        Event ev = EventRepository.findById(String code);
        ... set attributes ...
        EventRepository.save(ev);
DELETE:
    HTTP DELETE /events/<code>
        EventRepository.deleteById(String code)


- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
5. data.json should be placed near .jar file, it's provided in root folder.

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
6. To make calls from swagger-ui we have to specify url where UI will be run, this can be done by adding following
 line to the repository declaration:
 -> @CrossOrigin(origins = "http://localhost:8082")

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
