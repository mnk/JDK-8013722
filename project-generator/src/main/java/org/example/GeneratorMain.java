package org.example;

import org.apache.maven.model.Parent;
import org.apache.maven.project.MavenProject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeneratorMain {
    public static void main(String[] args) throws IOException {
        String pomFileName = "pom.xml";
        String topArtifactId = "test-project";
        Path topDir = Path.of(topArtifactId);
        String groupId = "org.example.generated";
        MavenProject topMavenProject = new MavenProject();
        topMavenProject.setModelVersion("4.0.0");
        topMavenProject.setGroupId(groupId);
        topMavenProject.setArtifactId(topArtifactId);
        topMavenProject.setPackaging("pom");
        Parent topParent = new Parent();
        topParent.setGroupId("org.example");
        topParent.setArtifactId("stream-closed-test");
        topParent.setVersion("1.0-SNAPSHOT");
        topMavenProject.getModel().setParent(topParent);
        topMavenProject.setVersion(null);
        Parent moduleParent = new Parent();
        moduleParent.setGroupId(topMavenProject.getGroupId());
        moduleParent.setArtifactId(topMavenProject.getArtifactId());
        moduleParent.setVersion(topMavenProject.getVersion());
        for (int i = 0; i < 100; i++) {
            String moduleArtifactId = "module" + i;
            Path moduleDir = topDir.resolve(moduleArtifactId);
            Files.createDirectories(moduleDir);
            MavenProject moduleMavenProject = new MavenProject();
            moduleMavenProject.setModelVersion("4.0.0");
            moduleMavenProject.setArtifactId(moduleArtifactId);
            moduleMavenProject.getModel().setParent(moduleParent);
            moduleMavenProject.getModel().setGroupId(null);
            moduleMavenProject.getModel().setVersion(null);
            moduleMavenProject.writeModel(new FileWriter(moduleDir.resolve(pomFileName).toFile()));
            Path moduleMarker = moduleDir.resolve("module-marker.txt");
            if (!Files.exists(moduleMarker)) {
                Files.createFile(moduleMarker);
            }
            topMavenProject.getModules().add(moduleArtifactId);
        }
        topMavenProject.writeModel(new FileWriter(topDir.resolve(pomFileName).toFile()));
    }
}