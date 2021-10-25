# kanban

## Project setup

```
npm install
```

### Compiles and hot-reloads for development

```
npm run serve
```

### Compiles and minifies for production

```
npm run build
```

# Dockerize

## Create Docker Image

```
docker build -t bocekanban.frontend -f ./Dockerfile.frontend ../
```

```
docker build -t bocekanban.backend -f ./Dockerfile.backend ../backend
```

## Create Docker Container

```
docker run --name frontend -p 80:80 -d bocekanban.frontend
```

```
docker run --name backend -p 8080:8080 -d bocekanban.backend
```

## Docker Compose

```
docker-compose up --force-recreate --build
```

## Deploy Heroku

### Backend

```
docker buildx build --platform linux/amd64 -t registry.heroku.com/bocekanban-api/web -f ./Dockerfile.backend ../backend
docker push registry.heroku.com/bocekanban-api/web
heroku container:release web -a bocekanban-api
```

### Backend

```
docker buildx build --platform linux/amd64 -t registry.heroku.com/bocekanban/web -f ./Dockerfile.frontend ../
docker push registry.heroku.com/bocekanban/web
heroku container:release web -a bocekanban
```
