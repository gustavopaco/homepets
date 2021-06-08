using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;


namespace HomePets.Data
{
    public class EFContext : DbContext
    {
        public EFContext(DbContextOptions options) : base(options)
        {

        }
        public DbSet<Usuario> Usuarios { get; set; }
        public DbSet<Pet> Pets { get; set; }
        public DbSet<Servico> Servicos { get; set; }
        public DbSet<ServicoContratado> ServicosContratados { get; set; }
        public DbSet<CartaoCredito> CartoesCredito { get; set; }
    }
}
