package com.openclassrooms.safetyNet.services;

import java.io.IOException;
import java.util.List;

public interface ICommunityEmailService {
    List<String> getAllEmailFromCity(String city) throws IOException;
}
