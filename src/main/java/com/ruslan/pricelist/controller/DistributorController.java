package com.ruslan.pricelist.controller;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Ruslan on 12/28/2016.
 */
@Controller
@RequestMapping("/distributor")
public class DistributorController {
    @Autowired
    FileStorageService fileStorageService;

    @RequestMapping
    public String getDistributorsPage() {
        return "distributor";
    }

    @RequestMapping("/getAllDistributors")
    public
    @ResponseBody
    List<Distributor> getDistributors() {
        return fileStorageService.getAllDistributors();
    }

    @RequestMapping("/remove/{id}")
    public @ResponseBody String removeDistributor(@PathVariable("id") Long id){
        fileStorageService.remove(id);
        return "redirect:/distributor";
    }

    @RequestMapping("/removeAll")
    public @ResponseBody void removeAllDistributors(){
        fileStorageService.removeAll();
    }

    @RequestMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        try {
            fileStorageService.storeFile(file);
            redirectAttributes.addFlashAttribute("message",
                    "Вы успешно загрузили файл " + file.getOriginalFilename() + "!");
            redirectAttributes.addFlashAttribute("success",true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("danger", true);
        }

        return "redirect:/distributor";
    }

}
