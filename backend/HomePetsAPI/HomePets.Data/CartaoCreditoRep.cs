using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;


namespace HomePets.Data
{
    public class CartaoCreditoRep : Repositorio<CartaoCredito>, ICartaoCreditoRep
    {
        public CartaoCreditoRep(EFContext context) : base(context)
        {

        }

        public CartaoCredito ObterCartaoCredito(int Id)
        {
            return AsQueryable().Where(p => !p.Deleted && p.Id == Id)
                .Select(s => new
                {
                    s,
                }).SingleOrDefault()?.s;
        }


        public IEnumerable<CartaoCredito> ObterCartoesCredito(int UsuarioId)
        {
            return AsQueryable().Where(p => !p.Deleted && p.UsuarioId == UsuarioId)
                .Select(s => new
                {
                    s,
                }).ToList().Select(s => s.s);

        }
    }
}
