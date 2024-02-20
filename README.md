# crud-vuejs-spring-boot

<!-- Overview -->

## Develop

#### DataBase
```
cd backend

#run db
mvn docker:run -Pdocker

#stop db
mvn docker:stop -Pdocker
```

#### Backend
```
cd backend

#run backend
mvn spring-boot:run

#run tests
mvn clean verify -Pdocker
```

#### Frontend
```
cd frontend

#run frontend
npm run dev
```

## Run in Docker

#### Build Backend
```
cd backend
mvn clean install -Pdocker
```

#### Build Frontend
```
cd frontend
npm run build-docker
```

#### Run Docker-compose
```
docker-compose up -d
```

#### Open Browser
```
http://localhost:80/ 
```

## Used Technologies
- Spring Boot
- Spring Data JPA
- Vue 3
- Vite
- CoreUI

## Links