package hemmouda.counter;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MongoConfig {

    @Bean
    public MongodConfig mongodConfig() {
        return MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net("localhost", 27017, false))
                .replication(new Storage("./data/mongodb", null, 0))
                .build();
    }

    @Bean(destroyMethod = "stop")
    public MongodExecutable embeddedMongoServer() {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        return starter.prepare(mongodConfig());
    }

    @Bean(destroyMethod = "stop")
    public MongodProcess mongodProcess(MongodExecutable mongodExecutable) throws IOException {
        return mongodExecutable.start();
    }

}
