using Microsoft.EntityFrameworkCore.Migrations;

namespace ParkBuddies.Migrations
{
    public partial class intital21 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "NumberOfLotsAvailable",
                table: "Carpark");

            migrationBuilder.DropColumn(
                name: "TotalNumberOfLots",
                table: "Carpark");

            migrationBuilder.AddColumn<string>(
                name: "CarparkName",
                table: "CarparkLots",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "NumberOfLotsAvailable",
                table: "CarparkLots",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "TotalNumberOfLots",
                table: "CarparkLots",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "CarparkName",
                table: "CarparkLots");

            migrationBuilder.DropColumn(
                name: "NumberOfLotsAvailable",
                table: "CarparkLots");

            migrationBuilder.DropColumn(
                name: "TotalNumberOfLots",
                table: "CarparkLots");

            migrationBuilder.AddColumn<int>(
                name: "NumberOfLotsAvailable",
                table: "Carpark",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "TotalNumberOfLots",
                table: "Carpark",
                nullable: false,
                defaultValue: 0);
        }
    }
}
