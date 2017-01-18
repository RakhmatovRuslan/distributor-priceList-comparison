package com.ruslan.pricelist.controller;

import com.ruslan.pricelist.beans.Distributor;
import com.ruslan.pricelist.beans.Message;
import com.ruslan.pricelist.service.FileStorageService;
import com.ruslan.pricelist.utility.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
    public String handleFileUpload(@RequestParam("files") MultipartFile files[], RedirectAttributes redirectAttributes) {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            if(files.length == 0)
                throw new IllegalArgumentException("Вы не загрузили ни одного файла!");

            for (MultipartFile file : files) {
                messages.add(fileStorageService.storeFile(file));
            }
            redirectAttributes.addFlashAttribute("messages",messages);
        } catch (Exception e) {
            messages.add(new Message(e.getMessage(),false));
            redirectAttributes.addFlashAttribute("messages", messages);
        }

        return "redirect:/distributor";
    }

}
