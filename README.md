# crud-vuejs-spring-boot

## Develop

### DataBase
```
cd backend

#run db
mvn docker:run -Pdocker

#stop db
mvn docker:stop -Pdocker
```

### Backend
```
cd backend

#run backend
mvn spring-boot:run

#run tests
mvn clean verify -Pdocker
```

### Frontend
```
cd frontend

#run frontend
npm run serve
```

## Run in Docker

### Build Backend
```
cd backend
mvn clean install -Pdocker
```

### Build Frontend
```
cd frontend
npm run build-docker
```

### Run Docker-compose
```
docker-compose up -d
```

### Open Browser
```
http://localhost:80/ 
```