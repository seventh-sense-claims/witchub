package witchub.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import witchub.com.models.Product;
import witchub.com.services.ProductService;
import witchub.com.services.SecurityService;

import java.util.List;

@Controller
public class HomePageController {
    SecurityService securityService;
    ProductService productService;

    @Autowired
    public HomePageController(SecurityService securityService, ProductService productService) {
        this.securityService = securityService;
        this.productService = productService;
    }

    @RequestMapping("/403")
    public String accessDenied(){
        return "accessDenied";
    }

    @RequestMapping({"/", "", "/homepage"})
    public String homepage(Model model){
        String loggedInUsername = securityService.findLoggedInUsername();
        if(loggedInUsername==null){
            model.addAttribute("loginStatus", false);
        }
        else{
            model.addAttribute("loginStatus", true);
        }
        List<Product> productList = productService.getAll();
        System.out.println(productList.size());
        model.addAttribute("products", productList);
        return "homepage";
    }
}
