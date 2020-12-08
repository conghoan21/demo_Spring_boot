package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.ProductForm;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Value("${upload.path}")
    private String fileUpload;

    @GetMapping("/list")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView("/product/list");
        List<Product> products = (List<Product>) productService.findAll();
        modelAndView.addObject("products", products);
        return modelAndView;
    }
    @GetMapping()
    public ResponseEntity<Iterable<Product>> getAll(){
        return new ResponseEntity<>(productService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/create")
    public ModelAndView showFormCreate(){
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new ProductForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public String creatTask(@ModelAttribute ProductForm product){
        Product product1 = new Product.ProductBuilder(product.getName())
                .description(product.getDescription()).build();
        MultipartFile multipartFile = product.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(product.getImage().getBytes(), new File(this.fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        product1.setImage(fileName);
        productService.save(product1);
        return "redirect:/products/list";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id){
        productService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
