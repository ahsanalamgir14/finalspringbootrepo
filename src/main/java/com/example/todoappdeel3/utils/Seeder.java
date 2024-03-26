package com.example.todoappdeel3.utils;

import com.example.todoappdeel3.dao.ProductDAO;
import com.example.todoappdeel3.repository.UserRepository;
import com.example.todoappdeel3.models.Category;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.Product;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Seeder {
    private ProductDAO productDAO;
    private UserRepository userRepository;

    public Seeder(ProductDAO productDAO, UserRepository userRepository) {
        this.productDAO = productDAO;
        this.userRepository = userRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event){
        this.seedProducts();
        this.seedUser();
    }

    private void seedProducts(){
        Category budgetPc = new Category("1000 euro pc budget");

        Category performancePc= new Category("1700 pc performance");

        Product gpu_xt7900 = new Product("Asus Gpu xt 7900", "Een van de beste price performace videokaarten van AMD ", 799.00, performancePc, "https://edgeup.asus.com/wp-content/uploads/2022/11/feature-7900-xtx.jpg");


        Product pcCase = new Product("Montech air", "de case is extreem uitgebreid voor de prijs en het voelt als een steal de cablemanagement is goed weg te werken", 70.00, performancePc, "https://www.alternate.nl/p/600x600/5/4/Montech_Air_903_Base_midi_tower_behuizing@@1915745_30.jpg");
        Product cpu_7amd78003xd = new Product("AMD ryzen 7 78003xd", "De beste cpu voor gaming op de markt", 380.00, performancePc, "https://cf-images.dustin.eu/cdn-cgi/image/format=auto,quality=75,width=828,,fit=contain/image/d200001001923745/amd-ryzen-7-7800x3d-42ghz-socket-am5-processor.png");
        Product fansPhantom = new Product("Phantom fans", "Hele stille fans die gemakkelijk een zware cpu kunnen koelen met rgb", 45.00, performancePc, "https://m.media-amazon.com/images/I/41vBFxRWimL.jpg");
        Product powerSupply_850Watt = new Product("Cooler master 850 Watt 80 certified", "stille krachtige powersupply", 110.00, performancePc, "https://files.coolermaster.com/og-image/mwe-gold-850-v2-full-modular-1200x630.jpg");
        Product ssd1Tb = new Product("Lexar NM710 1 TB ", "goede prijs performance ssd", 70.00, performancePc, "https://img.informatique.nl/750/22890504.jpg");
        Product moboASRock_B650M = new Product("Asrock B650M", "goedkoop klein am5 motherboard", 140.00, performancePc, "https://www.megekko.nl/productimg/262416/midi/1_Asrock-B650M-PG-Lightning-WIFI-moederbord.jpg");

        Product gpu_xt6800 = new Product("XFX Speedster SWFT 319 Radeon RX 6800 16 GB Video Card", "goede budget amd gpu", 400.00, budgetPc, "https://assets-global.website-files.com/5d1911406ad3cbdb9924a753/611127d2165b209ae6755b7a_6800%20SWFT319%20box%2Bcard.jpg");
        Product pcCase_coolerMaster = new Product("Cooler Master", "goede budget pc case", 65.00, budgetPc, "https://azerty.nl/media/catalog/product/4/O/4OioEG.52aff83f3648ace2d06b59b4f2963c93-b9991b1a.jpg?quality=80&bg-color=255,255,255&fit=bounds&height=700&width=700&canvas=700:700");
        Product cpu_5amd7600 = new Product("AMD Ryzen 5 7600 3.8 GHz 6-Core Processor", "Krachtige processor", 200.00, budgetPc, "https://cf-images.dustin.eu/cdn-cgi/image/format=auto,quality=75,width=828,,fit=contain/image/d200001001903442/amd-ryzen-5-7600-38ghz-socket-am5-processor.jpg");
        Product powerSupply_750Watt = new Product("Gigabyte UD750GM 750 W 80+ Gold", "een van de beste merken", 89.00, budgetPc, "https://m.media-amazon.com/images/I/71ueFT9h8JL.jpg");
        Product ssd1_Tb = new Product("Western Digital Blue SN580 1 TB", "goede budget ssd", 65.00, budgetPc, "https://tweakers.net/ext/i/2006001592.jpeg");
        Product moboASRock_A620M = new Product("ASRock_A620M", "groot motherboard", 90.00, budgetPc, "https://media.s-bol.com/5EPxg8vPppyX/B36mBx/550x478.jpg");


        this.productDAO.createProduct(gpu_xt7900);
        this.productDAO.createProduct(cpu_7amd78003xd);
        this.productDAO.createProduct(fansPhantom);
        this.productDAO.createProduct(pcCase);
        this.productDAO.createProduct(powerSupply_850Watt);
        this.productDAO.createProduct(ssd1Tb);
        this.productDAO.createProduct(moboASRock_B650M);

        this.productDAO.createProduct(gpu_xt6800);
        this.productDAO.createProduct(cpu_5amd7600);
        this.productDAO.createProduct(pcCase_coolerMaster);
        this.productDAO.createProduct(powerSupply_750Watt);
        this.productDAO.createProduct(ssd1_Tb);
        this.productDAO.createProduct(moboASRock_A620M);

    }

    private void seedUser(){
        CustomUser customUser = new CustomUser();
        customUser.setEmail("test@mail.com");
        customUser.setPassword(new BCryptPasswordEncoder().encode("Test123!"));
        userRepository.save(customUser);
    }
}