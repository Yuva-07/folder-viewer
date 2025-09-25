**How to run in Eclipse**

Import project: File → Import → Maven → Existing Maven Projects.

Run: Right-click FolderViewerApplication.java present inside(folder-viewer/src/main/java/com.example.folderviewer) → Run As → Java Application.

Open browser: http://localhost:8080

Enter a folder path → Browse files

**Running the Application with Docker**
Make sure Docker Desktop is running, Apache Maven installed and Set Environment Variables.
Verify Installation
Open a new Command Prompt :
Run:
mvn -v

1. Build the Docker Image

Make sure you are in the **root directory** where the Dockerfile is located:

docker build -t folder-viewer .


-t folder-viewer tags the image with the name folder-viewer.

. refers to the current directory containing the Dockerfile.

2. Verify the Docker Image
docker images


You should see folder-viewer listed.

3. Run the Docker Container
docker run -p 8080:8080 folder-viewer


-p 8080:8080 maps port 8080 of the container to port 8080 on your host machine.

4. Access the Application

Open your browser and go to http://localhost:8080.

Notes

Make sure the JAR file name in the Dockerfile matches the actual name in the target/ directory.

The application is platform-independent and can run on any OS using Docker.

**MAVEN COMMANDS TO RUN THE APPLICATION**

Navigate to your project directory
If your Spring Boot project is in C:\Users\Yogesh P\folder-viewer, then do:

cd "C:\Users\folder-viewer"


Check that pom.xml exists:

dir


You should see pom.xml listed.

Run Maven commands from the project directory:

mvn clean install
mvn spring-boot:run
