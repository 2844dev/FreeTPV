package com.mateo.freetpv.util;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BingImageScraper {

    private static final Logger log = LoggerFactory.getLogger(BingImageScraper.class);

    public record ImagenResultado(String turl, String murl) {}

    public static List<ImagenResultado> buscar(String query, int num_resultados) {

        // Establecemos la URL para buscar, la query la codificamos con UTF-8 y imagen al final para asegurar resultados
        String url = "https://www.bing.com/images/search?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8) + " imagen";
        List<ImagenResultado> resultados = new ArrayList<>();
        try {
            // Conseguimos el html de la url dándole un user agent moderno
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:149.0) Gecko/20100101 Firefox/149.0 GLS/100.10.9956.100").get();
            Elements elementos = doc.select("a.iusc");
            for (Element el : elementos) {
                // Escojemos el atributo m y lo añadimos a la lista
                JSONObject json = new JSONObject(el.attr("m"));
                String turl = json.getString("turl"); // Resolución miniatura
                String murl = json.getString("murl"); // Resolución completa
                // Nos aseguramos que no contienen imagenes .webp (No soportado por JavaFX)
                if (murl.contains(".webp") || turl.contains(".webp")) { continue; }
                resultados.add(new ImagenResultado(turl, murl));
                // Limitamos resultados
                if (resultados.size() >= num_resultados) break;
            }
            return resultados;
        } catch (IOException e) {
            log.error("Error al scrapear imagenes", e);
            return resultados;
        }
    }
}
