using System;
using System.Collections.Generic;

namespace HomePets.Domain
{
    public interface IServicoRep : IRepositorio<Servico>
    {
        Servico ObterServico(int Id);
        IEnumerable<Servico> ObterServicos(int UsuarioId);
    }
}