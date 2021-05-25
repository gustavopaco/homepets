using System;
using System.Collections.Generic;

namespace HomePets.Domain
{
    public interface ICartaoCreditoRep : IRepositorio<CartaoCredito>
    {
        CartaoCredito ObterCartaoCredito(int Id);
        IEnumerable<CartaoCredito> ObterCartoesCredito(int UsuarioId);
    }
}