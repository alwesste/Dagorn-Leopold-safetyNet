package com.openclassrooms.safetyNet.interfaces;

import java.io.IOException;
import java.util.List;

public interface ICommunityEmailService {
    List<String> getAllEmailFromCity(String city) throws IOException;
}
