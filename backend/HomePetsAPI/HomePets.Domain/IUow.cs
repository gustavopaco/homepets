using System;

namespace HomePets.Domain
{
    public interface IUoW
    {
        int SaveChanges();

        IUsuarioRep Usuarios { get; }
        IServicoRep Servicos { get; }
        IPetRep Pets { get; }
    }
}