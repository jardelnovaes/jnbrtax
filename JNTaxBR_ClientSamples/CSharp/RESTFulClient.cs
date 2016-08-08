// add System.Net.Http.dll reference
// add System.Runtime.Serialization.dll reference
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Runtime.Serialization.Json;
using System.IO;

namespace JNTaxBR_ClientSample
{
    public class RESTFulClient
    {
        private Uri taxRuleServiceUri;
        private HttpClient client;

        public String URLBase { get; set; }
        public String User { get; set; }
        public String Password { get; set; }

        public RESTFulClient()
        {
        	client = new HttpClient();        
        }

        private String GetCredentials()
        {
            return Convert.ToBase64String(System.Text.ASCIIEncoding.ASCII.GetBytes(string.Format("{0}:{1}", User, Password)));
        }

        public TaxRule GetTaxRule(String fromState, String toState, Int64 transactionType
                              /*,Int64 operation, Int64 personType, String ncm, Integer exNCM, String cest, Integer itemId, QueryTypeFindTaxRuleEnum queryType*/
                                )
        {
            TaxRule rule = null;

            client.BaseAddress = new Uri(URLBase);
            client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));
            
            //Update credentials
            client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", GetCredentials());

            //Call the API            
            HttpResponseMessage response = client.GetAsync(String.Format("taxRules/getTaxRule?fromState={0}&toState={1}&transactionType={2}", fromState, toState, transactionType)).Result;
            //if it has returned a success status
            if (response.IsSuccessStatusCode)
            {
                //it's getting the header
                taxRuleServiceUri = response.Headers.Location;
                //it's getting data from REST and putting it in a local variable.
                MemoryStream dados = new MemoryStream(response.Content.ReadAsByteArrayAsync().Result);
                DataContractJsonSerializer serialize = new DataContractJsonSerializer(typeof(TaxRule));
                rule = (TaxRule) serialize.ReadObject(dados);
            }            
            else
            {
                //if it has an error export the error message.
                throw new Exception("Error in getTaxRule: " + response.StatusCode.ToString() + " - " + response.ReasonPhrase);
            }
            return rule;
        }

        public static void Test()
        {
            RESTFulClient client = new RESTFulClient();
            client.URLBase = "http://192.168.211.111:9095/JNTaxBRPortal/rest/";
            client.User = "master@test.com";
            client.Password = "master";
            TaxRule rules = client.GetTaxRule("SC", "SC", 1);
            //Do taxes operations
        }

    }
}
