	package br.com.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.dto.BuscaCep;

@Service
public class BuscaCepService {

	public BuscaCep getEndereco(String cep) throws ClientProtocolException, IOException {

		BuscaCep endereco = null;

		HttpGet request = new HttpGet("https://viacep.com.br/ws/" + cep + "/json/");

		try (CloseableHttpClient httpclient = HttpClientBuilder.create().disableRedirectHandling().build();
				CloseableHttpResponse responde = httpclient.execute(request)) {

			HttpEntity entity = responde.getEntity();

			if (entity != null) {
				String result = EntityUtils.toString(entity);

				Gson gson = new Gson();

				endereco = gson.fromJson(result, BuscaCep.class);

			}

		}

		return endereco;

	}

}
