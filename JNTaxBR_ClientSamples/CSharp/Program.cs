using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace JNTaxBR_ClientSample
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                RESTFulClient.Test();
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error:\n" + ex.Message);
            }
            Console.Write("Press any key to continue ...");
            Console.ReadLine();
        }
    }
}
