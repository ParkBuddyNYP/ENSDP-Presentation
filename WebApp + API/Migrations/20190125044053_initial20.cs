using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace ParkBuddies.Migrations
{
    public partial class initial20 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "CarparkGraphData",
                columns: table => new
                {
                    CarparkGraphDataID = table.Column<string>(nullable: false),
                    TimeRecorded = table.Column<DateTime>(nullable: false),
                    TotalNumberOfLots = table.Column<string>(nullable: true),
                    LotsAvailable = table.Column<string>(nullable: true),
                    CarparkName = table.Column<string>(nullable: true),
                    CarparkID = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CarparkGraphData", x => x.CarparkGraphDataID);
                });

            migrationBuilder.CreateTable(
                name: "CarparkLots",
                columns: table => new
                {
                    LotID = table.Column<string>(nullable: false),
                    LotNumber = table.Column<int>(nullable: false),
                    LotStatus = table.Column<int>(nullable: false),
                    CarparkID = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CarparkLots", x => x.LotID);
                });

            migrationBuilder.CreateTable(
                name: "User",
                columns: table => new
                {
                    UserID = table.Column<string>(nullable: false),
                    UserName = table.Column<string>(nullable: true),
                    Name = table.Column<string>(nullable: true),
                    PasswordHash = table.Column<byte[]>(nullable: true),
                    PasswordSalt = table.Column<byte[]>(nullable: true),
                    UserProfileImage = table.Column<string>(nullable: true),
                    UserRole = table.Column<string>(nullable: true),
                    UserToken = table.Column<string>(nullable: true),
                    UserEmail = table.Column<string>(nullable: true),
                    CarparkName = table.Column<string>(nullable: true),
                    CarparkID = table.Column<string>(nullable: true),
                    AdminRFID = table.Column<string>(nullable: true),
                    CompanyName = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_User", x => x.UserID);
                });

            migrationBuilder.CreateTable(
                name: "CarparkCompany",
                columns: table => new
                {
                    CompanyID = table.Column<string>(nullable: false),
                    CompanyName = table.Column<string>(nullable: true),
                    UserID = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CarparkCompany", x => x.CompanyID);
                    table.ForeignKey(
                        name: "FK_CarparkCompany_User_UserID",
                        column: x => x.UserID,
                        principalTable: "User",
                        principalColumn: "UserID",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "Carpark",
                columns: table => new
                {
                    CarparkID = table.Column<string>(nullable: false),
                    CarparkName = table.Column<string>(nullable: true),
                    CarparkLat = table.Column<string>(nullable: true),
                    CarparkLng = table.Column<string>(nullable: true),
                    NumberOfLotsAvailable = table.Column<int>(nullable: false),
                    CarparkStatus = table.Column<string>(nullable: true),
                    TotalNumberOfLots = table.Column<int>(nullable: false),
                    CompanyName = table.Column<string>(nullable: true),
                    CompanyID = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Carpark", x => x.CarparkID);
                    table.ForeignKey(
                        name: "FK_Carpark_CarparkCompany_CompanyID",
                        column: x => x.CompanyID,
                        principalTable: "CarparkCompany",
                        principalColumn: "CompanyID",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "UserReport",
                columns: table => new
                {
                    ReportID = table.Column<string>(nullable: false),
                    ReportStatus = table.Column<string>(nullable: true),
                    ReportDate = table.Column<string>(nullable: true),
                    ReportDescription = table.Column<string>(nullable: true),
                    ReportType = table.Column<string>(nullable: true),
                    ReportImage = table.Column<string>(nullable: true),
                    ReportImagebyte = table.Column<byte[]>(nullable: true),
                    UserID = table.Column<string>(nullable: true),
                    CarparkName = table.Column<string>(nullable: true),
                    CarparkID = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserReport", x => x.ReportID);
                    table.ForeignKey(
                        name: "FK_UserReport_Carpark_CarparkID",
                        column: x => x.CarparkID,
                        principalTable: "Carpark",
                        principalColumn: "CarparkID",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_UserReport_User_UserID",
                        column: x => x.UserID,
                        principalTable: "User",
                        principalColumn: "UserID",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Carpark_CompanyID",
                table: "Carpark",
                column: "CompanyID");

            migrationBuilder.CreateIndex(
                name: "IX_CarparkCompany_UserID",
                table: "CarparkCompany",
                column: "UserID");

            migrationBuilder.CreateIndex(
                name: "IX_CarparkGraphData_CarparkID",
                table: "CarparkGraphData",
                column: "CarparkID");

            migrationBuilder.CreateIndex(
                name: "IX_CarparkLots_CarparkID",
                table: "CarparkLots",
                column: "CarparkID");

            migrationBuilder.CreateIndex(
                name: "IX_User_CarparkID",
                table: "User",
                column: "CarparkID");

            migrationBuilder.CreateIndex(
                name: "IX_UserReport_CarparkID",
                table: "UserReport",
                column: "CarparkID");

            migrationBuilder.CreateIndex(
                name: "IX_UserReport_UserID",
                table: "UserReport",
                column: "UserID");

            migrationBuilder.AddForeignKey(
                name: "FK_CarparkGraphData_Carpark_CarparkID",
                table: "CarparkGraphData",
                column: "CarparkID",
                principalTable: "Carpark",
                principalColumn: "CarparkID",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_CarparkLots_Carpark_CarparkID",
                table: "CarparkLots",
                column: "CarparkID",
                principalTable: "Carpark",
                principalColumn: "CarparkID",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_User_Carpark_CarparkID",
                table: "User",
                column: "CarparkID",
                principalTable: "Carpark",
                principalColumn: "CarparkID",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Carpark_CarparkCompany_CompanyID",
                table: "Carpark");

            migrationBuilder.DropTable(
                name: "CarparkGraphData");

            migrationBuilder.DropTable(
                name: "CarparkLots");

            migrationBuilder.DropTable(
                name: "UserReport");

            migrationBuilder.DropTable(
                name: "CarparkCompany");

            migrationBuilder.DropTable(
                name: "User");

            migrationBuilder.DropTable(
                name: "Carpark");
        }
    }
}
