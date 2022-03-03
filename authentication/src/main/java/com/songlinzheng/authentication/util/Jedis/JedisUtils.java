package com.songlinzheng.authentication.util.Jedis;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class JedisUtils {
    @Value("#{'${spring.redis.host}'.split(',')}")
    @Getter
    @Setter
    private List<String> host;

    @Getter
    @Setter
    @Value("#{'${spring.redis.port}'.split(',')}")
    private List<String> port;

    @Value("${spring.redis.databaseName}")
    private String database;

    private JedisCluster jedis;


    public JedisCluster getJedis() {
        if (jedis == null) {
            Set<HostAndPort> jedisClusterNodes = new HashSet<>();
            for (int i = 0; i < host.size() && i < port.size(); i++) {
                try {
                    jedisClusterNodes.add(new HostAndPort(host.get(i), Integer.parseInt(port.get(i))));
                } catch (Exception e){
                    log.warn("Wrong configuration of redis host and port");
                }
            }
            jedis = new JedisCluster(jedisClusterNodes);
        }
        return jedis;
    }

    public long addRefreshToken(String refreshToken) throws Exception{
        return getJedis().sadd(database, refreshToken);
    }

    public long removeRefreshToken(String refreshToken) throws Exception{
        return getJedis().srem(database, refreshToken);
    }

    public boolean containsRefreshToken(String refreshToken) throws Exception{
        return getJedis().sismember(database, refreshToken);
    }

}
