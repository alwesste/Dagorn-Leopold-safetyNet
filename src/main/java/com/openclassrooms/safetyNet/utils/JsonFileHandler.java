package com.openclassrooms.safetyNet.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


// JsonFileHandler en static permet de sauver de la memoir par rapport a l'utilisation avec Autowired
// permet de dire a java je veux acceder a cette methode sans la création d'une instance
@Component
public class JsonFileHandler {


    @Value("${file.path}")
    public String filePath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @return une map contenant les datas du fichier json. La cle de est une string, la valeur est un objet quelconque
     * @throws IOException renvoie une erreur si un probleme se passe lors de la lecture.
     */
    public DataJsonHandler readJsonFile() throws IOException {
        return objectMapper.readValue(new File(filePath), DataJsonHandler.class);
    }

    /**
     * @param data une map contenant les datas du fichier json. La cle de est une string, la valeur est un objet quelconque
     * @throws IOException renvoie une erreur si un probleme se passe lors de l'ecriture.
     */
    public void writeJsonFile(DataJsonHandler data) throws IOException {

        objectMapper.writeValue(new File(filePath), data);
    }
}
