package sg.edu.nus.workshop39.server.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // value redis host from appln.properties
    @Value("${spring.data.redis.host}")
    private String redisHost;

    // value redis port from appln.properties
    @Value("${spring.data.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    // define the return redis template bean as single Object
    // throughout the runtime.
    // Return the RedisTemplate
    @Bean
    @Scope("singleton")
    public RedisTemplate<String, String> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        System.out.println("redisHost " + redisHost);
        System.out.println("redisPort " + redisPort.get());
        System.out.println("redisUsername " + redisUsername);
        System.out.println("redisPassword " + redisPassword);
        System.out.println("redisUsername " + !redisUsername.isEmpty());
        System.out.println("redisPassword " + !redisPassword.isEmpty());
        
        config.setHostName(redisHost);
        config.setPort(redisPort.get());

        if (!redisUsername.isEmpty() && !redisPassword.isEmpty()) {
            System.out.println("not seting username and password ");
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
        }
        config.setDatabase(0);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration
                .builder()
                .build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        // associate with the redis connection
        redisTemplate.setConnectionFactory(jedisFac);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // set the map key/value serialization type to String
        redisTemplate.setHashKeySerializer(redisTemplate.getKeySerializer());
        redisTemplate.setValueSerializer(redisTemplate.getKeySerializer());
        redisTemplate.setHashValueSerializer(redisTemplate.getKeySerializer());

        return redisTemplate;
    }
}