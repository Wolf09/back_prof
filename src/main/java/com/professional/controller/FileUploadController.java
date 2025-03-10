package com.professional.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/up")
public class FileUploadController {

    // Define la ruta donde se guardarán los archivos. Por ejemplo, en el directorio "uploads"
    private final String uploadDir = "uploads";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        // Directorio donde se guardarán los archivos
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                response.put("error", "No se pudo crear el directorio de subida");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Limpia el nombre del archivo original y extrae su extensión
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }

        // Usar la fecha actual en milisegundos como nombre del archivo
        String fileName = System.currentTimeMillis() + extension;
        Path filePath = uploadPath.resolve(fileName);

        // Si ya existe un archivo con ese nombre, solo se guarda el primero y se devuelve la ruta existente
        if (Files.exists(filePath)) {
            response.put("path", "/uploads/" + fileName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        try {
            Files.copy(file.getInputStream(), filePath);
            String fileUrl = "/uploads/" + fileName;
            response.put("path", fileUrl);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("error", "Error al guardar el archivo");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

