import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static ObjectMapper mapper = new ObjectMapper();

    public static List<Cat> httpGet(String http) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36\n")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(http);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        CloseableHttpResponse response = httpClient.execute(request);
        Arrays.stream(response.getAllHeaders());
        List<Cat> post  = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {});

        response.close();
        httpClient.close();

        return post;
    }

    public static void main(String[] args) throws IOException {
        final String url =  "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
        List<Cat> catWin = httpGet (url)
                .stream()
                .filter(value -> value.getUpvotes() > 0)
                .collect(Collectors.toList());

                 catWin.forEach(System.out::println);


       }

    }

