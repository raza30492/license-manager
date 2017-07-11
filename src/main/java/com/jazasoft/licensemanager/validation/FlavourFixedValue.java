package com.jazasoft.licensemanager.validation;

import com.jazasoft.licensemanager.Constants;
import com.jazasoft.licensemanager.service.ProductService;
import com.jazasoft.licensemanager.util.ApplicationContextUtil;
import com.jazasoft.licensemanager.util.YamlUtils;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by razamd on 3/30/2017.
 */
public class FlavourFixedValue implements FixedValue{

    @Override
    public Set<String> getFixedValues() {
        ProductService productService = ApplicationContextUtil.getApplicationContext().getBean(ProductService.class);
        return productService.getProductFlavours();
    }
}
