package de.fh_dortmund.inf.cw.surstwalat.client.util;

import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Stephan Klimek
 */
public class TextRepository {

    private static TextRepository instance;

    private final static String PATH = "resources/i18n/";
    private final String lang = "de";
    private final String local = "DE";

    Map<String, Object> textRepository;

    /**
     * Constructor
     */
    public TextRepository() {
        textRepository = YMLLoader();
    }

    /**
     * Get component textRepository
     * 
     * @param component
     * @return component textRepository
     */
    public Map<String, String> getTextRepository(String component) {
        return (Map<String, String>) textRepository.get(component);
    }

    /**
     * Get root Repository
     * 
     * @return root textRepository
     */
    public Map<String, Object> getTextRepository() {
        return textRepository;
    }

    /**
     * Load root of the text repository
     *
     * @return root textRepository
     */
    private Map<String, Object> YMLLoader() {
        Yaml snakeyaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(PATH + lang + "-" + local + ".yml");
        return snakeyaml.load(inputStream);
    }

    /**
     * Get instance TextRepository
     *
     * @return instance of TextRepository
     */
    public static TextRepository getInstance() {
        if (instance == null) {
            instance = new TextRepository();
        }
        return instance;
    }

    /**
     * Get new instance TextRepository
     *
     * @return new instance of TextRepository
     */
    public static TextRepository getNewInstance() {
        return new TextRepository();
    }
}
