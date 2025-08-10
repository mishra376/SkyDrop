package com.skydrop.SkyDrop.Controller;

import com.skydrop.SkyDrop.Service.DownloadObect;
import com.skydrop.SkyDrop.Service.ObjectService;
import com.skydrop.SkyDrop.Service.UploadObject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class ObjectController {

    private ObjectService objectService;
    private UploadObject uploadObject;
    public DownloadObect downloadObect;

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        return ResponseEntity.ok(objectService.listObjects());
    }





    @GetMapping("/download/{objectName}")
    public ResponseEntity<?> downloadObject(@PathVariable String objectName) {
        try {
            return downloadObect.downloadObject(objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String response =  uploadObject.uploadFile(file);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        try {
            objectService.deleteObject("my-bucket", filename); // Change to your bucket
            return ResponseEntity.ok("File deleted: " + filename);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting file: " + e.getMessage());
        }
    }
}
