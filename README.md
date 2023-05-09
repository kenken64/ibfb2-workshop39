## Docker

```

docker ps -a
```

```
docker rmi <image id>
```

```
docker rm <contain id>
```

```
docker system prune -a
```

```
docker images -a | grep "pattern" | awk '{print $3}' | xargs docker rmi
```

```
docker rmi $(docker images -a -q)
```

```
docker rm $(docker ps -a -f status=exited -q)
```


### Server 

```
docker buildx create --name mybuilder
```

```
docker buildx use mybuilder
```

```
docker buildx ls
```

Building mongodb
```
docker buildx --builder mybuilder build .
```

Docker compose UP and build

```
docker compose up -d  --build
```


Docker compose down

```
docker compose down
```
