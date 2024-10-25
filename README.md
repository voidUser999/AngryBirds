# Angry Birds

An Angry Birds-inspired game developed using LibGDX, generated with the gdx-liftoff tool. This project includes application launchers and configurations for desktop deployment using LWJGL3.

## Prerequisites
Java Development Kit (JDK) 8 or higher Download JDK

Gradle (optional, a Gradle wrapper is included) Install Gradle

## Setup Instructions
### Clone the Repository:

```bash
git clone https://github.com/voidUser999/Prototype.git
cd Prototype
```
### Import the Project:

Open in an IDE that supports Gradle (e.g., IntelliJ IDEA or Eclipse).
Import as a Gradle project to resolve dependencies automatically.

### Build the Project:

```bash
./gradlew build
```
### Run the Game:
Desktop (LWJGL3)
```bash
./gradlew lwjgl3:run
```
## Project Structure

core: Contains shared application logic and game assets.

lwjgl3: Configures and launches the desktop version using LWJGL3.

