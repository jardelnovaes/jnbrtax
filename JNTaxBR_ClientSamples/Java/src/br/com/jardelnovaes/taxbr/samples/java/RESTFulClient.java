package br.com.jardelnovaes.taxbr.samples.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;
import br.com.jardelnovaes.taxbr.models.TaxRule;

public class RESTFulClient {
	//private Uri taxRuleServiceUri;
    //private HttpClient client;

    private String URLBase;
    private String User; 
    private String Password;

    public String getURLBase() {
		return URLBase;
	}

	public void setURLBase(String uRLBase) {
		URLBase = uRLBase;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	
    private String getCredentials()
    {
    	try {    		
    		return javax.xml.bind.DatatypeConverter.printBase64Binary(String.format("%s:%s",getUser(), getPassword()).getBytes()); //Convert.ToBase64String(System.Text.ASCIIEncoding.ASCII.GetBytes(string.Format("{0}:{1}", User, Password)));
		} catch (Exception e) {
			return "";
		}        
    }

    public TaxRule getTaxRule(String fromState, String toState, Long transactionType
                          /*,Long operation, Long personType, String ncm, Integer exNCM, String cest, Integer itemId, QueryTypeFindTaxRuleEnum queryType*/
                            ) throws Exception
    {
    	
        TaxRule rule = null;
        String jsonString;
        
        URL url = new URL(String.format("%s/taxRules/getTaxRule?fromState=%s&toState=%s&transactionType=%s", getURLBase(), fromState, toState, transactionType));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Authorization", "Basic "+getCredentials());
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
        
        //Call the API
        if (conn.getResponseCode() != 200) {
        	
        	//if it has an error export the error message.
			throw new RuntimeException("Error in getTaxRule: \nHTTP error code: "
					+ conn.getResponseCode());
		}
        
        //if it has returned a success status
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        jsonString = br.readLine();
		
        if(jsonString != null){			
			System.out.println("Output from Server .... \n");
			System.out.println(jsonString);
		
			//it's getting data from REST and putting it in a local variable.
			ObjectMapper mapper = new ObjectMapper();
			rule = mapper.readValue(jsonString, TaxRule.class);
			
		} 
		conn.disconnect();		
		return rule;            
    }

    @SuppressWarnings("unused")
	public static void Test() throws Exception
    {
		RESTFulClient client = new RESTFulClient();
		client.setURLBase("http://localhost:8080/JNTaxBRPortal/rest");
        client.setUser("master@test.com");
        client.setPassword("master");
        TaxRule rules = client.getTaxRule("SC", "SC", 1L);
        //Do taxes operations	    
    }
	
	public static void main(String[] args) {
		try {
			System.out.println("RESTFul java test\n---------------------------------\n\n");
			RESTFulClient.Test();
			System.out.println("\n---------------------------------\nfinished");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
