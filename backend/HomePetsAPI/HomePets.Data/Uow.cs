using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;


namespace HomePets.Data
{
    public class UoW : IUoW
    {

        private readonly EFContext _context;
        public IPetRep Pets { get; private set; }
        public IServicoRep Servicos { get; private set; }
        public IServicoContratadoRep ServicosContratados { get; private set; }
        public IUsuarioRep Usuarios { get; private set; }


        public UoW(EFContext context)
        {
            _context = context;

            Pets = new PetRep(context);
            Servicos = new ServicoRep(context);
            ServicosContratados = new ServicoContratadoRep(context);
            Usuarios = new UsuarioRep(context);
        }


        public int SaveChanges()
        {
            return _context.SaveChanges();
        }
    }
}
