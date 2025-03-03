Pour utiliser l'application:

Placer vous dans le backend:

lancer le build :
./gradlew build

Puis on construit l'image:
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/code-with-quarkus-jvm .

Ensuite on lance le containeur:
docker run -i --rm -p 8080:8080 quarkus/code-with-quarkus-jvm

Votre Backend est prêt!

Enfin, placez vous dans le front et taper la commande:
npm run start