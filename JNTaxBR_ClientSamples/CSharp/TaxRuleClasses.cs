using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace JNTaxBR_ClientSample
{
    /*
     * This is a simple example, you need create the properties that you need in you application.
     * In this example some properties are commented.
    */
    public class TaxRule
    {
        public long id { get; set; }

        //private AddressState fromState;	
        //private AddressState toState;

        public Boolean active { get; set; }

        //private TransactionType transactionType;
        //private Operation operation;
        //private PersonType personType;

        public String NCM { get; set; }
        public int ExNCM { get; set; }
        public String CEST { get; set; }
        public int itemId { get; set; }

        public TaxRulePISCOFINS taxRulePISCOFINS { get; set; }
        public TaxRuleICMS taxRuleICMS { get; set; }
    }

    public class GenericCSTEntity
    {
        public String cst { get; set; }
        public String description { get; set; }
    }

    public class TaxRulePISCOFINS
    {
        //private PISCOFINSCST pisCST; //You can create a specific CST PIS/COFINS class to get all properties
        public GenericCSTEntity pisCST { get; set; }
        public Decimal pisRate { get; set; }

        //private PISCOFINSCST cofinsCST; //You can create a specific CST PIS/COFINS class to get all properties
        public GenericCSTEntity cofinsCST { get; set; }
        public Decimal cofinsRate { get; set; }
        public String legalNotes { get; set; }
        public Boolean active { get; set; }
    }

    public class TaxRuleICMS
    {

        //private ICMSCST icmsCST; //You can create a specific CST ICMS class to get all properties
        public GenericCSTEntity icmsCST { get; set; }
        public Decimal taxPayerRate { get; set; }
        public Decimal finalConsumerRate { get; set; }
        public Decimal reductionPercent { get; set; }
        public Decimal stRate { get; set; }
        public Decimal mvaRate { get; set; }
        public Decimal stReductionPercent { get; set; }
        public String legalNotes { get; set; }
        public Boolean active { get; set; }
    }
}
