package com.yoc.importer.elasticsearch.processors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author klaus
 *
 * processes List<List<String>> objects from camel-csv for example
 * to HashMap<String, String> with headline names as needed by camel.elasticsearch
 *
 */
public class Mapping implements Processor {

    private List<String> headLine;

    @Override
    public void process(Exchange exchange) throws Exception {

        /**
         * the unmarshalled input csv file line wise in List<List<String>> object
         * should be a one lined table if split is set in route
         * but should work with multiline tables already
         */
        @SuppressWarnings("unchecked")
        List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();

        /**
         * sets the headline if not already set
         */
        if(this.getHeadLine() == null) {
            this.setHeadLine();
        }

        /**
         * initializes the out object as HashMap<String, String>
         */
        Map<String, String> out = new HashMap<String, String>();

        /**
         * first loop should not be ran more than once if route splits stream
         */
        for (List<String> row : data) {

            /**
             * loop through each column and put togehther name and value pairs
             */
            int n = 0;
            for (String value : row) {
                out.put(this.getHeadLine().get(n), value);
                n++;
            }

            /**
             * puts filename as key value pair in result
             */
            out.put("fileName", (String)exchange.getIn().getHeader("CamelFileName"));

            /**
             * sets the index name
             */
            String indexName = (String)exchange.getIn().getHeader("CamelFileName");
            exchange.getIn().setHeader("CamelIndexName", indexName.split(".")[0]);

            /**
             * sets the out object in the exchange object
             */
            exchange.getIn().setBody(out);
        }
    }

    /**
     * returns head line data
     *
     * @return the headLine
     */
    public List<String> getHeadLine() {
        return headLine;
    }

    /**
     * sets the headLine can be overwritten or refactored
     */
    public void setHeadLine() {

        this.headLine = new ArrayList<String>();

        this.getHeadLine().add("t_data_belboon_productnumber");
        this.getHeadLine().add("t_data_t_program_id");
        this.getHeadLine().add("t_data_merchant_productnumber");
        this.getHeadLine().add("t_data_ean_code");
        this.getHeadLine().add("t_data_product_title");
        this.getHeadLine().add("t_data_manufacturer");
        this.getHeadLine().add("t_data_brand");
        this.getHeadLine().add("t_data_price");
        this.getHeadLine().add("t_data_price_old");
        this.getHeadLine().add("t_data_currency");
        this.getHeadLine().add("t_data_valid_from");
        this.getHeadLine().add("t_data_valid_to");
        this.getHeadLine().add("t_data_deeplink_url");
        this.getHeadLine().add("t_data_into_basket_url");
        this.getHeadLine().add("t_data_image_small_url");
        this.getHeadLine().add("t_data_image_small_height");
        this.getHeadLine().add("t_data_image_small_width");
        this.getHeadLine().add("t_data_image_big_url");
        this.getHeadLine().add("t_data_image_big_height");
        this.getHeadLine().add("t_data_image_big_width");
        this.getHeadLine().add("t_data_merchant_product_category");
        this.getHeadLine().add("t_data_belboon_product_category");
        this.getHeadLine().add("t_data_keywords");
        this.getHeadLine().add("t_data_product_description_short");
        this.getHeadLine().add("t_data_product_description_long");
        this.getHeadLine().add("t_data_last_update");
        this.getHeadLine().add("t_data_shipping");
        this.getHeadLine().add("t_data_availability");
        this.getHeadLine().add("t_data_option_1");
        this.getHeadLine().add("t_data_option_2");
        this.getHeadLine().add("t_data_option_3");
        this.getHeadLine().add("t_data_option_4");
        this.getHeadLine().add("t_data_option_5");
    }

}
