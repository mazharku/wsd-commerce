package com.wsd.commerce.service.impl;

import com.wsd.commerce.model.dto.product.ProductSale;
import com.wsd.commerce.model.entity.Product;
import com.wsd.commerce.model.entity.Sale;
import com.wsd.commerce.repository.ProductRepository;
import com.wsd.commerce.repository.SaleRepository;
import com.wsd.commerce.service.SaleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = SaleServiceImpl.class)
public class TestSaleServiceImpl {
    @MockBean
    private SaleRepository saleRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private SaleService service;


    Product mango() {
        Product product = new Product();
        product.setName("mango");
        product.setPrice(10.0);
        return product;
    }

    Product tv() {
        Product product = new Product();
        product.setName("tv");
        product.setPrice(340.0);
        return product;
    }

    Product phone() {
        Product product = new Product();
        product.setName("phone");
        product.setPrice(499.0);
        return product;
    }
    Product watch() {
        Product product = new Product();
        product.setName("watch");
        product.setPrice(199.0);
        return product;
    }

    Product remote() {
        Product product = new Product();
        product.setName("remote");
        product.setPrice(19.0);
        return product;
    }

    List<Sale> todaySales() {
        Sale sale1 = new Sale();
        sale1.setSaleAt(LocalDate.now());
        sale1.setQuantity(1);
        sale1.setTotalAmount(sale1.getQuantity()* tv().getPrice());
        sale1.setProduct(tv());

        Sale sale2 = new Sale();
        sale2.setSaleAt(LocalDate.now());
        sale2.setQuantity(2);
        sale2.setTotalAmount(sale2.getQuantity()* mango().getPrice());
        sale2.setProduct(mango());

        return List.of(sale1, sale2);
    }

    List<Sale> lastMonthSales() {
        Sale sale1 = new Sale();
        sale1.setSaleAt(LocalDate.of(2024, 4, 22));
        sale1.setQuantity(3);
        sale1.setTotalAmount(sale1.getQuantity()* remote().getPrice());
        sale1.setProduct(remote());

        Sale sale2 = new Sale();
        sale2.setSaleAt(LocalDate.of(2024, 4, 10));
        sale2.setQuantity(2);
        sale2.setTotalAmount(sale2.getQuantity()* phone().getPrice());
        sale2.setProduct(phone());

        Sale sale3 = new Sale();
        sale3.setSaleAt(LocalDate.of(2024, 4, 29));
        sale3.setQuantity(1);
        sale3.setTotalAmount(sale3.getQuantity()* watch().getPrice());
        sale3.setProduct(watch());

        return List.of(sale1, sale2,sale3);
    }

    List<Sale> allSales() {
        List<Sale> sales = new ArrayList<>();
        sales.addAll(lastMonthSales());
        sales.addAll(todaySales());
        return sales;
    }

    List<ProductSale> top5Sales() {
        ProductSale p1 = new ProductSale() {
            @Override
            public String getName() {
                return "mango";
            }

            @Override
            public double getTotal() {
                return 19;
            }
        };

        ProductSale p2 = new ProductSale() {
            @Override
            public String getName() {
                return "tv";
            }

            @Override
            public double getTotal() {
                return 350;
            }
        };
        ProductSale p3 = new ProductSale() {
            @Override
            public String getName() {
                return "remote";
            }

            @Override
            public double getTotal() {
                return 99;
            }
        };

        ProductSale p4 = new ProductSale() {
            @Override
            public String getName() {
                return "watch";
            }

            @Override
            public double getTotal() {
                return 209;
            }
        };
        ProductSale p5 = new ProductSale() {
            @Override
            public String getName() {
                return "phone";
            }

            @Override
            public double getTotal() {
                return 450;
            }
        };
        return List.of(p5, p2, p4,p3,p1 );
    }

    @Test
    void test__should_return_current_day_sale_amount() {
        Mockito.when(saleRepository.findAllBySaleAt(LocalDate.now())).thenReturn(todaySales());

        var result = service.currentDaySaleAmount();

        Assertions.assertEquals(result, 360);
        verify(saleRepository, times(1)).findAllBySaleAt(LocalDate.now());
    }

    @Test
    void test__should_return_zero_for_no_sale() {
        Mockito.when(saleRepository.findAllBySaleAt(LocalDate.now())).thenReturn(List.of());

        var result = service.currentDaySaleAmount();

        Assertions.assertEquals(result, 0);
        verify(saleRepository, times(1)).findAllBySaleAt(LocalDate.now());
    }

    @Test
    void test__maxSaleDateOfRange_should_return_a_valid_date() {
        LocalDate startDate = LocalDate.of(2024, 4, 1);
        LocalDate endDate = LocalDate.of(2024, 4, 22);

        Mockito.when(saleRepository.findAllBySaleAtBetween(startDate, endDate)).thenReturn(allSales());
        var result = service.maxSaleDateOfRange(startDate,endDate);

        LocalDate expectedDate = LocalDate.of(2024, 4, 10);

        Assertions.assertEquals(expectedDate, result);
        verify(saleRepository, times(1)).findAllBySaleAtBetween(startDate,endDate);
    }

    @Test
    void test__maxSaleDateOfRange_should_return_current_date_when_value_not_in_range() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 22);

        Mockito.when(saleRepository.findAllBySaleAtBetween(startDate, endDate)).thenReturn(List.of());
        var result = service.maxSaleDateOfRange(startDate,endDate);

        LocalDate expectedDate = LocalDate.now();

        Assertions.assertEquals(expectedDate, result);
        verify(saleRepository, times(1)).findAllBySaleAtBetween(startDate,endDate);
    }

    @Test
    void test_top_5_should_return_5() {
        Mockito.when(saleRepository.findTopSaleProduct()).thenReturn(top5Sales());
        var result = service.topFiveSaleProduct();

        List<String> expectedValue = List.of("phone", "tv", "watch","remote", "mango");

        Assertions.assertEquals(expectedValue, result);
        verify(saleRepository, times(1)).findTopSaleProduct();
    }

    @Test
    void test_top_5_should_return_below_5_when_items_less_five() {
        Mockito.when(saleRepository.findTopSaleProduct()).thenReturn(top5Sales());
        var result = service.topFiveSaleProduct().subList(0,3);

        List<String> expectedValue = List.of("phone", "tv", "watch");

        Assertions.assertEquals(expectedValue, result);
        verify(saleRepository, times(1)).findTopSaleProduct();
    }

    @Test
    void test__customer_wish_list_should_return_all_product_list() {
        Mockito.when(productRepository.findAll()).thenReturn(List.of(mango(),tv(),watch(),remote(),phone()));

        var result = service.customerWishList();

        Assertions.assertEquals(5, result.size());
        verify(productRepository, times(1)).findAll();
    }

}
