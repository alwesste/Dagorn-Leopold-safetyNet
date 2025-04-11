package com.openclassrooms.safetyNet.services;

import com.openclassrooms.safetyNet.models.DataJsonHandler;

import java.io.IOException;

public interface IJsonFileHandler {

    DataJsonHandler readJsonFile() throws IOException;
    void writeJsonFile(DataJsonHandler data) throws IOException;
}
