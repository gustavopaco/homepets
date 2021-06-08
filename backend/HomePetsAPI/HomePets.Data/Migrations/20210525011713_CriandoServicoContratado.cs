using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace HomePets.Data.Migrations
{
    public partial class CriandoServicoContratado : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ServicosContratados",
                columns: table => new
                {
                    Deleted = table.Column<bool>(nullable: false),
                    Timestamp = table.Column<byte[]>(rowVersion: true, nullable: true),
                    ServicoContratadoId = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    ValorTotal = table.Column<double>(nullable: false),
                    DataExecucao = table.Column<DateTime>(nullable: false),
                    ServicoId = table.Column<int>(nullable: false),
                    PetId = table.Column<int>(nullable: false),
                    UsuarioDonoPetId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ServicosContratados", x => x.ServicoContratadoId);
                    table.ForeignKey(
                        name: "FK_ServicosContratados_Pets_PetId",
                        column: x => x.PetId,
                        principalTable: "Pets",
                        principalColumn: "PetId",
                        onDelete: ReferentialAction.NoAction);
                    table.ForeignKey(
                        name: "FK_ServicosContratados_Servicos_ServicoId",
                        column: x => x.ServicoId,
                        principalTable: "Servicos",
                        principalColumn: "ServicoId",
                        onDelete: ReferentialAction.NoAction);
                    table.ForeignKey(
                        name: "FK_ServicosContratados_Usuarios_UsuarioDonoPetId",
                        column: x => x.UsuarioDonoPetId,
                        principalTable: "Usuarios",
                        principalColumn: "UsuarioId",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ServicosContratados_PetId",
                table: "ServicosContratados",
                column: "PetId");

            migrationBuilder.CreateIndex(
                name: "IX_ServicosContratados_ServicoId",
                table: "ServicosContratados",
                column: "ServicoId");

            migrationBuilder.CreateIndex(
                name: "IX_ServicosContratados_UsuarioDonoPetId",
                table: "ServicosContratados",
                column: "UsuarioDonoPetId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ServicosContratados");
        }
    }
}
