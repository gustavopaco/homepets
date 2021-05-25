using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HomePets.Domain
{
    public class CartaoCredito : PersistentData
    {
        public CartaoCredito()
        {

        }

        [Column("CartaoCreditoId")]
        public override int Id { get; set; }

        public string NomeTitular { get; set; }
        public string Numero { get; set; }
        public string CodigoValidacao { get; set; }
        public string ValidadeMesAno { get; set; }


        public int UsuarioId { get; set; }
        public Usuario Usuario { get; set; }
    }
}
