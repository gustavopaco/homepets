using System;
using System.Collections.Generic;

namespace HomePets.Domain
{
    public interface IServicoContratadoRep : IRepositorio<ServicoContratado>
    {
        ServicoContratado ObterServicoContratado(int Id);
        IEnumerable<ServicoContratado> ObterServicosRelacionados(int UsuarioId);
    }
}