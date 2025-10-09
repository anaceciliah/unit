package unit.br.unitnetwork.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unit.br.unitnetwork.exception.FileUploadNotAllowedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    public String saveFile(MultipartFile file) {

        if( file.isEmpty() || file == null) {
            throw new FileUploadNotAllowedException("Foto Inválida");

        }

        validateType(file);
        validateSize(file);

        String uploadDir = "uploads/";
        String fileName = null;
        try {
            Files.createDirectories(Paths.get(uploadDir));
            fileName = UUID.randomUUID().toString() + getFileExtension(file);
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

        } catch (IOException e){
            throw new FileUploadNotAllowedException("Não foi possível salvar a foto no diretório especificado ");
        }

        return fileName;

    }

    private String getFileExtension (MultipartFile file) {

        String contentType = file.getContentType();
        return "." + contentType.split("/")[1];
    }


    private void validateType (MultipartFile file) {
        String contentType = file.getContentType();
        if (!("image/jpeg".equals(contentType) || "image/png".equals(contentType) || "image/jpg" .equals(contentType))) {
            throw new FileUploadNotAllowedException("Apenas arquivos JPEG, JPG e PNG são permitidos");
        }

    }
    private void validateSize (MultipartFile file) {
        if(file.getSize() > (5 *  1024 * 1024)) {
            throw new FileUploadNotAllowedException("Arquivo excede 5MB");
        }
    }
}
