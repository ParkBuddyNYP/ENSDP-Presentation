using Microsoft.EntityFrameworkCore.Migrations;

namespace ParkBuddies.Migrations
{
    public partial class initial30 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
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

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "NumberOfLotsAvailable",
                table: "Carpark");

            migrationBuilder.DropColumn(
                name: "TotalNumberOfLots",
                table: "Carpark");
        }
    }
}
