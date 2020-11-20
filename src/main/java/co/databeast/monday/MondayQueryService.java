
package co.databeast.monday;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Service
public class MondayQueryService {

    @Value( "${monday.api.url}" )
    private String mondayApiUrl;

    @Value( "${monday.api.key}" )
    private String mondayApiKey;

    public void call(){




        //String body = "{\"query\":\"{boards(limit:1){id name}}\"}";
        String body = "{\"query\":\"mutation {\n" +
                "create_item (board_id: 860823721, group_id: \"today\", item_name: \"new item\") {\n" +
                "id\n" +
                "}\n" +
                "}\"}";

        body = getQuery();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", mondayApiKey);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(mondayApiUrl, HttpMethod.POST, entity, String.class);

        String jsonObject = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getQuery() {
        String contents = "";
        try {
            Resource resource = new ClassPathResource("query.graphql");
            File file = resource.getFile();
            contents = new String(Files.readAllBytes(file.toPath()));
            contents = "{" + contents + "}";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }
}
