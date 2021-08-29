package com.example.orderengine.readtestdata.service;

import com.example.orderengine.modal.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProducrDAO producrDAO;

    public List<Product> findAll() {
        try {
            return producrDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    public boolean readTestData() {
        try {
            List<Product> csvData = getCSVData();
            if (csvData == null && csvData.isEmpty()) {
                return false;
            }
            producrDAO.saveAll(csvData);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Product> getCSVData() throws IOException {
        InputStream inputStream = null;
        Scanner sc = null;
        List<Product> productsList = new ArrayList<>();
        try {
            Resource resource = new ClassPathResource("./selected-data.txt");
            inputStream = resource.getInputStream();
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] split = line.split("\t");
                Product products = new Product();
                products.setProductId(split[0]);
                products.setCustomerIP(split[1]);
                products.setProductDetails(split[2]);
//                System.out.println(products);
                productsList.add(products);
            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
        return productsList;
    }

}
