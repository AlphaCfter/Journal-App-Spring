package com.alphacfter.journalApp.cache;

import com.alphacfter.journalApp.entity.Config;
import com.alphacfter.journalApp.repository.ConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }

    public static Map<String, String> cache;

    @Autowired
    private ConfigRepository configRepository;

    /**
     * Initializes the cache by clearing it and repopulating it with data from the database.
     *
     * <p>This method clears the existing cache and then queries the database for all configuration
     * entries, which are subsequently stored in the cache. This ensures that the cache contains the
     * most up-to-date configuration values. It is typically used to refresh the cache when necessary.</p>
     *
     * @see AppCache#cache
     * @see ConfigRepository#findAll()
     */
    @PostConstruct
    public void init(){
        cache = new ConcurrentHashMap<>();
        List<Config> all = configRepository.findAll();
        for (Config entries : all) {
            cache.put(entries.getKey(), entries.getValue());
        }
    }
}
