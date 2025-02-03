package service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;

import exception.TriviaAPIException;
import model.TriviaInfo;
import model.trivia.ErrorResponse;
import model.trivia.Result;
import model.trivia.TriviaResult;

public class TriviaAPIService {
	private final String API_URL;
	

	public TriviaAPIService(String API_URL) {
		this.API_URL = API_URL;
		
	}


	public List<TriviaInfo> getQuestions(int number) throws TriviaAPIException {
		List<TriviaInfo> triviaInfoList = new ArrayList<TriviaInfo>();
	try {
		URIBuilder uriBuilder = new URIBuilder(API_URL).setPathSegments("api.php")
				.addParameter("amount", (new Integer (number)).toString());
		
		URI uri = uriBuilder.build();
		
		HttpGet getRequest = new HttpGet(uri);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		CloseableHttpResponse response = httpclient.execute(getRequest);
		
		HttpEntity entity = response.getEntity();
		
		ObjectMapper mapper = new ObjectMapper();
		
		if (response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK) {
			ErrorResponse errorResponse = mapper.readValue(entity.getContent(), ErrorResponse.class);
			if(errorResponse.getResponseCode()!=null) {
				System.out.println(errorResponse.getResponseCode());
				throw new TriviaAPIException("Error occures on API call code "+ errorResponse.getResponseCode());
			}
		}
		
		TriviaResult triviaResultObject = mapper.readValue(entity.getContent(), TriviaResult.class);
		
		List<Result> tResults = triviaResultObject.getResults();
		
		
		for (Result r: tResults) {
			TriviaInfo t = new TriviaInfo(r.getCategory(), r.getType(), r.getQuestion(), r.getCorrectAnswer(), r.getIncorrectAnswers());
			triviaInfoList.add(t);
			
			

		}
		
		
		return triviaInfoList;
		
		
		
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new TriviaAPIException("Unable to create request URI.", e);
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new TriviaAPIException("Problem with Client", e);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new TriviaAPIException("Error requesting data from the site", e);
	}
	
	
	
	}
	
	
	public List<TriviaInfo> getQuenstionsWithParameters(int number, String category, String difficulty, String type) throws TriviaAPIException{
		List<TriviaInfo> triviaInfoList = new ArrayList<TriviaInfo>();
	try {
		URIBuilder uriBuilder = new URIBuilder(API_URL).setPathSegments("api.php")
				.addParameter("amount", (new Integer (number)).toString())
				.addParameter("category", category)
				.addParameter("difficulty",difficulty)
				.addParameter("type",type);
		
		URI uri = uriBuilder.build();
		
		HttpGet getRequest = new HttpGet(uri);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		CloseableHttpResponse response = httpclient.execute(getRequest);
		
		HttpEntity entity = response.getEntity();
		
		ObjectMapper mapper = new ObjectMapper();
		
		if (response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK) {
			ErrorResponse errorResponse = mapper.readValue(entity.getContent(), ErrorResponse.class);
			if(errorResponse.getResponseCode()!=null) {
				System.out.println(errorResponse.getResponseCode());
				throw new TriviaAPIException("Error occures on API call code "+ errorResponse.getResponseCode());
			}
		}
		
		TriviaResult triviaResultObject = mapper.readValue(entity.getContent(), TriviaResult.class);
		
		List<Result> tResults = triviaResultObject.getResults();
		
		
		for (Result r: tResults) {
			TriviaInfo t = new TriviaInfo(r.getCategory(), r.getType(), r.getQuestion(), r.getCorrectAnswer(), r.getIncorrectAnswers());
			triviaInfoList.add(t);
			

		}
		
		
		
		return triviaInfoList;
		
		
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new TriviaAPIException("Error requesting data from the site", e);
		
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new TriviaAPIException("Error requesting data from the site", e);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new TriviaAPIException("Error requesting data from the site", e);
	}
	
	
	
	}
	
	
	
}
