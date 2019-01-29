using Microsoft.EntityFrameworkCore.Migrations;

namespace ParkBuddies.Migrations
{
    public partial class inital31 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "LotStatus",
                table: "CarparkLots",
                nullable: true,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "LotStatus",
                table: "CarparkLots",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);
        }
    }
}
