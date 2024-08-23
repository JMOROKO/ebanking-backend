package com.enset.ebankingbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    public Map<String, String> uploadFile(MultipartFile file, String direName){

        Map<String, String> response = new HashMap<>();
        if(file!=null){
            try {
                Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", direName);

                //System.out.println(System.getProperty("user.home"));
                //creation du dossier s'il n'existe pas
                if(!Files.exists(folderPath)){
                    Files.createDirectories(folderPath);
                }

                //création du nom du fichier unique du fichier
                String fileName = UUID.randomUUID().toString()+file.getOriginalFilename();

                //sauvegarde du fichier
                // Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "users", file.getOriginalFilename());
                Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", direName, fileName);

                //transfert du fichier vers le dossier
                Files.copy(file.getInputStream(), filePath);

                response.put("fileName", filePath.toUri().toString());
                response.put("directory", filePath.toString());
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize folder for upload!");
            }
        }

        return response;
    }


}
