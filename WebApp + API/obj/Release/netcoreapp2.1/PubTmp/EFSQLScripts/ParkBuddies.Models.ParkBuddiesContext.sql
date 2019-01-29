IF OBJECT_ID(N'[__EFMigrationsHistory]') IS NULL
BEGIN
    CREATE TABLE [__EFMigrationsHistory] (
        [MigrationId] nvarchar(150) NOT NULL,
        [ProductVersion] nvarchar(32) NOT NULL,
        CONSTRAINT [PK___EFMigrationsHistory] PRIMARY KEY ([MigrationId])
    );
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE TABLE [CarparkGraphData] (
        [CarparkGraphDataID] nvarchar(450) NOT NULL,
        [TimeRecorded] datetime2 NOT NULL,
        [TotalNumberOfLots] nvarchar(max) NULL,
        [LotsAvailable] nvarchar(max) NULL,
        [CarparkName] nvarchar(max) NULL,
        [CarparkID] nvarchar(450) NULL,
        CONSTRAINT [PK_CarparkGraphData] PRIMARY KEY ([CarparkGraphDataID])
    );
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE TABLE [CarparkLots] (
        [LotID] nvarchar(450) NOT NULL,
        [LotNumber] int NOT NULL,
        [LotStatus] int NOT NULL,
        [CarparkID] nvarchar(450) NULL,
        CONSTRAINT [PK_CarparkLots] PRIMARY KEY ([LotID])
    );
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE TABLE [User] (
        [UserID] nvarchar(450) NOT NULL,
        [UserName] nvarchar(max) NULL,
        [Name] nvarchar(max) NULL,
        [PasswordHash] varbinary(max) NULL,
        [PasswordSalt] varbinary(max) NULL,
        [UserProfileImage] nvarchar(max) NULL,
        [UserRole] nvarchar(max) NULL,
        [UserToken] nvarchar(max) NULL,
        [UserEmail] nvarchar(max) NULL,
        [CarparkName] nvarchar(max) NULL,
        [CarparkID] nvarchar(450) NULL,
        [AdminRFID] nvarchar(max) NULL,
        [CompanyName] nvarchar(max) NULL,
        CONSTRAINT [PK_User] PRIMARY KEY ([UserID])
    );
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE TABLE [CarparkCompany] (
        [CompanyID] nvarchar(450) NOT NULL,
        [CompanyName] nvarchar(max) NULL,
        [UserID] nvarchar(450) NULL,
        CONSTRAINT [PK_CarparkCompany] PRIMARY KEY ([CompanyID]),
        CONSTRAINT [FK_CarparkCompany_User_UserID] FOREIGN KEY ([UserID]) REFERENCES [User] ([UserID]) ON DELETE NO ACTION
    );
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE TABLE [Carpark] (
        [CarparkID] nvarchar(450) NOT NULL,
        [CarparkName] nvarchar(max) NULL,
        [CarparkLat] nvarchar(max) NULL,
        [CarparkLng] nvarchar(max) NULL,
        [NumberOfLotsAvailable] int NOT NULL,
        [CarparkStatus] nvarchar(max) NULL,
        [TotalNumberOfLots] int NOT NULL,
        [CompanyName] nvarchar(max) NULL,
        [CompanyID] nvarchar(450) NULL,
        CONSTRAINT [PK_Carpark] PRIMARY KEY ([CarparkID]),
        CONSTRAINT [FK_Carpark_CarparkCompany_CompanyID] FOREIGN KEY ([CompanyID]) REFERENCES [CarparkCompany] ([CompanyID]) ON DELETE NO ACTION
    );
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE TABLE [UserReport] (
        [ReportID] nvarchar(450) NOT NULL,
        [ReportStatus] nvarchar(max) NULL,
        [ReportDate] nvarchar(max) NULL,
        [ReportDescription] nvarchar(max) NULL,
        [ReportType] nvarchar(max) NULL,
        [ReportImage] nvarchar(max) NULL,
        [ReportImagebyte] varbinary(max) NULL,
        [UserID] nvarchar(450) NULL,
        [CarparkName] nvarchar(max) NULL,
        [CarparkID] nvarchar(450) NULL,
        CONSTRAINT [PK_UserReport] PRIMARY KEY ([ReportID]),
        CONSTRAINT [FK_UserReport_Carpark_CarparkID] FOREIGN KEY ([CarparkID]) REFERENCES [Carpark] ([CarparkID]) ON DELETE NO ACTION,
        CONSTRAINT [FK_UserReport_User_UserID] FOREIGN KEY ([UserID]) REFERENCES [User] ([UserID]) ON DELETE NO ACTION
    );
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE INDEX [IX_Carpark_CompanyID] ON [Carpark] ([CompanyID]);
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE INDEX [IX_CarparkCompany_UserID] ON [CarparkCompany] ([UserID]);
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE INDEX [IX_CarparkGraphData_CarparkID] ON [CarparkGraphData] ([CarparkID]);
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE INDEX [IX_CarparkLots_CarparkID] ON [CarparkLots] ([CarparkID]);
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE INDEX [IX_User_CarparkID] ON [User] ([CarparkID]);
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE INDEX [IX_UserReport_CarparkID] ON [UserReport] ([CarparkID]);
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    CREATE INDEX [IX_UserReport_UserID] ON [UserReport] ([UserID]);
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    ALTER TABLE [CarparkGraphData] ADD CONSTRAINT [FK_CarparkGraphData_Carpark_CarparkID] FOREIGN KEY ([CarparkID]) REFERENCES [Carpark] ([CarparkID]) ON DELETE NO ACTION;
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    ALTER TABLE [CarparkLots] ADD CONSTRAINT [FK_CarparkLots_Carpark_CarparkID] FOREIGN KEY ([CarparkID]) REFERENCES [Carpark] ([CarparkID]) ON DELETE NO ACTION;
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    ALTER TABLE [User] ADD CONSTRAINT [FK_User_Carpark_CarparkID] FOREIGN KEY ([CarparkID]) REFERENCES [Carpark] ([CarparkID]) ON DELETE NO ACTION;
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125044053_initial20')
BEGIN
    INSERT INTO [__EFMigrationsHistory] ([MigrationId], [ProductVersion])
    VALUES (N'20190125044053_initial20', N'2.1.4-rtm-31024');
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125093118_intital21')
BEGIN
    DECLARE @var0 sysname;
    SELECT @var0 = [d].[name]
    FROM [sys].[default_constraints] [d]
    INNER JOIN [sys].[columns] [c] ON [d].[parent_column_id] = [c].[column_id] AND [d].[parent_object_id] = [c].[object_id]
    WHERE ([d].[parent_object_id] = OBJECT_ID(N'[Carpark]') AND [c].[name] = N'NumberOfLotsAvailable');
    IF @var0 IS NOT NULL EXEC(N'ALTER TABLE [Carpark] DROP CONSTRAINT [' + @var0 + '];');
    ALTER TABLE [Carpark] DROP COLUMN [NumberOfLotsAvailable];
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125093118_intital21')
BEGIN
    DECLARE @var1 sysname;
    SELECT @var1 = [d].[name]
    FROM [sys].[default_constraints] [d]
    INNER JOIN [sys].[columns] [c] ON [d].[parent_column_id] = [c].[column_id] AND [d].[parent_object_id] = [c].[object_id]
    WHERE ([d].[parent_object_id] = OBJECT_ID(N'[Carpark]') AND [c].[name] = N'TotalNumberOfLots');
    IF @var1 IS NOT NULL EXEC(N'ALTER TABLE [Carpark] DROP CONSTRAINT [' + @var1 + '];');
    ALTER TABLE [Carpark] DROP COLUMN [TotalNumberOfLots];
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125093118_intital21')
BEGIN
    ALTER TABLE [CarparkLots] ADD [CarparkName] nvarchar(max) NULL;
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125093118_intital21')
BEGIN
    ALTER TABLE [CarparkLots] ADD [NumberOfLotsAvailable] int NOT NULL DEFAULT 0;
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125093118_intital21')
BEGIN
    ALTER TABLE [CarparkLots] ADD [TotalNumberOfLots] int NOT NULL DEFAULT 0;
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125093118_intital21')
BEGIN
    INSERT INTO [__EFMigrationsHistory] ([MigrationId], [ProductVersion])
    VALUES (N'20190125093118_intital21', N'2.1.4-rtm-31024');
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125154610_initial30')
BEGIN
    ALTER TABLE [Carpark] ADD [NumberOfLotsAvailable] int NOT NULL DEFAULT 0;
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125154610_initial30')
BEGIN
    ALTER TABLE [Carpark] ADD [TotalNumberOfLots] int NOT NULL DEFAULT 0;
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125154610_initial30')
BEGIN
    INSERT INTO [__EFMigrationsHistory] ([MigrationId], [ProductVersion])
    VALUES (N'20190125154610_initial30', N'2.1.4-rtm-31024');
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125162723_inital31')
BEGIN
    DECLARE @var2 sysname;
    SELECT @var2 = [d].[name]
    FROM [sys].[default_constraints] [d]
    INNER JOIN [sys].[columns] [c] ON [d].[parent_column_id] = [c].[column_id] AND [d].[parent_object_id] = [c].[object_id]
    WHERE ([d].[parent_object_id] = OBJECT_ID(N'[CarparkLots]') AND [c].[name] = N'LotStatus');
    IF @var2 IS NOT NULL EXEC(N'ALTER TABLE [CarparkLots] DROP CONSTRAINT [' + @var2 + '];');
    ALTER TABLE [CarparkLots] ALTER COLUMN [LotStatus] nvarchar(max) NULL;
END;

GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20190125162723_inital31')
BEGIN
    INSERT INTO [__EFMigrationsHistory] ([MigrationId], [ProductVersion])
    VALUES (N'20190125162723_inital31', N'2.1.4-rtm-31024');
END;

GO

