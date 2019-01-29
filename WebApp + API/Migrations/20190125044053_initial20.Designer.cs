﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using ParkBuddies.Models;

namespace ParkBuddies.Migrations
{
    [DbContext(typeof(ParkBuddiesContext))]
    [Migration("20190125044053_initial20")]
    partial class initial20
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.1.1-rtm-30846")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("ParkBuddies.Models.Carpark", b =>
                {
                    b.Property<string>("CarparkID")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("CarparkLat");

                    b.Property<string>("CarparkLng");

                    b.Property<string>("CarparkName");

                    b.Property<string>("CarparkStatus");

                    b.Property<string>("CompanyID");

                    b.Property<string>("CompanyName");

                    b.Property<int>("NumberOfLotsAvailable");

                    b.Property<int>("TotalNumberOfLots");

                    b.HasKey("CarparkID");

                    b.HasIndex("CompanyID");

                    b.ToTable("Carpark");
                });

            modelBuilder.Entity("ParkBuddies.Models.CarparkCompanies", b =>
                {
                    b.Property<string>("CompanyID")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("CompanyName");

                    b.Property<string>("UserID");

                    b.HasKey("CompanyID");

                    b.HasIndex("UserID");

                    b.ToTable("CarparkCompany");
                });

            modelBuilder.Entity("ParkBuddies.Models.CarparkGraphData", b =>
                {
                    b.Property<string>("CarparkGraphDataID")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("CarparkID");

                    b.Property<string>("CarparkName");

                    b.Property<string>("LotsAvailable");

                    b.Property<DateTime>("TimeRecorded");

                    b.Property<string>("TotalNumberOfLots");

                    b.HasKey("CarparkGraphDataID");

                    b.HasIndex("CarparkID");

                    b.ToTable("CarparkGraphData");
                });

            modelBuilder.Entity("ParkBuddies.Models.CarparkLots", b =>
                {
                    b.Property<string>("LotID")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("CarparkID");

                    b.Property<int>("LotNumber");

                    b.Property<int>("LotStatus");

                    b.HasKey("LotID");

                    b.HasIndex("CarparkID");

                    b.ToTable("CarparkLots");
                });

            modelBuilder.Entity("ParkBuddies.Models.User", b =>
                {
                    b.Property<string>("UserID")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("AdminRFID");

                    b.Property<string>("CarparkID");

                    b.Property<string>("CarparkName");

                    b.Property<string>("CompanyName");

                    b.Property<string>("Name");

                    b.Property<byte[]>("PasswordHash");

                    b.Property<byte[]>("PasswordSalt");

                    b.Property<string>("UserEmail");

                    b.Property<string>("UserName");

                    b.Property<string>("UserProfileImage");

                    b.Property<string>("UserRole");

                    b.Property<string>("UserToken");

                    b.HasKey("UserID");

                    b.HasIndex("CarparkID");

                    b.ToTable("User");
                });

            modelBuilder.Entity("ParkBuddies.Models.UserReport", b =>
                {
                    b.Property<string>("ReportID")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("CarparkID");

                    b.Property<string>("CarparkName");

                    b.Property<string>("ReportDate");

                    b.Property<string>("ReportDescription");

                    b.Property<string>("ReportImage");

                    b.Property<byte[]>("ReportImagebyte");

                    b.Property<string>("ReportStatus");

                    b.Property<string>("ReportType");

                    b.Property<string>("UserID");

                    b.HasKey("ReportID");

                    b.HasIndex("CarparkID");

                    b.HasIndex("UserID");

                    b.ToTable("UserReport");
                });

            modelBuilder.Entity("ParkBuddies.Models.Carpark", b =>
                {
                    b.HasOne("ParkBuddies.Models.CarparkCompanies", "Company")
                        .WithMany("Carparks")
                        .HasForeignKey("CompanyID");
                });

            modelBuilder.Entity("ParkBuddies.Models.CarparkCompanies", b =>
                {
                    b.HasOne("ParkBuddies.Models.User")
                        .WithMany("Companies")
                        .HasForeignKey("UserID");
                });

            modelBuilder.Entity("ParkBuddies.Models.CarparkGraphData", b =>
                {
                    b.HasOne("ParkBuddies.Models.Carpark", "Carpark")
                        .WithMany()
                        .HasForeignKey("CarparkID");
                });

            modelBuilder.Entity("ParkBuddies.Models.CarparkLots", b =>
                {
                    b.HasOne("ParkBuddies.Models.Carpark", "Carpark")
                        .WithMany("CarparkLots")
                        .HasForeignKey("CarparkID");
                });

            modelBuilder.Entity("ParkBuddies.Models.User", b =>
                {
                    b.HasOne("ParkBuddies.Models.Carpark", "Carpark")
                        .WithMany("Users")
                        .HasForeignKey("CarparkID");
                });

            modelBuilder.Entity("ParkBuddies.Models.UserReport", b =>
                {
                    b.HasOne("ParkBuddies.Models.Carpark", "Carpark")
                        .WithMany("UserReports")
                        .HasForeignKey("CarparkID");

                    b.HasOne("ParkBuddies.Models.User", "User")
                        .WithMany("UserReports")
                        .HasForeignKey("UserID");
                });
#pragma warning restore 612, 618
        }
    }
}
