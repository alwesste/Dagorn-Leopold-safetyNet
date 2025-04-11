package com.openclassrooms.safetyNet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetyNet.services.IJsonFileHandler;
import com.openclassrooms.safetyNet.models.DataJsonHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class JsonFileHandler implements IJsonFileHandler {

    @Value("${file.path}")
    public String filePath;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @return une map contenant les datas du fichier json. La cle de est une string, la valeur est un objet quelconque
     * @throws IOException renvoie une erreur si un probleme se passe lors de la lecture.
     */
    @Override
    public DataJsonHandler readJsonFile() throws IOException {
        return objectMapper.readValue(new File(filePath), DataJsonHandler.class);
    }

    /**
     * @param data une map contenant les datas du fichier json. La cle de est une string, la valeur est un objet quelconque
     * @throws IOException renvoie une erreur si un probleme se passe lors de l'ecriture.
     */
    @Override
    public void writeJsonFile(DataJsonHandler data) throws IOException {

        objectMapper.writeValue(new File(filePath), data);
    }
}
