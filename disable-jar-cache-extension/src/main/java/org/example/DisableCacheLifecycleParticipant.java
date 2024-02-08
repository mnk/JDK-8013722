package org.example;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;

import javax.inject.Named;
import javax.inject.Singleton;
import java.net.URLConnection;

@Named( "workaround")
@Singleton
public class DisableCacheLifecycleParticipant extends AbstractMavenLifecycleParticipant {
    @Override
    public void afterSessionStart(MavenSession session) throws MavenExecutionException {
        URLConnection.setDefaultUseCaches("jar", false);
    }
}
