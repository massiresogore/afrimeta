package com.msr.cg.afrimeta.storage;


import com.msr.cg.afrimeta.system.exception.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

//@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/bataclan/images")
public class FileUploadController {

    private final StorageService storageService;
    private final Path rootLocation;

    @Autowired
    public FileUploadController(StorageService storageService, StorageProperties properties) {
        this.storageService = storageService;
        this.rootLocation =  Paths.get(properties.getLocation());
    }

    @GetMapping
    public String listUploadedFiles(Model model) throws IOException {
        //+All Image
        model.addAttribute("files",storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        //Une Image
       // Path path =  storageService.load("ciudad-maderas-MXbM1NrRqtI-unsplash.jpg");

       // System.out.println(path);

  /* String p =   MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
           "serveFile",path.getFileName().toString()).build().toUri().toString();
        System.out.println(p);*/

  // model.addAttribute("file",p);
        return "index";
    }

  /*   @GetMapping
    public Result listUploadedFiles() throws IOException {

        return new Result(true, StatusCode.SUCCESS, "toutes les images", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
    }*/

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes
    ) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }



}