# LesPirates

Jeu de cartes à 2 joueurs (thème pirates), écrit en Java avec [Processing](https://processing.org/) pour l'interface graphique.

## Prérequis

- Java 21 (JDK, pas seulement le JRE)
- Aucun autre outil requis : le projet se compile avec `javac` brut, la seule dépendance externe (`core-3.3.7.jar`, Processing) est déjà incluse dans le dépôt.

## Compiler

Depuis la racine du projet :

```bash
javac -cp core-3.3.7.jar -d bin $(find src -name "*.java")
```

(sous PowerShell : `Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName | javac -cp core-3.3.7.jar -d bin -`)

Ou ouvrez simplement le dossier dans VS Code (extension Java installée) : les configurations de lancement sont déjà prêtes dans `.vscode/launch.json`.

## Jouer en local (hotseat)

Les deux joueurs se relaient sur le même clavier/écran :

```bash
java -cp "bin;core-3.3.7.jar" affichage.InterfaceJeu
```

(remplacer `;` par `:` sous Linux/macOS)

Le jeu demande le nom de chaque joueur au clavier dans le terminal, puis l'interface graphique s'ouvre.

## Jouer en réseau (à distance)

Le jeu supporte aussi un mode client/serveur pour jouer à deux sur des machines différentes.

**1. Un des deux joueurs lance le serveur** (une partie à la fois, port 5000 par défaut) :

```bash
java -cp "bin;core-3.3.7.jar" serveur.ServeurJeu
```

**2. Les deux joueurs lancent le client graphique** :

```bash
java -cp "bin;core-3.3.7.jar" affichage.InterfaceJeu
```

Le client demande l'adresse et le port du serveur, puis un nom. Utiliser `localhost` si tout tourne sur la même machine.

**3. Pour jouer à distance (machines différentes)** : les deux joueurs installent [Tailscale](https://tailscale.com/) (gratuit), se connectent chacun avec leur propre compte, et l'hôte partage sa machine avec l'autre joueur depuis https://login.tailscale.com/admin/machines (menu `...` → `Share`). Le joueur qui rejoint utilise ensuite l'adresse Tailscale de l'hôte (`tailscale ip -4`, du type `100.x.y.z`) comme adresse du serveur, port `5000`.

## Structure du projet

- `src/jeu/` — moteur de jeu (règles, cartes, joueurs), indépendant de l'affichage et du réseau
- `src/affichage/` — interface graphique Processing (`InterfaceJeu`)
- `src/reseau/` — protocole réseau et connexion côté client
- `src/serveur/` — serveur faisant autorité pour le mode réseau
