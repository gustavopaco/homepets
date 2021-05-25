using System;

namespace HomePets.Domain
{
    public class CreditCardModel
    {
        public int id { get; set; }
        public string numero { get; set; }
        public string nomeTitular { get; set; }
        public string codigoValidacao { get; set; }
        public string validadeMesAno { get; set; }

    }
}
